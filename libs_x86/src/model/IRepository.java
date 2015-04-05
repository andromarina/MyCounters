package model;

import java.util.ArrayList;

/**
 * Created by mara on 4/4/15.
 */
public interface IRepository {

    public ArrayList<Record> getRecordsByCategoryId(int categoryId);
}
