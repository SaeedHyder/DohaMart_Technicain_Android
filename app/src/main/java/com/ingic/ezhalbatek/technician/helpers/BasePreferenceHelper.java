package com.ingic.ezhalbatek.technician.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ingic.ezhalbatek.technician.activities.MainActivity;
import com.ingic.ezhalbatek.technician.entities.UserEnt;
import com.ingic.ezhalbatek.technician.retrofit.GsonFactory;

import java.util.Locale;


public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";

    private static final String FILENAME = "preferences";

    protected static final String Firebase_TOKEN = "Firebasetoken";

    protected static final String NotificationCount = "NotificationCount";

    protected static final String KEY_USER = "KEY_USER";

    protected static final String KeyIsRequest = "KeyIsRequest";
    protected static final String KeyIsCustomerMaster = "KeyIsCustomerMaster";
    protected static final String KeyIsPayment = "KeyIsPayment";
    protected static final String KeyIsHistory = "KeyIsHistory";
    protected static final String KEY_DEFAULT_LANG = "keyLanguage";
    protected static final String KEY_LANGUAGE_STATUS = "Islanguage";


    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }


    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }
    public int getNotificationCount() {
        return getIntegerPreference(context, FILENAME, NotificationCount);
    }

    public void setNotificationCount(int count) {
        putIntegerPreference(context, FILENAME, NotificationCount, count);
    }

    public UserEnt getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), UserEnt.class);
    }

    public void putUser(UserEnt user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public boolean isLanguageArabic() {
        return false;
    }

    public void setIsFromRequest( boolean isStudio ) {
        putBooleanPreference( context, FILENAME, KeyIsRequest, isStudio );
    }

    public boolean isRequest() {
        return getBooleanPreference(context, FILENAME, KeyIsRequest);
    }

    public void setIsFromCustomerMasterSubscriber(boolean isCustomerMaster ) {
        putBooleanPreference( context, FILENAME, KeyIsCustomerMaster, isCustomerMaster );
    }

    public boolean isFromCustomerMasterSub() {
        return getBooleanPreference(context, FILENAME, KeyIsCustomerMaster);
    }

    public void setIsFromPaymentSubscriber(boolean isCustomerMaster ) {
        putBooleanPreference( context, FILENAME, KeyIsPayment, isCustomerMaster );
    }

    public boolean isFromPaymentSub() {
        return getBooleanPreference(context, FILENAME, KeyIsPayment);
    }

    public void setIsFromHistorySubscriber(boolean isCustomerMaster ) {
        putBooleanPreference( context, FILENAME, KeyIsHistory, isCustomerMaster );
    }

    public boolean isFromHistorySub() {
        return getBooleanPreference(context, FILENAME, KeyIsHistory);
    }

    public void putLang(Activity activity, String lang) {
        Log.v("lang", "|" + lang);
        Resources resources = context.getResources();

        if (lang.equals("ar")){
            lang = "ar";}
        else{
            lang = "en";}

        putStringPreference(context, FILENAME, KEY_DEFAULT_LANG, lang);
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        Locale locale=new Locale(lang);
        // conf.setLayoutDirection(locale);
        conf.locale = locale;
        conf.setLayoutDirection(Locale.ENGLISH);
        resources.updateConfiguration(conf, dm);

        ((MainActivity) activity).restartActivity();

    }



    public String getLang() {
        return getStringPreference(context, FILENAME, KEY_DEFAULT_LANG);
    }

    public boolean isLanguageArabian() {
        return getLang().equalsIgnoreCase("ar");
    }

    public boolean isLanguageSelected() {
        return getBooleanPreference(context, FILENAME, KEY_LANGUAGE_STATUS);
    }


    public void setLanguageSelected( boolean isSocial ) {
        putBooleanPreference( context, FILENAME, KEY_LANGUAGE_STATUS, isSocial );
    }

}
