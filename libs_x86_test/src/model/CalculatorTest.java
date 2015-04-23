package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mara on 4/19/15.
 */
public class CalculatorTest {

    @Test
    public void test01_calcDiff() {
        ArrayList<Record> records = new ArrayList<Record>();
        Record rec1 = new Record(1, new Date(), 2590, 0);
        Record rec2 = new Record(2, new Date(), 2512, 0);
        records.add(rec1);
        records.add(rec2);
        Calculator.recalculateDiff(records);
        Assert.assertEquals(78, records.get(0).getDiff(), 0);
    }
}
