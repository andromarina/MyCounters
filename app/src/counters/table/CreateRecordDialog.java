package counters.table;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;
import com.example.MyCounters.R;
import model.Record;

import java.util.Date;

/**
 * Created by mara on 1/12/15.
 */
public class CreateRecordDialog {
    private AlertDialog.Builder builder;
    private int categoryId;
    private CloseListener closeListener;
    private Context context;

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
        builder.setTitle(R.string.meters);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new OnOKListener(input));
        builder.setNegativeButton("Cancel", new OnCancelListener());
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
        private EditText input;

        public OnOKListener(EditText input) {
            this.input = input;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String value = input.getText().toString();
            Record result = createRecord(value.replaceAll("\\s", ""), categoryId);
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
