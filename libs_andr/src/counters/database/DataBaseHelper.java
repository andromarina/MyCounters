package counters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import model.Category;

import java.util.HashMap;

/**
 * Created by mara on 1/2/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DataBaseHelper.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Counters.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_CATEGORIES_TABLE =
            "CREATE TABLE " + Contracts.Categories.TABLE_NAME + " (" +
                    Contracts.Categories._ID + " INTEGER PRIMARY KEY," +
                    Contracts.Categories.COLUMN_CATEGORY_NAME + TEXT_TYPE + " )";
    private static final String SQL_CREATE_APARTMENTS_TABLE =
            "CREATE TABLE " + Contracts.Apartments.TABLE_NAME + " (" +
                    Contracts.Apartments._ID + " INTEGER PRIMARY KEY," +
                    Contracts.Apartments.COLUMN_APARTMENT_NAME + TEXT_TYPE + " )";
    private static final String SQL_CREATE_RECORDS_TABLE =
            "CREATE TABLE " + Contracts.Records.TABLE_NAME + " (" +
                    Contracts.Records._ID + " INTEGER PRIMARY KEY," +
                    Contracts.Records.COLUMN_CATEGORY_ID + INT_TYPE + COMMA_SEP +
                    Contracts.Records.COLUMN_APARTMENT_ID + INT_TYPE + COMMA_SEP +
                    Contracts.Records.COLUMN_DATE + INT_TYPE + COMMA_SEP +
                    Contracts.Records.COLUMN_VALUE + INT_TYPE +" )";
    private static final String SQL_CREATE_TARIFFS_TABLE =
            "CREATE TABLE " + Contracts.Tariffs.TABLE_NAME + " (" +
                    Contracts.Tariffs._ID + " INTEGER PRIMARY KEY," +
                    Contracts.Tariffs.COLUMN_CATEGORY_ID + INT_TYPE + COMMA_SEP +
                    Contracts.Tariffs.COLUMN_MAX + INT_TYPE + COMMA_SEP +
                    Contracts.Tariffs.COLUMN_MIN + INT_TYPE + COMMA_SEP +
                    Contracts.Tariffs.COLUMN_PRICE + REAL_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contracts.Categories.TABLE_NAME + COMMA_SEP + Contracts.Records.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        Log.d(LOG_TAG, "Create Database query SQL_CREATE_CATEGORIES_TABLE: " + SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_APARTMENTS_TABLE);
        Log.d(LOG_TAG, "Create Database query SQL_CREATE_APARTMENTS_TABLE: " + SQL_CREATE_APARTMENTS_TABLE);
        db.execSQL(SQL_CREATE_RECORDS_TABLE);
        Log.d(LOG_TAG, "Create Database query SQL_CREATE_RECORDS_TABLE: " + SQL_CREATE_RECORDS_TABLE);
        db.execSQL(SQL_CREATE_TARIFFS_TABLE);
        Log.d(LOG_TAG, "Create Database query SQL_CREATE_TARIFFS_TABLE: " + SQL_CREATE_TARIFFS_TABLE);

        //fill in categories table && tariffs
        HashMap<Integer, String> categories = Category.DefaultCategories();
        for(int i = 1; i <= categories.size(); ++i) {
            ContentValues values = prepareCategoryValues(i, categories.get(i));
            db.insert(Contracts.Categories.TABLE_NAME, "null", values);
            ContentValues tariffsValues = prepareTariffsValues(i);
            db.insert(Contracts.Tariffs.TABLE_NAME, "null", tariffsValues);
        }
        //fill in apartments table
        db.insert(Contracts.Apartments.TABLE_NAME, "null", prepareApartmentValues());
    }

    private ContentValues prepareApartmentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contracts.Apartments._ID, 1);
        contentValues.put(Contracts.Apartments.COLUMN_APARTMENT_NAME, "home");
        return contentValues;
    }

    private ContentValues prepareCategoryValues(int id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contracts.Categories._ID, id);
        contentValues.put(Contracts.Categories.COLUMN_CATEGORY_NAME, name);
        return contentValues;
    }

    private ContentValues prepareTariffsValues(int categoryId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contracts.Tariffs.COLUMN_CATEGORY_ID, categoryId);
        contentValues.put(Contracts.Tariffs.COLUMN_PRICE, 0.0);
        contentValues.put(Contracts.Tariffs.COLUMN_MIN, 0);
        contentValues.put(Contracts.Tariffs.COLUMN_MAX, Integer.MAX_VALUE);
        return contentValues;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.d(LOG_TAG, "Upgrade Database query: " + SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
