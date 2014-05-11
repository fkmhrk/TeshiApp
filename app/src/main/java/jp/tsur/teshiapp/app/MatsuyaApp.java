package jp.tsur.teshiapp.app;

/**
 * Logic for {@link jp.tsur.teshiapp.MatsuyaActivity}
 */
public interface MatsuyaApp {
    /**
     * Calculates a price of given matsuya
     * @param matsuya a number of matsuya
     * @return total price
     */
    int toYen(int matsuya);

    /**
     * Calculates a number of matsuya from yen
     * @param yen price
     * @return number of matsuya
     */
    double toMatsuya(int yen);
}
