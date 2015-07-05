package counters.graph;

import android.view.View;
import android.widget.AdapterView;
import counters.SpinnerListener;

/**
 * Created by mara on 7/5/15.
 */
public abstract class SpinnerListenerForGraphActivity extends SpinnerListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        super.onItemSelected(parent, view, position, id);
        notifyItemSelected();
    }

    public abstract void notifyItemSelected();
}
