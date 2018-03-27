package com.reactlibrary;

/**
 * Created by Terence on 27/03/2018.
 */

public class BleManager {
    private static final BleManager ourInstance = new BleManager();

    public static BleManager shared() {
        return ourInstance;
    }

    private BleManager() {
    }


}
