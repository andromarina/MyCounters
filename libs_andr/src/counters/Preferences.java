package counters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by mara on 4/13/15.
 */
public class Preferences {
    private static SharedPreferences sPref;
    private static Context context;
    private static final String LOG_TAG = "Preferences";
    public static final String CURRENCY = "currency";
    public static final String NOTIFICATIONS = "notificationsChBox";
    public static final String SPINNER_POSITION = "months";

    public static void initialize(Context context) {
        Preferences.context = context;
    }

    public static void saveCurrency(Currency currency) {
        sPref = context.getSharedPreferences(CURRENCY, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CURRENCY, currency.getCurrencyCode());
        ed.commit();
        Log.d(LOG_TAG, currency.getCurrencyCode() + " Currency saved");
    }

    public static Currency getSavedCurrency() {

        sPref = context.getSharedPreferences(CURRENCY, Context.MODE_PRIVATE);
        String savedCode = sPref.getString(CURRENCY, "");
        if(savedCode.equals("")) {
            return Currency.getInstance(Locale.getDefault());
        }
        return Currency.getInstance(savedCode);
    }

    public static void saveSpinnerPosition(int spinnerPosition) {
        sPref = context.getSharedPreferences(SPINNER_POSITION, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(SPINNER_POSITION, spinnerPosition);
        ed.commit();
        Log.d(LOG_TAG, "Saved spinner position: " + spinnerPosition);
    }

    public static int getSpinnerPosition() {
        sPref = context.getSharedPreferences(SPINNER_POSITION, Context.MODE_PRIVATE);
        int savedValue = sPref.getInt(SPINNER_POSITION, 0);
        return savedValue;
    }

    public static void saveNotificationsFlag(boolean flag) {
        sPref = context.getSharedPreferences(NOTIFICATIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(NOTIFICATIONS, flag);
        ed.commit();
        Log.d(LOG_TAG, "Saved notifications flag: " + flag);
    }

    public static boolean getNotificationsFlag() {
        sPref = context.getSharedPreferences(NOTIFICATIONS, Context.MODE_PRIVATE);
        boolean savedFlag = sPref.getBoolean(NOTIFICATIONS, true);
        return savedFlag;
    }
}
