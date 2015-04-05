package counters.database;

import android.provider.BaseColumns;

/**
 * Created by mara on 1/2/15.
 */
public final class Contracts {

    public Contracts() {}

    public static abstract class Categories implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_CATEGORY_NAME = "name";
    }

    public static abstract class Apartments implements BaseColumns {
        public static final String TABLE_NAME = "apartments";
        public static final String COLUMN_APARTMENT_NAME = "name";
    }

    public static abstract class Records implements BaseColumns {
        public static final String TABLE_NAME = "records";
        public static final String COLUMN_CATEGORY_ID = "categoryid";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_APARTMENT_ID = "apartmentid";
    }

}
