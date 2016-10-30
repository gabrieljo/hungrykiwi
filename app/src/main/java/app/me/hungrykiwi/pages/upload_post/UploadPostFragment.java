package app.me.hungrykiwi.pages.upload_post;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.service.PostService;
import app.me.hungrykiwi.utils.ActionUtil;
import app.me.hungrykiwi.utils.Message;
import app.me.hungrykiwi.utils.Utility;
import app.me.hungrykiwi.utils.Validation;
import app.me.hungrykiwi.component.attached_image.ImageAdapter;
import app.me.hungrykiwi.component.viewutil.ViewUtil;

/**
 * upload new post
 * Created by user on 10/6/2016.
 */

public class UploadPostFragment extends Fragment implements View.OnClickListener {


    ArrayList<String> mImgList; // single image took from camera
    LinearLayout parent;
    Uri imgUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_upload_post, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User appUser = User.getAppUser(); // app user info
        this.mImgList = new ArrayList<>();
        if (appUser.getIsRestr() == 0) { // a user
            this.user(appUser);
        } else { // restraurant owner
            this.restr(appUser);
        }

        this.initViews();// init views
        Intent intent = this.getActivity().getIntent();
        this.whatToDo(); // navigate to action triggered from previous activity

    }

    /**
     * user select from uploaed post to edit
     */
    public void goEdit(Intent intent) {
        String content = intent.getStringExtra("content");
        EditText edit = (EditText)this.getView().findViewById(R.id.editContent);
        edit.setText(content);
        edit.setSelection(edit.length());
    }


    /**
     * trigger action decided from previous activity, MainActivity
     */
    public void whatToDo() {
        Intent intent = this.getActivity().getIntent();
        String todo = intent.getType();
        if(todo != null && todo.equals("camera")) {
            this.goCamera();
        } else if(todo != null && todo.equals("gallery")){
            this.goGallery();
        } else if (todo != null && todo.equals("edit")) {
            this.goEdit(intent);
        }
    }

    /**
     * init views
     */
    public void initViews() {
        View view = this.getView();
        view.findViewById(R.id.camera).setOnClickListener(this); // camera select
        view.findViewById(R.id.gallery).setOnClickListener(this); // picture select
        this.parent = (LinearLayout) view.findViewById(R.id.parent);
    }


    /**
     * init user info
     */
    public void user(User appUser) {
        View view = this.getView();
        // set image
        HungryClient.userImage(this.getContext(), (ImageView) view.findViewById(R.id.imgUser), appUser.getImgPath());
        ((TextView) view.findViewById(R.id.textName)).setText(appUser.getName());
    }

    /**
     * init restraunt info
     * @param user
     */
    public void restr(User user) {

    }

    /**
     * upload new post to server
     */
    public void post() {
        String content = ((EditText) this.getView().findViewById(R.id.editContent)).getText().toString();
        int count = this.mImgList.size();
        String[] imgs = new String[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = this.mImgList.get(i);
        }
        Intent intent = this.getActivity().getIntent();
        PostService service = new PostService(this.getContext());
        String type = intent.getType();
        if(type != null && type.equals("edit"))   // edit
            service.edit(content, 0, intent.getIntExtra("post_id", 0));
        else  // create new post
            service.store(content, imgs, 0);
    }

    /**
     * to validate if
     * @return
     */
    public boolean isReady() {
        return !Validation.isEmpty(this.getView().findViewById(R.id.editContent));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.getActivity().RESULT_OK) { // success
            switch (requestCode) {
                case ActionUtil.RC_CAMERA: // camera
                    if (this.imgUri != null) { // image uri location
                        this.mImgList.add(this.imgUri.toString());
                        this.addImage(new String[]{this.mImgList.get(this.mImgList.size() - 1)});
                        this.imgUri = null;
                    }
                    break;
                case ActionUtil.RC_GALLERY: // gallery
                    ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
                    int count = images.size();
                    String[] info = new String[count];
                    for (int i = 0; i < count; i++) {
                        String uriPath = images.get(i).getPath();
                        this.mImgList.add(uriPath);
                        info[i] = "file://" + uriPath;
                    }
                    this.addImage(info);
                    break;
            }

        }

    }

    /**
     * init recyclerview for upload image
     *
     * @return
     */
    public void addImage(String[] uris) {
        RecyclerView recycler = null;
        ViewUtil util = new ViewUtil();
        if (this.parent.getTag() == null) {
            recycler = util.getImageRecycler(this.getContext(), uris, ImageAdapter.Mode.UPLOAD_IMAGE);
            this.parent.addView(recycler);
            this.parent.setTag(recycler);
        } else {
            recycler = (RecyclerView) this.parent.getTag();
            ((ImageAdapter) recycler.getAdapter()).notifyDataSetChanged();
        }
        util.setImageRecyclerGridLayout(recycler, this.mImgList.size());
    }

    /**
     * activate camera
     */
    public void goCamera() {
        this.imgUri = new ActionUtil().goCamera(UploadPostFragment.this);
    }

    /**
     * activate multiple image chooser
     */
    public void goGallery() {
        new ActionUtil().goGallery(UploadPostFragment.this, 5 - mImgList.size());
    }


    @Override
    public void onClick(View v) {
        if (this.mImgList.size() < 5) {
            switch (v.getId()) {
                case R.id.camera: // camera
                    this.goCamera();
                    break;
                case R.id.gallery: // galaery
                    this.goGallery();
                    break;
            }
        } else {
            new Utility().toast(this.getContext(), Message.getMessage(Message.Type.PROBLEM_IMAGE_NOT_ALLOWED));
        }
    }
}
