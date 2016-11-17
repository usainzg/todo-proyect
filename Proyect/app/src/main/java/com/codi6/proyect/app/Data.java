package com.codi6.proyect.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Locale;

/***********************************************************************************************
 * Class to manage select locale language info and manage preference data to app with one or more
 * values to use in correct app functionality
 ***********************************************************************************************/

public class Data {

    //Default constants values
    private static final String DEFAULT_PREFERENCES = "Default Preferences";
    private static final String LANG_PROPERTY = "Language";
    private static final String DEFAULT_LANG = "es";

    public static String getLocaleLanguage (Context context)
    {
        String langPref = LANG_PROPERTY;
        SharedPreferences prefs = context.getSharedPreferences(DEFAULT_PREFERENCES, Activity.MODE_PRIVATE);
        return prefs.getString(langPref, DEFAULT_LANG);
    }
    public static void loadLocale(Context context)
    {
        String langPref = LANG_PROPERTY;
        SharedPreferences prefs = context.getSharedPreferences(DEFAULT_PREFERENCES, Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, DEFAULT_LANG);
        changeLang(language, context);
    }

    public static void changeLang(String lang, Context context)
    {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang, context);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    private static void saveLocale(String lang, Context context)
    {
        String langPref = LANG_PROPERTY;
        SharedPreferences prefs = context.getSharedPreferences(DEFAULT_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public static SharedPreferences getPreferencesFile(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // METHODS TO GET AND SET PREFERENCES ON PREFERENCES FILE
    // GOOD PRACTICE
    public static String getPreference(Context context, String propertyName){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(propertyName, "");
    }
    public static void setPreference(Context context, String [] propertyNames
            , String [] propertyValues){
        SharedPreferences sharedPreferences = getPreferencesFile(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i< propertyNames.length; i ++)
        {
            editor.putString(propertyNames [i], propertyValues [i]);
        }

        editor.commit();
    }
}
