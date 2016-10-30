package app.me.hungrykiwi.component.attached_image;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.utils.Utility;

/**
 * image adapter for post, restraurant and recipe
 * Created by user on 10/11/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder>{
    Context mContext;
    ArrayList<String> mImgList; // image url info
    int mode = 0;
    public ImageAdapter(Context mContext, ArrayList<String> imgList) {
        this.mContext = mContext;
        this.mImgList = new ArrayList<>();
        this.mImgList = imgList;
    }

    public ImageAdapter(Context mContext, String[] imgs) {
        this.mContext = mContext;
        this.mImgList = new ArrayList<>();
        this.mImgList = new ArrayList<>();
        int count = imgs.length;
        for(int i = 0 ; i < count ; i++) {
            this.mImgList.add(imgs[i]);
        }
    }

    /**
     * set mode between attached image for post and for upload image
     * @param mode
     * @return
     */
    public ImageAdapter setMode(int mode) {
        this.mode=mode;
        return this;
    }


    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = null;
        switch(this.mode) {
            case Mode.ATTACHED_IMAGE :
                root = LayoutInflater.from(this.mContext).inflate(R.layout.image_atttached, null);
                break;
            case Mode.UPLOAD_IMAGE :
                root = LayoutInflater.from(this.mContext).inflate(R.layout.image_upload, null);
                break;
        }
        return new ImageHolder(root);

    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        holder.set(position);
    }

    @Override
    public int getItemCount() {
        return this.mImgList.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        View root;
        ImageView image;
        TextView text;
        public ImageHolder(View itemView) {
            super(itemView);
            this.root = itemView;
            switch (mode) {
                case ImageAdapter.Mode.ATTACHED_IMAGE :
                    this.image = (ImageView)itemView.findViewById(R.id.imageAttach);
                    this.text = (TextView)itemView.findViewById(R.id.text);
                    break;
                case ImageAdapter.Mode.UPLOAD_IMAGE :
                    this.image = (ImageView)itemView.findViewById(R.id.imageUpload);
                    break;
            }


        }

        /**
         * set image view
         * @param position
         */
        public void set(int position) {
            switch(mImgList.size()) {
                case 1:
                    this.setSingle();
                    break;
                case 2:
                    this.setDouble(position);
                    break;
                case 3:
                    this.setTriple(position);
                    break;
                case 4:
                    this.setQuadruple(position);
                    break;
                case 5:
                    this.setPentuple(position);
                    break;
            }

        }

        /**
         * set single image
         */
        public void setSingle() {
            int width = new Utility().screenWidth(mContext);

            if(mode == ImageAdapter.Mode.ATTACHED_IMAGE)
                HungryClient.image(mContext, this.image, mImgList.get(0));
            else if(mode == ImageAdapter.Mode.UPLOAD_IMAGE)
                this.image.setImageBitmap(new Utility().uriTobitmap(mContext, Uri.parse(mImgList.get(0)), width, width));


        }

        /**
         * set double image
         */
        public void setDouble(int position) {
            int width = new Utility().screenWidth(mContext);
            if(mode == ImageAdapter.Mode.ATTACHED_IMAGE){
                width -= 50;
                root.setLayoutParams(new TableRow.LayoutParams(width/2, width/2));
                HungryClient.image(mContext, this.image, mImgList.get(position));
            }
            else if(mode == ImageAdapter.Mode.UPLOAD_IMAGE)
                this.image.setImageBitmap(new Utility().uriTobitmap(mContext, Uri.parse(mImgList.get(position)),width/ 2, width / 2));

        }

        /**
         * set triple image
         */
        public void setTriple(int position) {
            int width = new Utility().screenWidth(mContext);

            if(mode == ImageAdapter.Mode.ATTACHED_IMAGE) {
                HungryClient.image(mContext, this.image, mImgList.get(position));
                width -= 50;
                root.setLayoutParams(new TableRow.LayoutParams(width/3, width/3));
            }

            else if(mode == ImageAdapter.Mode.UPLOAD_IMAGE){
                this.image.setImageBitmap(new Utility().uriTobitmap(mContext, Uri.parse(mImgList.get(position)), width/ 3, width / 3));
            }


        }

        /**
         * set quadruple image
         */
        public void setQuadruple(int position) {
            int width = new Utility().screenWidth(mContext);
            if(mode == ImageAdapter.Mode.ATTACHED_IMAGE){
                HungryClient.image(mContext, this.image, mImgList.get(position));
                width -= 50;
                root.setLayoutParams(new TableRow.LayoutParams(width/2, width/2));
            }
            else if(mode == ImageAdapter.Mode.UPLOAD_IMAGE){
                this.image.setImageBitmap(new Utility().uriTobitmap(mContext, Uri.parse(mImgList.get(position)), width /2, width/2));
            }


        }

        /**
         * set pentuple image
         */
        public void setPentuple(int position) {
            int width = new Utility().screenWidth(mContext);

            if(mode == ImageAdapter.Mode.ATTACHED_IMAGE)  {
                HungryClient.image(mContext, this.image, mImgList.get(position));
                width -= 50;
                if(position == 0 ) root.setLayoutParams(new TableRow.LayoutParams(width/2, width/2));
                else root.setLayoutParams(new TableRow.LayoutParams(width/3, width/3));
            }
            else if(mode == ImageAdapter.Mode.UPLOAD_IMAGE) {
                if(position == 0)   this.image.setImageBitmap(new Utility().uriTobitmap(mContext, Uri.parse(mImgList.get(position)), width / 2 , width / 2));
                else this.image.setImageBitmap(new Utility().uriTobitmap(mContext, Uri.parse(mImgList.get(position)), width / 3 , width / 3));
            }


        }



    }


    public static class Mode {
        public static final int ATTACHED_IMAGE = 0;
        public static final int UPLOAD_IMAGE = 1;
    }

}
