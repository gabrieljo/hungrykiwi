package app.me.hungrykiwi.component.viewutil;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.component.attached_image.ImageAdapter;
import app.me.hungrykiwi.pages.recipe.RecipeAdapter;
import app.me.hungrykiwi.service.FileBaseDB;

/**
 * view helper class to support more versetile views
 * Created by user on 10/12/2016.
 */
public class ViewUtil {


    public RecyclerView getImageRecycler(Context context, String[] imgs, int mode) {
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.recyclerview, null);
        GridLayoutManager manager = new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new ImageItemDecoration(10));

        recyclerView.setAdapter(new ImageAdapter(context, imgs).setMode(mode));
        return recyclerView;
    }

    public void setImageRecyclerGridLayout(RecyclerView recycler, int size) {
        if(recycler.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) recycler.getLayoutManager();
            switch(size) {
                case 1:
                    manager.setSpanCount(1);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return 1;
                        }
                    });
                    break;
                case 2:
                    manager.setSpanCount(2);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return 1;
                        }
                    });
                    break;
                case 3:
                    manager.setSpanCount(3);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return 1;
                        }
                    });
                    break;
                case 4:
                    manager.setSpanCount(2);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return 1;
                        }
                    });
                    break;
                case 5:
                    manager.setSpanCount(6);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            if(position < 2) return 3;
                            else return 2;
                        }
                    });
                    break;
            }

        }
    }



    /**
     * edit dialog
     * @param context
     * @param title
     * @param content
     * @param positive
     */
    public EditText editDialog(Context context, String title, String content, EditText input, AlertDialog.OnClickListener positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(title != null) builder.setTitle(title);
        if(content != null) input.setHint(content);
        builder.setView(input);
        if(positive !=null) builder.setPositiveButton("Yes", positive);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
        return input;
    }

    /**
     * make two choice dialog
     * @param context
     * @param title
     * @param message
     * @param strFirst
     * @param first
     * @param strSecond
     * @param second
     */
    public void twoChice(Context context, String title, String message, String strFirst, AlertDialog.OnClickListener first, String strSecond, AlertDialog.OnClickListener second) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);
        if(first != null) builder.setPositiveButton(strFirst, first);
        if(second != null) builder.setNegativeButton(strSecond, second);

        builder.create().show();
    }

    /**
     * set yes no confirm
     */
    public void confirmDialog(Context context, String title, String message, AlertDialog.OnClickListener positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(title != null) builder.setTitle(title);
        if(message != null) builder.setMessage(message);
        if(positive != null) builder.setPositiveButton("Yes", positive);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * multi choice dialog
     * @param context
     * @param title
     * @param options
     * @param positive
     */
    public void multichoiceDialog(Context context, String title, String[] options, DialogInterface.OnClickListener positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMultiChoiceItems(options, null, null);
        builder.setPositiveButton("Done", positive);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * show recipefilter dialog
     * @param context
     * @param title
     * @param positive
     */
    public void recipefilterDialog(final Context context, final RecipeAdapter adapter, String title, DialogInterface.OnClickListener positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recipe_filter, null);

        FileBaseDB db = new FileBaseDB();
        final int selectedMin = db.getRecipeFilterMin(context);
        final boolean selectedVegi = db.getRecipeFilterVegi(context);
        int selectedRbTime = R.id.rbAny;
        int selectedRbVegi = R.id.rbVegiNo;
        switch (selectedMin) {
            case 15:
                selectedRbTime = R.id.rb15;
                break;
            case 30:
                selectedRbTime = R.id.rb30;
                break;
            case 45:
                selectedRbTime = R.id.rb45;
                break;
            case 60:
                selectedRbTime = R.id.rb60;
                break;
        }
        if(selectedVegi == true) selectedRbVegi = R.id.rbVegiYes;
        final RadioGroup rgMin = (RadioGroup)view.findViewById(R.id.rg_min);
        rgMin.check(selectedRbTime);
        final RadioGroup rgVegi = (RadioGroup)view.findViewById(R.id.rg_vegi);
        rgVegi.check(selectedRbVegi);
        builder.setView(view);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int mId = rgMin.getCheckedRadioButtonId();
                int vId = rgVegi.getCheckedRadioButtonId();
                int min = 0;
                boolean vegi = false;
                switch (mId) {
                    case R.id.rb15:
                        min = 15;
                        break;
                    case R.id.rb30:
                        min = 30;
                        break;
                    case R.id.rb45:
                        min = 45;
                        break;
                    case R.id.rb60:
                        min = 60;
                        break;
                }
                switch (vId) {
                    case R.id.rbVegiYes:
                        vegi = true;
                        break;
                }
                if(min != selectedMin || vegi != selectedVegi) {
                    FileBaseDB db = new FileBaseDB();
                    db.setRecipeFilterMin(context, min);
                    db.setRecipeFilterVegi(context, vegi);
                    adapter.refresh();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }



    class ImageItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public ImageItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.right = this.space;
            outRect.left = this.space;
            outRect.bottom = this.space;
            outRect.top = 0;

        }
    }
}
