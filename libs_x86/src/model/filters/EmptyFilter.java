package model.filters;

import model.Record;

/**
 * Created by mara on 3/1/15.
 */
public class EmptyFilter implements IFilter {

    @Override
    public boolean match(Record record) {
        return true;
    }
}
