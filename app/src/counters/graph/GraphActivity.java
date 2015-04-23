package counters.graph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import com.example.MyCounters.R;
import counters.CountersApplication;
import counters.categories.CategoriesHelper;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Created by mara on 2/11/15.
 */
public class GraphActivity extends Activity {
    private int[] categoriesIds;
    private GraphController graphController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        Intent intent = getIntent();
        this.categoriesIds = intent.getIntArrayExtra("categoryID");
        this.graphController = new GraphController(this.categoriesIds);
        drawChart();
    }

    private XYMultipleSeriesRenderer createMultipleRenderer() {
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        multiRenderer.setZoomEnabled(false,false);
       // multiRenderer.setChartTitle("Income vs Expense Chart");

    //    multiRenderer.setChartTitleTextSize(28);
        multiRenderer.setAxisTitleTextSize(24);
        multiRenderer.setLabelsTextSize(24);
        multiRenderer.setShowGridY(true);
        multiRenderer.setFitLegend(true);
        multiRenderer.setLegendTextSize(24);
        multiRenderer.setShowGrid(true);
        multiRenderer.setAntialiasing(true);
        multiRenderer.setLegendHeight(30);
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        multiRenderer.setYLabels(10);
        multiRenderer.setYAxisMin(0);
        multiRenderer.setXAxisMin(-1);
        multiRenderer.setBarSpacing(0.5);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});
        multiRenderer.setXAxisMax(this.graphController.getMaxMonthIndex());
        multiRenderer.setYAxisMax(this.graphController.getMaxDiff() + 5);

        List<String> monthsNames = this.graphController.getMonthsNames();
        multiRenderer.setPanEnabled(false, false);

        if(monthsNames.size() > 12) {
            multiRenderer.setPanEnabled(true, false);
            multiRenderer.setXAxisMax(12);
        }

        // multiRenderer.setXTitle("Year 2014");

        for(int i = 0; i < monthsNames.size(); i++){
            multiRenderer.addXTextLabel(i, monthsNames.get(i));
        }

        for(int i = 0; i < this.categoriesIds.length; ++i) {
            multiRenderer.addSeriesRenderer(createBarRenderer(this.categoriesIds[i]));
        }
        if(categoriesIds.length > 1) {
            multiRenderer.setYTitle("Difference");
        } else {
            multiRenderer.setYTitle("Difference, " + Html.fromHtml(CategoriesHelper.getUnits(categoriesIds[0])));
        }

        return multiRenderer;
    }


    private XYSeriesRenderer createBarRenderer(int categoryId) {

        XYSeriesRenderer barsRenderer = new XYSeriesRenderer();
        barsRenderer.setColor(CategoriesHelper.getColor(categoryId));
        barsRenderer.setFillPoints(true);
        barsRenderer.setLineWidth(2);
        barsRenderer.setDisplayChartValues(true);
        barsRenderer.setDisplayChartValuesDistance(10);
        barsRenderer.setChartValuesTextSize(24);

        return barsRenderer;
    }

    private XYSeries convertDifferencesToXYSeries(int categoryId) {
        List<Integer> differences = this.graphController.getDifferenceByCategoryId(categoryId);
        XYSeries differenceSeries = new XYSeries(CountersApplication.getRepository().getCategoryById(categoryId).getName());
        for(int i = 0; i < differences.size(); i++) {
            differenceSeries.add(i,differences.get(i));
        }
        return differenceSeries;
    }

    private XYMultipleSeriesDataset createDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

            for(int i = 0; i < categoriesIds.length; ++i) {
                XYSeries differenceSeries = convertDifferencesToXYSeries(this.categoriesIds[i]);
                dataset.addSeries(differenceSeries);
            }

        return dataset;
    }

    private void drawChart() {
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        chartContainer.removeAllViews();
        XYMultipleSeriesRenderer multiRenderer = createMultipleRenderer();
        XYMultipleSeriesDataset dataset = createDataset();
        View chart = ChartFactory.getBarChartView(this, dataset, multiRenderer, BarChart.Type.DEFAULT);
        chartContainer.addView(chart);
    }
}
