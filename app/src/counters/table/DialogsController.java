package counters.table;

import android.content.Context;
import android.util.Log;
import counters.database.Repository;
import model.Record;
import model.Tariff;

/**
 * Created by mara on 2/8/15.
 */
public class DialogsController implements CreateRecordDialog.CloseListener, MyDatePickerDialog.DateChangedListener,
        UpdateTariffDialog.CloseListener
{
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
        MyDatePickerDialog dialog = new MyDatePickerDialog(this.context, record);
        dialog.setDateChangedListener(this);
        dialog.show();
    }

    public void showUpdateTariffDialog() {
        //TODO: only one tariff range hardcoded
        double oldPrice = this.repository.getTariff(this.categoryId).getRanges().get(0).getPrice();
        UpdateTariffDialog dialog = new UpdateTariffDialog(this.context, this.categoryId, oldPrice);
        dialog.setOnCloseListener(this);
        dialog.show();
    }

    @Override
    public void OnClose(Record record) {
        if(record == null) {
           return;
        }
        Record old = this.repository.findRecordByDate(record);
        if( old != null) {
            this.repository.updateRecord(old.copyValues(record));
        }
        else {
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

    @Override
    public void OnClose(Tariff tariff) {
        if(tariff != null) {
            this.repository.updateTariff(tariff);
        }
    }
}
