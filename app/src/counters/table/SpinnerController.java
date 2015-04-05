package counters.table;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import counters.CountersApplication;
import model.TimePeriodsUtils;
import model.filters.TimePeriodFilter;

/**
 * Created by mara on 3/1/15.
 */
public class SpinnerController implements AdapterView.OnItemSelectedListener {
    private Context context;

    public SpinnerController(Context context) {
        this.context = context;
    }

    public ArrayAdapter<String> createAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item,
                TimePeriodsUtils.getAllTimePeriods());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        return this;
    }

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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
