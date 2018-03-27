package com.reactlibrary;

/**
 * Created by Terence on 27/03/2018.
 */

public class BlePeripheralManager {
    private static final BlePeripheralManager ourInstance = new BlePeripheralManager();

    public static BlePeripheralManager shared() {
        return ourInstance;
    }

    private BlePeripheralManager() {
    }
}
