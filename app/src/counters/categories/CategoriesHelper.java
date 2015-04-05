package counters.categories;

import android.graphics.Color;
import com.example.MyCounters.R;

import java.util.HashMap;

/**
 * Created by mara on 4/4/15.
 */
public class CategoriesHelper {


    public static int getImageId(int categoryId) {

        switch (categoryId) {
            case 1:
                return R.drawable.ic_bulb;
            case 2:
                return R.drawable.ic_drop;
            case 3:
                return R.drawable.ic_gas;
        }
        return -1;
    }

    public static int getColor(int categoryId) {

        switch (categoryId) {
            case 1:
                return Color.parseColor("#8bc34a");
            case 2:
                return Color.parseColor("#03a9f4");
            case 3:
                return Color.parseColor("#ff9800");
        }
        return -1;
    }

    public static int getColorLight(int categoryId) {

        switch (categoryId) {
            case 1:
                return Color.parseColor("#dcedc8");
            case 2:
                return Color.parseColor("#b3e5fc");
            case 3:
                return Color.parseColor("#ffe0b2");
        }
        return -1;
    }

    public static String getUnits(int categoryId) {

        switch (categoryId) {
            case 1:
                return "kWt";
            case 2:
                return "m<sup><small>3 </small></sup>";
            case 3:
                return "m<sup><small>3 </small></sup>";
        }
        return "";
    }

}
