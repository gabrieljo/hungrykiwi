package app.me.hungrykiwi.pages.restaurant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.user.Restr;

/**
 * restraurant holder
 * Created by user on 10/18/2016.
 */
public class RestrHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView imageRestr, imageLocation, imageMap, imagePhone;
    TextView textName, textAddress, textCuisine, textRate;
    Context mContext;
    public RestrHolder(View view, Context mContext) {
        super(view);
        this.mContext = mContext;
        this.imageRestr = (ImageView)view.findViewById(R.id.imageRestr);
        this.imageLocation = (ImageView)view.findViewById(R.id.imgLocation);
        this.imageMap = (ImageView)view.findViewById(R.id.imgMap);
        this.imagePhone = (ImageView)view.findViewById(R.id.imgPhone);
        this.textName = (TextView)view.findViewById(R.id.textName);
        this.textAddress = (TextView)view.findViewById(R.id.textAddress);
        this.textCuisine = (TextView)view.findViewById(R.id.textCuisine);
        this.textRate = (TextView)view.findViewById(R.id.textRating);

    }

    public void set(Restr restr) {
        String img = restr.getImg();
        if(img != null && img.isEmpty() == false) HungryClient.restrImage(this.mContext, this.imageRestr, restr.getImg());
        if(restr.getPhone() == null || restr.getPhone().length() == 0) this.imagePhone.setVisibility(View.GONE);
        this.textName.setText(restr.getName());
        this.textAddress.setText(restr.getAddress());
        this.textCuisine.setText(restr.getCuisine());
        this.textRate.setText(String.valueOf(restr.getRating()));
        this.imagePhone.setOnClickListener(this);
        this.imageMap.setOnClickListener(this);
        this.imageLocation.setOnClickListener(this);
        this.textName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLocation:
                break;
            case R.id.imgPhone:
                break;
            case R.id.imgMap:
                break;

        }
    }
}
