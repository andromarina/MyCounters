package counters;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.*;
import com.counters.chart.R;

import java.util.Currency;
import java.util.List;

/**
 * Created by mara on 3/25/15.
 */
public class UserSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MyPreferenceFragment()).commit();

    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return MyPreferenceFragment.class.getName().equals(fragmentName);
    }


    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.fragment_preference);
            configureCurrenciesPref();
           // configureNotificationsCB();
        }

        private ListPreference configureCurrenciesPref() {
            ListPreference currencyPreference = (ListPreference) findPreference("currency");
            List< String > currencies = Utils.getAvailableCurrencies();
            String[] entries = new String[currencies.size()];
            for(int i = 0; i < currencies.size(); ++i) {
                String curName = currencies.get(i);
                entries[i] = curName;
            }
            currencyPreference.setEntries(entries);
            currencyPreference.setEntryValues(entries);
            currencyPreference.setDefaultValue(Preferences.getSavedCurrency());
            currencyPreference.setSummary(Preferences.getSavedCurrency().getSymbol());
            return currencyPreference;
        }

        private CheckBoxPreference configureNotificationsCB() {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("notificationsChBox");
            checkBoxPreference.setChecked(Preferences.getNotificationsFlag());
            return checkBoxPreference;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if (key.equals(Preferences.CURRENCY)) {
                Preference pref = findPreference(key);
                Currency cur = Currency.getInstance(sharedPreferences.getString(key, ""));
                pref.setSummary(cur.getSymbol());
                Preferences.saveCurrency(cur);
            }
            else if(key.equals(Preferences.NOTIFICATIONS)) {
                boolean flag = sharedPreferences.getBoolean(key, true);
                Preferences.saveNotificationsFlag(flag);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
