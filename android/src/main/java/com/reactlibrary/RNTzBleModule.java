
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.util.HashMap;
import java.util.Map;

public class RNTzBleModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNTzBleModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNTzBle";
  }




  //region Constants
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
//    constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
//    constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
    return constants;
  }
  //endregion

  //region Peripheral Manager
  @ReactMethod
  public void pm_init() {

  }

  @ReactMethod
  public void pm_createService(String uuid, Boolean primary){

  }

  @ReactMethod
  public void pm_appendCharacteristic(String characteristicId, String serviceId){

  }

  @ReactMethod
  public void pm_addService(String serviceId){

  }

  @ReactMethod
  public void pm_advertise(){

  }
  //endregion
}