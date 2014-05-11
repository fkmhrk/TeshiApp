package jp.tsur.teshiapp.app.impl;

import android.test.AndroidTestCase;

import jp.tsur.teshiapp.app.MatsuyaApp;

/**
 * Testcase for {@link jp.tsur.teshiapp.app.impl.MatsuyaAppImpl}
 */
public class MatsuyaAppTest extends AndroidTestCase {

    public void test_0000_toYen_1() {
        MatsuyaApp app = new MatsuyaAppImpl();
        int price = app.toYen(1);
        assertEquals(290, price);
    }

    public void test_0001_toYen_120() {
        MatsuyaApp app = new MatsuyaAppImpl();
        int price = app.toYen(120);
        assertEquals(34800, price);
    }

    public void test_0002_toYen_0() {
        MatsuyaApp app = new MatsuyaAppImpl();
        int price = app.toYen(0);
        assertEquals(0, price);
    }

    public void test_0100_toMatsuya_290() {
        MatsuyaApp app = new MatsuyaAppImpl();
        double matsuya = app.toMatsuya(290);
        assertEquals(1.0, matsuya);
    }

    public void test_0101_toMatsuya_435() {
        MatsuyaApp app = new MatsuyaAppImpl();
        double matsuya = app.toMatsuya(435);
        assertEquals(1.5, matsuya);
    }

    public void test_0102_toMatsuya_0() {
        MatsuyaApp app = new MatsuyaAppImpl();
        double matsuya = app.toMatsuya(0);
        assertEquals(0.0, matsuya);
    }
}
