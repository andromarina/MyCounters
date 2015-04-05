package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mara on 3/22/15.
 */
public class Report {
    private Map<Integer, ArrayList<Record>> recordsCollections = new HashMap<Integer, ArrayList<Record>>();
    private IRepository repo;

    public Report(IRepository repo) {
        this.repo = repo;
    }

    public void generate(int[] categoriesIds) {
        for(int categoryId : categoriesIds) {
            recordsCollections.put(categoryId, this.repo.getRecordsByCategoryId(categoryId));
        }
    }

    public int getLastMonthIndex() {
        int max = 0;
        for(ArrayList<Record> records : recordsCollections.values()) {
            if(records.size() > max) {
                max = records.size();
            }
        }
        return max;
    }

    public int getMaxDiff() {
        int maxDiff = 0;
        for(ArrayList<Record> records : recordsCollections.values()) {
            for(Record record : records) {
                if(record.getDiff() > maxDiff) {
                    maxDiff = record.getDiff();
                }
            }
        }
        return maxDiff;
    }

    public ArrayList<Record> getRecordsByMonthIndex(int monthIndex) {
        ArrayList<Record> result = new ArrayList<Record>();

        for(ArrayList<Record> records : recordsCollections.values()) {
            if(monthIndex >= records.size()) {
                return result;
            }
            result.add(records.get(monthIndex));
        }
        return result;
    }

}
