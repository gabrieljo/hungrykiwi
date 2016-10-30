package app.me.hungrykiwi.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;

import app.me.hungrykiwi.R;

/**
 * Created by user on 10/26/2016.
 */
public class FileBaseDB {
    public void setRecipeFilterMin(Context context, int min) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putInt(context.getResources().getString(R.string.key_recipe_min), min).commit();

    }

    public int getRecipeFilterMin(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(context.getResources().getString(R.string.key_recipe_min), 0);
    }

    public void setRecipeFilterVegi(Context context, boolean vegi) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putBoolean(context.getResources().getString(R.string.key_recipe_vegi), vegi).commit();

    }

    public boolean getRecipeFilterVegi(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(context.getResources().getString(R.string.key_recipe_vegi), false);
    }

    public void setRecipeFilterCriterial(Context context, int value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putInt(context.getResources().getString(R.string.key_recipe_critetial), value).commit();
    }

    public int getRecipeFilterCriterial(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(context.getResources().getString(R.string.key_recipe_critetial), 0);
    }
}
