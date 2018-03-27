package com.reactlibrary;

/**
 * Created by Terence on 27/03/2018.
 */

public class BleCentralManager {
    private static final BleCentralManager ourInstance = new BleCentralManager();

    public static BleCentralManager getInstance() {
        return ourInstance;
    }

    private BleCentralManager() {
    }
}
