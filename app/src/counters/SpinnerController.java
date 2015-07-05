package counters;

import android.content.Context;
import android.widget.ArrayAdapter;
import model.TimePeriodsUtils;

/**
 * Created by mara on 3/1/15.
 */
public class SpinnerController {
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
}
