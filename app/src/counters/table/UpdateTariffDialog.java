package counters.table;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;
import com.counters.chart.R;
import model.Tariff;
import model.TariffRange;

/**
 * Created by mara on 4/6/15.
 */
public class UpdateTariffDialog {
    private AlertDialog.Builder builder;
    private int categoryId;
    private UpdateTariffDialog.CloseListener closeListener;
    private Context context;

    public UpdateTariffDialog(Context context, int categoryId, double oldValue) {
        this.categoryId = categoryId;
        this.context = context;
        this.builder = create(oldValue);
    }

    public interface CloseListener {
        public void OnClose(Tariff tariff);
    }

    public void setOnCloseListener(CloseListener listener) {
        this.closeListener = listener;
    }

    private AlertDialog.Builder create(double oldValue) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.price_per_unit);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        input.setText(Double.toString(oldValue));
        builder.setView(input);

        builder.setPositiveButton("OK", new OnOKListener(input));
        builder.setNegativeButton("Cancel", new OnCancelListener());
        return builder;
    }

    public void show() {
        this.builder.show();
    }

    private Tariff createTariff(String value, int categoryID) {
        try {
            double price = Double.parseDouble(value);
            Tariff tariff = new Tariff(categoryID);
            //TODO: only one tariff range harcoded
            TariffRange range = new TariffRange(0, Integer.MAX_VALUE, price);
            tariff.addTariffRange(range);
            return tariff;
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
            Tariff result = createTariff(value.replaceAll("\\s", ""), categoryId);
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
