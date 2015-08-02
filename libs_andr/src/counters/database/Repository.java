package counters.database;

import android.content.Context;
import counters.Preferences;
import counters.Utils;
import model.*;
import model.filters.EmptyFilter;
import model.filters.IFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mara on 2/7/15.
 */
public class Repository implements IRepository{
    private RepositoryHelper helper;
    private Map<Integer, ArrayList<Record>> recordsCollections = new HashMap<Integer, ArrayList<Record>>();
    private Map<Integer, Tariff> tariffsCollection = new HashMap<Integer, Tariff>();
    private ArrayList<CollectionChangedListener> listeners = new ArrayList<CollectionChangedListener>();
    private IFilter filter;

    public Repository(Context context) {
        this.helper = new RepositoryHelper(context);
        this.filter = new EmptyFilter();
      //  insertAllRecords();
    }

    private void insertAllRecords() {
        ArrayList<Record> list = new ArrayList<Record>();

        list.add(new Record(convertToMsec("1/5/2014"), 0, 2)); // water
        list.add(new Record(convertToMsec("2/5/2014"), 8, 2)); // water
        list.add(new Record(convertToMsec("3/11/2014"), 14, 2)); // water
        list.add(new Record(convertToMsec("4/9/2014"), 20, 2)); // water
        list.add(new Record(convertToMsec("5/9/2014"), 27, 2)); // water
        list.add(new Record(convertToMsec("6/7/2014"), 34, 2)); // water
        list.add(new Record(convertToMsec("7/5/2014"), 40, 2)); // water
        list.add(new Record(convertToMsec("8/5/2014"), 47, 2)); // water
        list.add(new Record(convertToMsec("9/5/2014"), 55, 2)); // water
        list.add(new Record(convertToMsec("10/5/2014"), 59, 2)); // water
        list.add(new Record(convertToMsec("11/5/2014"), 67, 2)); // water
        list.add(new Record(convertToMsec("12/5/2014"), 75, 2)); // water
        list.add(new Record(convertToMsec("1/5/2015"), 84, 2)); // water
        list.add(new Record(convertToMsec("2/7/2015"), 91, 2)); // water

        list.add(new Record(convertToMsec("1/5/2014"), 318, 3)); // gas
        list.add(new Record(convertToMsec("2/5/2014"), 336, 3)); // gas
        list.add(new Record(convertToMsec("3/11/2014"), 352, 3)); // gas
        list.add(new Record(convertToMsec("4/9/2014"), 369, 3)); // gas
        list.add(new Record(convertToMsec("5/9/2014"), 386, 3)); // gas
        list.add(new Record(convertToMsec("6/7/2014"), 404, 3)); // gas
        list.add(new Record(convertToMsec("7/5/2014"), 417, 3)); // gas
        list.add(new Record(convertToMsec("8/5/2014"), 435, 3)); // gas
        list.add(new Record(convertToMsec("9/5/2014"), 452, 3)); // gas
        list.add(new Record(convertToMsec("10/5/2014"), 462, 3)); // gas
        list.add(new Record(convertToMsec("11/5/2014"), 482, 3)); // gas
        list.add(new Record(convertToMsec("12/5/2014"), 503, 3)); // gas
        list.add(new Record(convertToMsec("1/5/2015"), 530, 3)); // gas
        list.add(new Record(convertToMsec("2/7/2015"), 549, 3)); // gas

        list.add(new Record(convertToMsec("1/5/2014"), 3251, 1)); // elect
        list.add(new Record(convertToMsec("2/5/2014"), 3296, 1)); // elect
        list.add(new Record(convertToMsec("3/11/2014"), 3343, 1)); // elect
        list.add(new Record(convertToMsec("4/9/2014"), 3388, 1)); // elect
        list.add(new Record(convertToMsec("5/9/2014"), 3434, 1)); // elect
        list.add(new Record(convertToMsec("6/7/2014"), 3478, 1)); // elect
        list.add(new Record(convertToMsec("7/5/2014"), 3514, 1)); // elect
        list.add(new Record(convertToMsec("8/5/2014"), 3554, 1)); // elect
        list.add(new Record(convertToMsec("9/5/2014"), 3594, 1)); // elect
        list.add(new Record(convertToMsec("10/5/2014"), 3633, 1)); // elect
        list.add(new Record(convertToMsec("11/5/2014"), 3680, 1)); // elect
        list.add(new Record(convertToMsec("12/5/2014"), 3727, 1)); // elect
        list.add(new Record(convertToMsec("1/5/2015"), 3791, 1)); // elect
        list.add(new Record(convertToMsec("2/7/2015"), 3846, 1)); // elect

        for(Record rec : list) {
            insertRecord(rec);
        }
    }

