package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mara on 4/4/15.
 */
public class TariffTest {

    @Test
    public void test_01_Calculate() {
        Tariff tariff = new Tariff(0);
        TariffRange r1 = new TariffRange(0, Integer.MAX_VALUE, 2.5);
        tariff.addTariffRange(r1);
        ArrayList<Record> records = new ArrayList<Record>();
        Record rec = new Record(1, new Date(), 2512, 0);
        rec.setDiff(15);
        records.add(rec);
        tariff.calc(records);
        Assert.assertEquals(37.5, records.get(0).getPrice(), 0);
    }

    @Test
    public void test_02_Calculate() {
        Tariff tariff = new Tariff(0);
        TariffRange r1 = new TariffRange(0, 150, 2.5);
        TariffRange r2 = new TariffRange(151, Integer.MAX_VALUE, 3.5);
        tariff.addTariffRange(r1);
        tariff.addTariffRange(r2);
        ArrayList<Record> records = new ArrayList<Record>();
        Record rec = new Record(1, new Date(), 2512, 0);
        rec.setDiff(160);
        records.add(rec);
        tariff.calc(records);
        Assert.assertEquals(410, records.get(0).getPrice(), 0);
    }

    @Test
    public void test_03_Calculate() {
        Tariff tariff = new Tariff(0);
        TariffRange r1 = new TariffRange(0, 150, 2.5);
        tariff.addTariffRange(r1);
        ArrayList<Record> records = new ArrayList<Record>();
        Record rec = new Record(1, new Date(), 2512, 0);
        rec.setDiff(0);
        records.add(rec);
        tariff.calc(records);
        Assert.assertEquals(0, records.get(0).getPrice(), 0);
    }

}
