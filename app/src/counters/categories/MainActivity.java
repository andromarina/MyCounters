package counters.categories;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.MyCounters.R;
import counters.CountersApplication;
import counters.graph.GraphActivity;
import counters.table.TableActivity;

public class MainActivity extends Activity implements CategoryClickListener,MenuItem.OnMenuItemClickListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        CategoriesListAdapter adapter = new CategoriesListAdapter(CountersApplication.getRepository().getAllCategories(),
                this);
        recList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        MenuItem showGraphButton = menu.getItem(0);
        showGraphButton.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch(item.getItemId())
//        {
//            case R.id.preferences:
//                Intent i = new Intent(this, UserSettingsActivity.class);
//                startActivity(i);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
             case R.id.show_graph_button:
                 if(CountersApplication.getRepository().isRepositoryEmpty()) {
                     Toast.makeText(this, R.string.not_enough_records, Toast.LENGTH_LONG).show();
                     return true;
                 }
                Intent intent = new Intent(this, GraphActivity.class);
                 intent.putExtra("categoryID", new int[] {1,2,3});
                 startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public void notifyClick(int position) {
        Intent intent = new Intent(this, TableActivity.class);
        int categoryID = CountersApplication.getRepository().getAllCategories().get(position).getId();
        intent.putExtra("categoryID", categoryID);
        startActivity(intent);
    }
}
