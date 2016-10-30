package app.me.hungrykiwi.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.component.comment_dialog.CommentAdapter;
import app.me.hungrykiwi.component.post_like_dialog.LikeAdapter;
import app.me.hungrykiwi.http.Config;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.http.hungry_kiwi.HungryResponse;
import app.me.hungrykiwi.http.hungry_kiwi.HungryURL;
import app.me.hungrykiwi.model.Comment;
import app.me.hungrykiwi.model.post.Post;
import app.me.hungrykiwi.model.user.Restr;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.pages.post.PostAdapter;
import app.me.hungrykiwi.utils.Utility;
import app.me.hungrykiwi.component.Progress;
import cz.msebera.android.httpclient.Header;

/**
 * post service, create, upload or remove ..
 * Created by user on 10/3/2016.
 */

public class PostService {
    Context mContext;

    public PostService(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * edit post
     * @param content
     * @param restr_id
     * @param postId
     */
    public void edit(final String content, int restr_id, int postId) {
        try {
            Progress.on(this.mContext);
            HungryClient client = new HungryClient();
            HashMap<String, Object> coll = new HashMap<>();
            coll.put("content", content);
            coll.put("restr_id", restr_id);
            String url = HungryURL.getRelationalUrl(HungryURL.Routing.Type.POST_EDIT, coll, postId);
            Log.d("INFO", url);

            client.get(url, new HungryResponse(mContext) {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Progress.off();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Progress.off();
                    ((Activity) mContext).finish();
                }
            });
        } catch (Exception ex) {
            Log.d("INFO", "PostService store error : " + ex.getMessage());
        }
    }