    private Date convertToMsec(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {

            Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmpty(int categoryId) {
        ArrayList<Record> records = getUnfilteredRecordsByCategoryId(categoryId);
        if(records.size() > 0) {
            return false;
        }
        return true;
    }

    public boolean isRepositoryEmpty() {
        for(Category category : getAllCategories()) {
            if(!isEmpty(category.getId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<Record> getRecordsByCategoryId(int id) {
        ArrayList<Record> records = getUnfilteredRecordsByCategoryId(id);
        ArrayList<Record> result = new ArrayList<Record>();
        for(Record rec : records) {
            if(this.filter.match(rec)){
                result.add(rec);
            }
        }
        return result;
    }

    public ArrayList<Record> getAllRecords() {
        ArrayList<Record> result = new ArrayList<Record>();
        for(Category cat : getAllCategories()) {
            ArrayList<Record> records = getRecordsByCategoryId(cat.getId());
            for(Record rec : records) {
                result.add(rec);
            }
        }
        return result;
    }

    public void changeFilter(IFilter filter) {
        this.filter = filter;
        notifyAllFilterChanged();
    }

    public Tariff getTariff (int categoryId)
    {
        if(tariffsCollection.containsValue(categoryId)) {
            return tariffsCollection.get(categoryId);
        }
        Tariff tariff = new Tariff(categoryId);
        ArrayList<TariffRange> ranges = this.helper.getRangesByCategoryId(categoryId);
        for(TariffRange range : ranges) {
            tariff.addTariffRange(range);
        }
        tariffsCollection.put(categoryId, tariff);
        return tariff;
    }

    public void updateTariff (Tariff tariff) {
        int categoryId = tariff.getCategoryId();
        this.helper.deleteTariffRangesByCategoryId(categoryId);
        for(TariffRange range : tariff.getRanges()) {
            this.helper.insertTariffRange(range, categoryId);
        }
        tariffsCollection.put(categoryId, tariff);
        tariff.calc(getRecordsByCategoryId(categoryId));
    }

    private ArrayList<Record> getUnfilteredRecordsByCategoryId(int id) {
        if (recordsCollections.containsKey(id)) {
            return recordsCollections.get(id);
        }
        ArrayList<Record> records = this.helper.getRecordsByCategoryId(id);
        Collections.sort(records);
      //  records = Utils.groupRecords(records);
        Calculator.recalculateDiff(records);
        getTariff(id).calc(records);
        recordsCollections.put(id, records);
        return records;
    }

    public void addCollectionChangedListener(CollectionChangedListener listener) {
        this.listeners.add(listener);
    }

    public long insertRecord(Record record) {
        if (!recordsCollections.containsKey(record.getCategoryId())) {
            recordsCollections.put(record.getCategoryId(), new ArrayList<Record>());
        }
        long result = this.helper.insertRecord(record);
        if (result > 0) {
            int categoryId = record.getCategoryId();
            ArrayList<Record> records = recordsCollections.get(categoryId);
            records.add(record);
            Collections.sort(records);
          //  records = Utils.groupRecords(records);
            Calculator.recalculateDiff(records);
            getTariff(record.getCategoryId()).calc(records);
            Preferences.saveLastRecordDate(record.getDate());
            notifyAllRecordInserted(record);
        }
        return result;
    }

    public Record findRecordByDate(Record record) {
        int catId = record.getCategoryId();
        ArrayList<Record> records = this.getRecordsByCategoryId(catId);
        for(Record rec : records) {
            if(Utils.compareDate(rec.getDate(), record.getDate()) == 0) {
                return rec;
            }
        }
        return null;
    }

    public int deleteRecord(Record record) {
        int result = this.helper.deleteRecord(record);
        if (result >= 0) {
            ArrayList<Record> records = recordsCollections.get(record.getCategoryId());
            records.remove(record);
            Collections.sort(records);
            Calculator.recalculateDiff(records);
            notifyAllRecordDeleted(record);
        }
        return result;
    }

    public int updateRecord(Record record) {
        int categoryId = record.getCategoryId();
        ArrayList<Record> records = recordsCollections.get(categoryId);
        if (!records.contains(record)) {
            throw new RuntimeException("Trying to update not existing record!");
        }
        int result = this.helper.updateRecord(record);
        Collections.sort(records);
      //  records = Utils.groupRecords(records);
        Calculator.recalculateDiff(records);
        getTariff(record.getCategoryId()).calc(records);
        return result;
    }

    public ArrayList<Category> getAllCategories() {
        return this.helper.getAllCategories();
    }

    public Category getCategoryById(int id) {
        return this.helper.getCategoryById(id);
    }

    public Record getRecordById(int recordId, int categoryId) {
        ArrayList<Record> records = this.recordsCollections.get(categoryId);
        for(Record record : records) {
            if(record.getId() == recordId) {
                return record;
            }
        }
        return null;
    }

    private void notifyAllRecordInserted(Record record) {
        for (CollectionChangedListener lis : listeners) {
            lis.OnRecordAdded(record);
        }
    }

    private void notifyAllRecordDeleted(Record record) {
        for (CollectionChangedListener lis : listeners) {
            lis.OnRecordRemoved(record);
        }
    }

    private void notifyAllFilterChanged() {
        for (CollectionChangedListener lis : listeners) {
            lis.OnFilterChanged();
        }
    }
}
