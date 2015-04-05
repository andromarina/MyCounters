package counters;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import com.example.MyCounters.R;

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

//    @Override
//    protected boolean isValidFragment(String fragmentName)
//    {
//        return MyPreferenceFragment.class.getName().equals(fragmentName);
//    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.fragment_preference);
            ListPreference sizePreference = (ListPreference) findPreference("currency");
         //   Currency[] entryValues = (Currency[]) Currency.getAvailableCurrencies().toArray();
//            String[] entries = new String[entryValues.length];
//            for(int i = 0; i < entries.length; ++i) {
//                Currency cur = entryValues[i];
//                String curName = cur.getSymbol();
//                entries[i] = curName;
//            }

        //    sizePreference.setEntries(entries);
          //  sizePreference.setDefaultValue(entryValues[0]);
        }
    }
}
