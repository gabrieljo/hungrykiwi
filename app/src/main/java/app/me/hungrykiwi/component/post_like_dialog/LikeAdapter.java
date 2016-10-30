package app.me.hungrykiwi.component.post_like_dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.user.Restr;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.service.PostService;

/**
 * comment adapter
 * Created by user on 10/14/2016.
 */
public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeHolder>{
    Context mContext;
    ArrayList<User> mList;
    String next;
    int postId;

    public LikeAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }


    /**
     * show comment by post id
     * @param postId
     */
    public void read(int postId) {
        new PostService(this.mContext).readLike(postId, this.next, this);
        this.postId = postId;
    }

    /**
     * read from already shown comment list
     */
    public void read() {
        new PostService(this.mContext).readLike(this.postId, this.next, this);
    }


    /**
     * add more comment to adapter
     * @param users
     * @param next
     */
    public void more(User[] users, String next) {
        this.next = next;
        int count = users.length;
        for(int i=0; i< count; i++) {
            this.mList.add(users[i]);
        }
        this.notifyDataSetChanged();
    }



    /**
     * clear out all the comments
     */
    public void clear() {
        this.next = null;
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public LikeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LikeHolder(LayoutInflater.from(this.mContext).inflate(R.layout.list_like, null));
    }

    @Override
    public void onBindViewHolder(LikeHolder holder, int position) {
        // --- READ MORE COMMENT FROM SERVER --- ///
        if(position == this.getItemCount() - 1 && this.next != null) {
            this.read();
        }

        User user = this.mList.get(position);
        holder.set(user);

    }



    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    /**
     * comment holder for saving memory
     */
    class LikeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image; // user image
        TextView textName; // name and comment
        public LikeHolder(View view) {
            super(view);
            this.image = (ImageView)view.findViewById(R.id.imageUser);
            this.textName = (TextView) view.findViewById(R.id.textName);
        }

        /**
         * set info for comment views
         */
        public void set(User user) {

            if(user.getIsRestr() == 0 ) { // user comment
                HungryClient.userImage(mContext, this.image, user.getImgPath());
                this.textName.setText(user.getName());
            } else if (user.getIsRestr() == 1 ) { // restr comment
                Restr restr = user.getRestr();
                HungryClient.userImage(mContext, this.image, restr.getImg());
                this.textName.setText(restr.getName());
            }

        }

        /**
         * click event
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image :
                    break;
                case R.id.textName :
                    break;
            }
        }
    }
}
