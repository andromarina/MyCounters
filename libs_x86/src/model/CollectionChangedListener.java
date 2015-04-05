package model;

/**
 * Created by mara on 2/8/15.
 */
public interface CollectionChangedListener {

    public void OnRecordAdded(Record record);

    public void OnRecordRemoved(Record record);

    public void OnFilterChanged();
}
