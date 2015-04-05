package counters.table;

import android.content.Context;
import android.util.Log;
import model.Record;
import counters.database.Repository;

/**
 * Created by mara on 2/8/15.
 */
public class DialogsController implements CreateRecordDialog.CloseListener, MyDataPickerDialog.DateChangedListener {
    private Context context;
    private Repository repository;
    private int categoryId;

    public DialogsController(Context context, Repository repository, int categoryId) {
        this.context = context;
        this.repository = repository;
        this.categoryId = categoryId;
    }

    public void showAddRecordDialog() {
        CreateRecordDialog dialog = new CreateRecordDialog(this.context, this.categoryId);
        dialog.setOnCloseListener(this);
        dialog.show();
    }

    public void showDatePickerDialog(Record record) {
        MyDataPickerDialog dialog = new MyDataPickerDialog(this.context, record);
        dialog.setDateChangedListener(this);
        dialog.show();
    }

    @Override
    public void OnClose(Record record) {
        if(record != null) {
            this.repository.insertRecord(record);
        }
    }

    @Override
    public void OnDateChanged(Record record) {
        Log.d("DialogsController", "OnDataChanged: " + record.getDate());
        Record recordReal = this.repository.getRecordById(record.getId(), record.getCategoryId());
        recordReal.setDate(record.getDate());
        this.repository.updateRecord(recordReal);
    }
}
