package model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mara on 4/4/15.
 */
public class TariffTest {

    @Test
    public void test_01_Calculate() {
        Tariff tariff = new Tariff(0, 0);
        TariffRange r1 = new TariffRange(0, Integer.MAX_VALUE, 2.5);
        tariff.addTariffRange(r1);
        double res = tariff.calculate(15);
        Assert.assertEquals(37.5, res, 0);
    }

    @Test
    public void test_02_Calculate() {
        Tariff tariff = new Tariff(0, 0);
        TariffRange r1 = new TariffRange(0, 150, 2.5);
        TariffRange r2 = new TariffRange(151, Integer.MAX_VALUE, 3.5);
        tariff.addTariffRange(r1);
        tariff.addTariffRange(r2);
        double res = tariff.calculate(160);
        Assert.assertEquals(410, res, 0);
    }

    @Test
    public void test_03_Calculate() {
        Tariff tariff = new Tariff(0, 0);
        TariffRange r1 = new TariffRange(0, 150, 2.5);
        tariff.addTariffRange(r1);
        double res = tariff.calculate(0);
        Assert.assertEquals(0, res, 0);
    }

}
