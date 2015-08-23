package counters.table;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.counters.chart.R;
import counters.CountersApplication;
import counters.Preferences;
import counters.SpinnerController;
import counters.SpinnerListener;
import counters.categories.CategoriesHelper;
import counters.graph.GraphActivity;
import model.Category;


/**
 * Created by mara on 1/6/15.
 */
public class TableActivity extends Activity implements MenuItem.OnMenuItemClickListener{
    private DialogsController controller;
    private int categoryId;
    private int categoryColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        Intent intent = getIntent();
        this.categoryId = intent.getIntExtra("categoryID", 1);
        this.categoryColour = CategoriesHelper.getColor(this.categoryId);
        this.controller = new DialogsController(this, CountersApplication.getRepository(),this.categoryId);
        configureActionBar();
        initializeViews();
        configureSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureSpinner();
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }

    private void initializeViews() {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(createListAdapter());
        TextView dateHeader = (TextView) findViewById(R.id.date_header);
        dateHeader.setTextColor(this.categoryColour);
        TextView valueHeader = (TextView) findViewById(R.id.value_header);
        valueHeader.setTextColor(this.categoryColour);
        TextView diffHeader = (TextView) findViewById(R.id.diff_header);
        diffHeader.setTextColor(this.categoryColour);
        TextView priceHeader = (TextView) findViewById(R.id.price_header);
        priceHeader.setTextColor(this.categoryColour);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_activity_menu, menu);
        MenuItem addRowButton = menu.getItem(0);
        addRowButton.setOnMenuItemClickListener(this);
        MenuItem tariffButton = menu.getItem(1);
        tariffButton.setOnMenuItemClickListener(this);
        MenuItem showGraphButton = menu.getItem(2);
        showGraphButton.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    private void configureActionBar() {

        getActionBar().setDisplayShowTitleEnabled(true);
        Category category = CountersApplication.getRepository().getCategoryById(this.categoryId);
        getActionBar().setTitle(category.getName());
        getActionBar().setBackgroundDrawable(new ColorDrawable(CategoriesHelper.getColor(this.categoryId)));
    }

    private TableListAdapter createListAdapter() {

        TableListAdapter adapter = new TableListAdapter(this, R.layout.row, this.categoryId, this.controller,
                CountersApplication.getRepository());
        return adapter;
    }

    private void configureSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.year_spinner);
        SpinnerController spinnerController = new SpinnerController(this);
        ArrayAdapter<String> adapter = spinnerController.createAdapter();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerListener());
        spinner.setSelection(Preferences.getSpinnerPosition());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_row_button:
                controller.showAddRecordDialog();
                return true;
            case R.id.show_graph_button:
                if(CountersApplication.getRepository().isEmpty(categoryId)) {
                    Toast.makeText(this, R.string.not_enough_records, Toast.LENGTH_LONG).show();
                    return false;
                }
                Intent intent = new Intent(this, GraphActivity.class);
                intent.putExtra("categoryID", new int[] {this.categoryId});
                startActivity(intent);
                return true;
            case R.id.tariff_button:
                controller.showUpdateTariffDialog();
                return true;
        }
        return true;
    }
}
