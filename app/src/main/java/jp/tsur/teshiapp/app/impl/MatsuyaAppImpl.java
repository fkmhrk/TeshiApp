package jp.tsur.teshiapp.app.impl;

import jp.tsur.teshiapp.app.MatsuyaApp;

/**
 * Implementation of {@link jp.tsur.teshiapp.app.MatsuyaApp}
 */
public class MatsuyaAppImpl implements MatsuyaApp {

    private static final int RATE_MATSUYA = 290;

    @Override
    public int toYen(int matsuya) {
        return matsuya * RATE_MATSUYA;
    }

    @Override
    public double toMatsuya(int yen) {
        return (double) yen / RATE_MATSUYA;
    }}
