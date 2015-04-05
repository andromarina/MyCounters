package counters.table;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import com.example.MyCounters.R;
import model.Record;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mara on 2/1/15.
 */
public class MyDataPickerDialog implements DatePickerDialog.OnDateSetListener, DialogInterface.OnClickListener {
    private DatePickerDialog datePickerDialog;
    private Record record;
    private DateChangedListener listener;
    private boolean isDoneClicked = false;

    public MyDataPickerDialog(Context context, Record record) {
        this.record = new Record(record.getId(), record.getDate(), record.getValue(),
                record.getCategoryId());
        create(context);
    }

    public interface DateChangedListener {
        public void OnDateChanged(Record record);
    }

    public void setDateChangedListener(DateChangedListener listener) {
        this.listener = listener;
    }

   private void create(Context context) {
       final Calendar c = Calendar.getInstance();
       c.setTime(this.record.getDate());

       // Create a new instance of TimePickerDialog and return it
       this.datePickerDialog = new DatePickerDialog(context, this, c.get(c.YEAR), c.get(c.MONTH), c.get(c.DAY_OF_MONTH));
       this.datePickerDialog.setTitle(R.string.measurement_date);
       this.datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Done", this);
       this.datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
   }

    public void show() {
        this.datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.record.setDate(formatDate(year, monthOfYear, dayOfMonth));
        Log.d("MyDataPickerDialog", "OnDateSet: " + this.record.getDate());
        if(isDoneClicked) {
            this.listener.OnDateChanged(this.record);
        }
    }

    private static Date formatDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, monthOfYear, dayOfMonth);
        Date date = cal.getTime();
        return date;
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        //flag is added because OnDateSet called after onDoneClick and record does not change value properly
        this.isDoneClicked = true;
    }
}
