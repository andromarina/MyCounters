package counters.graph;

import counters.CountersApplication;
import counters.Utils;
import model.Calculator;
import model.Record;
import model.Report;
import counters.database.Repository;

import java.util.*;

/**
* Created by mara on 2/14/15.
*/
public class GraphController {

    private Repository repo;
    private Report report;


    public GraphController(int[] categoryIds) {
        this.repo = CountersApplication.getRepository();
        this.report = new Report(this.repo);
        this.report.generate(categoryIds);
    }

    public int getMaxMonthIndex() {
        return this.report.getLastMonthIndex();
    }

    public int getMaxDiff() {
        return this.report.getMaxDiff();
    }

    public List<Integer> getDifferenceByCategoryId(int categoryId) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Record> records = this.repo.getRecordsByCategoryId(categoryId);
        ArrayList<Record> merged = Utils.groupRecords(records);
        Calculator.recalculateDiff(merged);
        for(Record rec : merged) {
            result.add(rec.getDiff());
        }
        return result;
    }

   public List<String> getMonthsNames() {
       ArrayList<String> result = new ArrayList<String>();

       for(int i = 0; i < getMaxMonthIndex(); ++i) {
           ArrayList<Record> records = this.report.getRecordsByMonthIndex(i);
           if(records.size() == 0) {
               continue;
           }
           Calendar cal = Calendar.getInstance();
           Date date = records.get(0).getDate();
           cal.setTime(date);
           String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);

           result.add(month);
       }
       return result;
   }
}
