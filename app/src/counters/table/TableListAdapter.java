package counters.table;

import android.content.Context;
import android.text.Html;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.example.MyCounters.R;
import counters.categories.CategoriesHelper;
import counters.database.Repository;
import model.*;

import java.text.SimpleDateFormat;

/**
 * Created by mara on 1/11/15.
 */
public class TableListAdapter extends ArrayAdapter<Record> implements View.OnLongClickListener, View.OnClickListener,
        TextView.OnEditorActionListener, PropertyChangedListener<Record>, CollectionChangedListener {
    private DialogsController dialogsController;
    private Repository repository;
    private int categoryId;

    public TableListAdapter(Context context, int resource, int categoryId,
                          DialogsController dialogsController, Repository repository) {
        super(context, resource, repository.getRecordsByCategoryId(categoryId));
        this.categoryId = categoryId;
        this.dialogsController = dialogsController;
        this.repository = repository;
        this.repository.addCollectionChangedListener(this);
        subscribeForExistingRecords();
    }

    @Override
    public View getView(int position, View item, ViewGroup parent) {

        if (item == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.row, null);
        }
        if(position % 2 == 0) {
            item.setBackgroundColor(CategoriesHelper.getColorLight(this.categoryId));
        }
        final Record record = super.getItem(position);
        item.setOnLongClickListener(this);
        item.setTag(record);
        TextView dateTV = (TextView) item.findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        String formattedDate = sdf.format(record.getDate());
        dateTV.setText(formattedDate);
        dateTV.setOnClickListener(this);
        dateTV.setOnLongClickListener(this);
        dateTV.setTag(record);

        EditText value = (EditText) item.findViewById(R.id.value);
        value.setText(Integer.toString(record.getValue()));
        value.setOnEditorActionListener(this);
        value.setOnLongClickListener(this);
        value.setTag(record);

        TextView diff = (TextView) item.findViewById(R.id.diff);
        String diffText = String.valueOf(record.getDiff());
        diff.setText(diffText);
        diff.setOnLongClickListener(this);
        diff.setTag(record);

        TextView unit = (TextView) item.findViewById(R.id.unit);
        unit.setText(Html.fromHtml(CategoriesHelper.getUnits(this.categoryId)));

        return item;
    }

    //whole row listener
    @Override
    public boolean onLongClick(View v) {
        PopupMenu p = new PopupMenu(getContext(), v);
        p.getMenuInflater().inflate(
                R.menu.popup_menu, p.getMenu());
        p.show();
        final Record rec = (Record) v.getTag();
        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                repository.deleteRecord(rec);
                return true;
            }
        });
        return true;
    }

    //Date TextView listener
    @Override
    public void onClick(View v) {
        Record r = (Record) v.getTag();
       this.dialogsController.showDatePickerDialog(r);
    }

    //Value edit text listener
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Record rec = (Record) v.getTag();
        rec.setValue(Integer.parseInt(v.getText().toString()));
        this.repository.updateRecord(rec);
        return true;
    }

    @Override
    public void OnPropertyChanged(Record record) {
        super.clear();
        super.addAll(this.repository.getRecordsByCategoryId(this.categoryId));
        notifyDataSetChanged();
    }

    @Override
    public void OnRecordAdded(Record record) {
        if(record.getCategoryId() != this.categoryId) {
            return;
        }
        super.clear();
        super.addAll(this.repository.getRecordsByCategoryId(this.categoryId));
        super.notifyDataSetChanged();
        record.addUpdateListener(this);
    }

    @Override
    public void OnFilterChanged() {
        super.clear();
        super.addAll(this.repository.getRecordsByCategoryId(this.categoryId));
    }

    @Override
    public void OnRecordRemoved(Record record) {
        if(record.getCategoryId() != this.categoryId) {
            return;
        }
        super.clear();
        super.addAll(this.repository.getRecordsByCategoryId(this.categoryId));
        super.notifyDataSetChanged();
    }


    private void subscribeForExistingRecords() {
        for(int i = 0; i < super.getCount(); ++i) {
            super.getItem(i).addUpdateListener(this);
        }
    }
}
