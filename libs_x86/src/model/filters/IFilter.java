package model.filters;

import model.Record;

/**
 * Created by mara on 3/1/15.
 */
public interface IFilter {
    public boolean match(Record record);
}