    /**
     * create new post
     *
     * @param content
     * @param imgPaths
     */
    public void store(final String content, String[] imgPaths, int restr_id) {
        try {
            Progress.on(this.mContext);
            HungryClient client = new HungryClient();
            String url = HungryURL.postUrl(HungryURL.Routing.post);
            Log.d("INFO", "정보 : "+url);

            client.put("content", content);
            client.put("like_num", 0);
            client.put("comment_num", 0);
            if (imgPaths == null || imgPaths.length == 0) {
                client.put("is_img", 0);
            } else {
                client.put("is_img", 1);
                int count = imgPaths.length;
                File[] files = new File[imgPaths.length];
                for (int i = 0; i < count; i++) {
                    if (imgPaths[i].contains("file://") == true)
                        imgPaths[i] = imgPaths[i].substring(7);
                    files[i] = new File(imgPaths[i]);
                }
                client.put("img[]", files);
            }
            client.put("restr_id", restr_id);
                client.post(url, client.getParam(), new HungryResponse(mContext) {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Progress.off();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Progress.off();
                        ((Activity) mContext).finish();
                    }
                });
        } catch (FileNotFoundException ex) {
            Log.d("INFO", "PostService file error : " + ex.getMessage());
        } catch (Exception ex) {
            Log.d("INFO", "PostService store error : " + ex.getMessage());
        }
    }


    /**
     * delete post
     * @param postId
     */
    public void delete(int postId, final PostAdapter adapter) {
        String url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.POST, postId);
        Log.d("INFO", url);
        HungryClient client = new HungryClient();
        client.delete(url, new HungryResponse(this.mContext) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                adapter.refresh();
            }
        });
    }

    /**
     * read post by current page
     *
     * @param next
     * @param adapter
     */
    public void read(String next, final PostAdapter adapter) {
        try {
            String url = null;
            if (next == null) url = HungryURL.getPostPagination(HungryURL.Routing.post, 1);
            else url = next;
            HungryClient client = new HungryClient();
            client.get(url, new HungryResponse(mContext) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String newNext = response.getString("next_page_url");
                        if (newNext == null && newNext.equals("null")) newNext = null;
                        JSONArray jsonData = response.getJSONArray("data");
                        int size = jsonData.length();
                        Post[] posts = new Post[size];
                        Gson gson = new Gson();
                        for (int i = 0; i < size; i++) {
                            JSONObject jsonEach = jsonData.getJSONObject(i);
                            posts[i] = gson.fromJson(jsonEach.getJSONObject("post").toString(), Post.class);
                            User user = gson.fromJson(jsonEach.getJSONObject("user").toString(), User.class);
                            if (jsonEach.isNull("restr") == false)
                                user.setRestr(gson.fromJson(jsonEach.getJSONObject("restr").toString(), Restr.class));
                            if (jsonEach.isNull("imgs") == false) {
                                JSONArray jsonImgs = jsonEach.getJSONArray("imgs");
                                int count = jsonImgs.length();
                                String[] imgs = new String[count];
                                for (int j = 0; j < count; j++) {
                                    imgs[j] = Config.baseUrl + jsonImgs.getJSONObject(j).getString("img");
                                }
                                posts[i].setImgs(imgs);
                            }
                            user.setName(user.getfName() + " " + user.getlName());
                            posts[i].setUser(user);
                        }
                        adapter.add(posts, newNext);

                    } catch (JSONException ex) {
                        Log.d("INFO", "PostService : " + ex.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Utility.goWeb(responseString, mContext);
                }
            });
        } catch (Exception ex) {
            Log.d("INFO", "PostService read : " + ex.getMessage());
        }
    }

    /**
     * read comment from server
     */
    public void readComment(int postId, String url, final CommentAdapter adapter) {
        if (url == null)
            url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.POST_COMMENT, postId);
        HungryClient client = new HungryClient();
        client.get(url, new HungryResponse(this.mContext) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String next = response.getString("next_page_url");
                    JSONArray jsonData = response.getJSONArray("data");
                    int count = jsonData.length();
                    Gson gson = new Gson();
                    Comment[] comments = new Comment[count];
                    for (int i = 0; i < count; i++) {
                        JSONObject jsonEach = jsonData.getJSONObject(i);
                        User user = gson.fromJson(jsonEach.getJSONObject("user").toString(), User.class);
                        user.setName(user.getfName() + " " + user.getlName());
                        if (jsonEach.has("restr") == true) {
                            user.setRestr(gson.fromJson(jsonEach.getJSONObject("restr").toString(), Restr.class));
                        }
                        comments[i] = gson.fromJson(jsonEach.toString(), Comment.class);
                        comments[i].setUser(user);
                    }
                    adapter.more(comments, next);
                } catch (Exception ex) {
                    Log.d("INFO", "PostService readComment : " + ex.getMessage());
                }
            }
        });

    }

    /**
     * set like
     */
    public void like(int postId, final int postHolder, final PostAdapter adapter) {
        try {
            HungryClient client = new HungryClient();
            String url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.POST_LIKE, postId);
            client.put("post_id", postId);
            client.post(url, client.getParam(), new HungryResponse(this.mContext) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String confirm = response.getString("message");
                        adapter.setLike(postHolder, confirm.equals(mContext.getString(R.string.success)));
                    } catch (JSONException ex) {
                        Log.d("INFO", "PostService like : " + ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("INFO", "PostService like store : " + ex.getMessage());
        }
    }

    /**
     * store comment into server
     */
    public void storeComment(int postId, final int postHolderId, String comment, final CommentAdapter adapter, final PostAdapter postAdapter) {
        try {
            HungryClient client = new HungryClient();
            String url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.POST_COMMENT, postId);
            client.put("post_id", postId);
            client.put("comment", comment);
            client.post(url, client.getParam(), new HungryResponse(this.mContext) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    adapter.refresh();
                    postAdapter.setComment(postHolderId, true);
                }
            });
        } catch (Exception ex) {
            Log.d("INFO", "PostService comment store : " + ex.getMessage());
        }
    }

    /**
     * check who like
     *
     * @param postId
     */
    public void readLike(int postId, String url, final LikeAdapter adapter) {
        try {
            HungryClient client = new HungryClient();
            if (url == null)
                url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.POST_LIKE, postId);
            client.put("post_id", postId);
            client.get(url, client.getParam(), new HungryResponse(this.mContext) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String next = response.getString("next_page_url");
                        JSONArray jsonData = response.getJSONArray("data");
                        int count = jsonData.length();
                        Gson gson = new Gson();
                        User[] users = new User[count];
                        for (int i = 0; i < count; i++) {
                            JSONObject jsonEach = jsonData.getJSONObject(i);
                            users[i] = gson.fromJson(jsonEach.getJSONObject("user").toString(), User.class);
                            users[i].setName(users[i].getfName() + " " + users[i].getlName());
                            if (users[i].getIsRestr() == 1) {
                                users[i].setRestr(gson.fromJson(jsonEach.getJSONObject("restr").toString(), Restr.class));
                            }
                        }
                        adapter.more(users, next);
                    } catch (JSONException ex) {
                        Log.d("INFO", "PostService readLike : " + ex.getMessage());
                    }

                }


            });
        } catch (Exception ex) {
            Log.d("INFO", "PostService like store : " + ex.getMessage());
        }
    }

    /**
     * edit comment
     * @param comment
     * @param commentId
     */
    public void editComment(String comment, int commentId, final CommentAdapter adapter) {
        try {
            HashMap<String, Object> coll = new HashMap<>();
            coll.put("comment", comment);

            String url = HungryURL.getRelationalUrl(HungryURL.Routing.Type.POST_COMMENT_EDIT, coll, 0, commentId);
            HungryClient client = new HungryClient();


            client.get(url, new HungryResponse(this.mContext){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    adapter.refresh();
                }
            });
        }catch(Exception ex) {
            Log.d("INFO", "PostService editComment : "+ex.getMessage());
        }
    }


    /**
     * delete comment
     * @param commentId
     */
    public void deleteComment(int commentId, int postId, final int postHolderId, final CommentAdapter adapter, final PostAdapter postAdapter) {
        String url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.POST_COMMENT_DELETE, postId, commentId);
        HungryClient client = new HungryClient();
        client.delete(url, new HungryResponse(this.mContext){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                adapter.refresh();
                postAdapter.setComment(postHolderId, false);
            }
        });
    }


}
