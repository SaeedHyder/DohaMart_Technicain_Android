package com.ingic.ezhalbatek.technician.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ingic.ezhalbatek.technician.entities.UserEnt;
import com.ingic.ezhalbatek.technician.retrofit.GsonFactory;


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
}
