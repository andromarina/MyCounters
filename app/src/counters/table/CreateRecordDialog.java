package counters.table;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.counters.chart.R;
import model.Record;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mara on 1/12/15.
 */
public class CreateRecordDialog implements DatePicker.OnDateChangedListener {
    private AlertDialog.Builder builder;
    private int categoryId;
    private CloseListener closeListener;
    private Context context;
    private Date pickerDate = Calendar.getInstance().getTime();

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);
        this.pickerDate.setTime(cal.getTimeInMillis());
    }

    public interface CloseListener {
        public void OnClose(Record record);
    }

    public CreateRecordDialog(Context context, int categoryId) {
        this.categoryId = categoryId;
        this.context = context;
        this.builder = create();
    }

    public void setOnCloseListener(CloseListener listener) {
        this.closeListener = listener;
    }

    private AlertDialog.Builder create() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.create_record);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText recordValue = new EditText(context);
        recordValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        recordValue.setHint(R.string.record_value);
        layout.addView(recordValue);
        layout.setPadding(30, 0, 30, 25);

        DatePicker date = new DatePicker(context);
        date.setCalendarViewShown(false);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        date.init(year, month, day, this);
        layout.addView(date);

        builder.setView(layout);
        builder.setPositiveButton("OK", new OnOKListener(recordValue));
        builder.setNegativeButton("Cancel", new OnCancelListener());
        builder.create();
        return builder;
    }

    public void show() {
        this.builder.show();
    }

    private Record createRecord(String value, int categoryID) {
        try {
            int val = Integer.parseInt(value);
            Record record = new Record(new Date(), val, categoryID);
            return record;
        }
        catch (NumberFormatException ex) {
            Toast.makeText(this.context, R.string.not_valid_value, Toast.LENGTH_LONG).show();
        }
        return null;
    }


    private class OnOKListener implements DialogInterface.OnClickListener {
        private EditText recordValue;

        public OnOKListener(EditText recordValue) {
            this.recordValue = recordValue;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String value = recordValue.getText().toString();
            Record result = createRecord(value.replaceAll("\\s", ""), categoryId);
            if (result != null) {
                result.setDate(pickerDate);
            }
            closeListener.OnClose(result);
        }
    }

    private class OnCancelListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            closeListener.OnClose(null);
        }
    }


}
