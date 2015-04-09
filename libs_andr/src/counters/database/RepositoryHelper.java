package counters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.Category;
import model.Record;
import model.TariffRange;

import java.util.ArrayList;

/**
 * Created by mara on 1/2/15.
 */
public class RepositoryHelper {
    private SQLiteDatabase db;

    public RepositoryHelper(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        Cursor c = db.query(Contracts.Categories.TABLE_NAME, null, null, null, null, null,
                Contracts.Categories._ID + " ASC");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            Category category = new Category(id, name);
            categories.add(category);
            c.moveToNext();
        }
        c.close();
        return categories;
    }

    public Category getCategoryById(int id) {
        String selection = Contracts.Categories._ID + "=" + id;
        Cursor c = db.query(Contracts.Categories.TABLE_NAME, null, selection, null, null, null,
                Contracts.Categories._ID + " ASC");
        c.moveToFirst();
        String categoryName = c.getString(1);
        c.close();
        Category category = new Category(id, categoryName);
        return category;
    }

    public int updateRecord(Record record) {

        ContentValues cv = new ContentValues();
        cv.put(Contracts.Records.COLUMN_DATE, record.getDate().getTime());
        cv.put(Contracts.Records.COLUMN_VALUE, record.getValue());
        String selection = Contracts.Records._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(record.getId()) };

        return db.update(Contracts.Records.TABLE_NAME,cv,selection,selectionArgs);
    }

    public long insertRecord(Record record) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Contracts.Records.COLUMN_DATE, record.getDate().getTime());
            cv.put(Contracts.Records.COLUMN_VALUE, record.getValue());
            cv.put(Contracts.Records.COLUMN_CATEGORY_ID, record.getCategoryId());
            cv.put(Contracts.Records.COLUMN_APARTMENT_ID, record.getApartmentId());
            return db.insertOrThrow(Contracts.Records.TABLE_NAME, null, cv);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.close();
            return -2;
        }
    }

    public int deleteRecord(Record record) {
        try {
            String where = Contracts.Records._ID + "=" + record.getId();
            return db.delete(Contracts.Records.TABLE_NAME, where, null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.close();
            return -1;
        }
    }

    public long insertTariffRange(TariffRange range, int categoryId) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Contracts.Tariffs.COLUMN_CATEGORY_ID, categoryId);
            cv.put(Contracts.Tariffs.COLUMN_MIN, range.getMin());
            cv.put(Contracts.Tariffs.COLUMN_MAX, range.getMax());
            cv.put(Contracts.Tariffs.COLUMN_PRICE, range.getPrice());
            return db.insertOrThrow(Contracts.Tariffs.TABLE_NAME, null, cv);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.close();
            return -2;
        }
    }

    public ArrayList<TariffRange> getRangesByCategoryId(int categoryId) {
        ArrayList<TariffRange> ranges = new ArrayList<TariffRange>();

        String selection = Contracts.Tariffs.COLUMN_CATEGORY_ID + "=" + categoryId;
        Cursor c = db.query(Contracts.Tariffs.TABLE_NAME, null, selection, null, null, null,
                Contracts.Tariffs._ID + " ASC");
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(Contracts.Tariffs._ID));
            int min = c.getInt(c.getColumnIndex(Contracts.Tariffs.COLUMN_MIN));
            int max = c.getInt(c.getColumnIndex(Contracts.Tariffs.COLUMN_MAX));
            double price = c.getDouble(c.getColumnIndex(Contracts.Tariffs.COLUMN_PRICE));
            TariffRange range = new TariffRange(id, min, max, price);
            ranges.add(range);
        }
        c.close();
        return ranges;
    }

//    public int updateTariffRange(TariffRange range) {
//
//        ContentValues cv = new ContentValues();
//        cv.put(Contracts.Tariffs.COLUMN_MIN, range.getMin());
//        cv.put(Contracts.Tariffs.COLUMN_MAX, range.getMax());
//        cv.put(Contracts.Tariffs.COLUMN_PRICE, range.getPrice());
//        String selection = Contracts.Tariffs._ID + " LIKE ?";
//        String[] selectionArgs = { String.valueOf(range.getId()) };
//
//        return db.update(Contracts.Tariffs.TABLE_NAME,cv,selection,selectionArgs);
//    }

    public int deleteTariffRangesByCategoryId (int categoryId) {
        try {
            String where = Contracts.Tariffs.COLUMN_CATEGORY_ID + "=" + categoryId;
            return db.delete(Contracts.Tariffs.TABLE_NAME, where, null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.close();
            return -1;
        }
    }

    public Record getRecordById(int recordId) {
        String selection = Contracts.Records._ID + "=" + recordId;
        Cursor c = db.query(Contracts.Records.TABLE_NAME, null, selection, null, null, null,
                Contracts.Records._ID + " ASC");
        c.moveToFirst();
        long recordDate = c.getLong(c.getColumnIndex(Contracts.Records.COLUMN_DATE));
        int value = c.getInt(c.getColumnIndex(Contracts.Records.COLUMN_VALUE));
        int categoryId = c.getInt(c.getColumnIndex(Contracts.Records.COLUMN_CATEGORY_ID));
        int apartmentId = c.getInt(c.getColumnIndex(Contracts.Records.COLUMN_APARTMENT_ID));
        c.close();
        Record record = new Record(recordId, recordDate, value, categoryId);
        return record;
    }

    public ArrayList<Record> getRecordsByCategoryId(int categoryId) {
        ArrayList<Record> records = new ArrayList<Record>();

        String selection = Contracts.Records.COLUMN_CATEGORY_ID + "=" + categoryId;
        String[] projection = {Contracts.Records._ID, Contracts.Records.COLUMN_DATE, Contracts.Records.COLUMN_VALUE};
        Cursor c = db.query(Contracts.Records.TABLE_NAME, projection, selection, null, null, null,
                Contracts.Categories._ID + " ASC");
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id = c.getInt(0);
            long date = c.getLong(1);
            int value = c.getInt(2);
            Record record = new Record(id, date, value, categoryId);
            records.add(record);
        }
        c.close();
        return records;
    }
}
