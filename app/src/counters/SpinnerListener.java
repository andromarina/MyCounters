package counters;

import android.view.View;
import android.widget.AdapterView;
import model.TimePeriodsUtils;
import model.filters.TimePeriodFilter;

/**
 * Created by mara on 7/5/15.
 */
public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String period = (String) parent.getItemAtPosition(position);
        TimePeriodFilter filter = new TimePeriodFilter(100);
        if(period.equals(TimePeriodsUtils.SIX_MONTHES)) {
            filter = new TimePeriodFilter(6);
        }
        else if(period.equals(TimePeriodsUtils.TWELVE_MONTHES)) {
            filter = new TimePeriodFilter(12);
        }
        else if(period.equals(TimePeriodsUtils.TWENTY_FOUR_MONTHES)) {
            filter = new TimePeriodFilter(24);
        }
        CountersApplication.getRepository().changeFilter(filter);
        Preferences.saveSpinnerPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
