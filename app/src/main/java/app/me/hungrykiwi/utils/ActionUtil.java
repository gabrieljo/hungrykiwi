package app.me.hungrykiwi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.nguyenhoanglam.imagepicker.activity.ImagePicker;

import java.io.File;


/**
 * utils for action like camera or gallery.
 * Created by user on 10/10/2016.
 */
public class ActionUtil {
    public static final int RC_CAMERA = 1; // CAMERA REQUEST CODE
    public static final int RC_GALLERY = 2; // CAMERA REQUEST CODE

    /**
     * activate camera to take picture to upload
     * @param fragment
     */
    public Uri goCamera(Fragment fragment) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String dirName = Environment.getExternalStorageDirectory()+ File.separator + Environment.DIRECTORY_PICTURES + File.separator + "hungry_kiwi" + File.separator;
        String fileName = "hungry_kiwi_"+String.valueOf(System.currentTimeMillis())+".jpg";
        File dir = new File(dirName);
        if(dir.exists() == false) dir.mkdirs();
        Uri uri = Uri.fromFile(new File(dirName + fileName));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(intent, RC_CAMERA);
        return uri;
    }

    /**
     * go to gallery to pick multiple images
     * @param fragment
     */
    public void goGallery(Fragment fragment, int limit) {
        ImagePicker.create(fragment)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Image") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(limit) // max images can be selected (99 by default)
                .showCamera(false) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .start(RC_GALLERY); // start image picker activity with request code
    }
}
