
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

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


  //region enums
  public enum ManagerState{
    Unknown,
    Resetting,
    Unsupported,
    Unauthorized,
    PoweredOff,
    PoweredOn,
  }
  //endregion



  //region Constants
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("ManagerStateUnknown" , ManagerState.Unknown);
    constants.put("ManagerStateResetting" , ManagerState.Resetting);
    constants.put("ManagerStateUnsupported" , ManagerState.Unsupported);
    constants.put("ManagerStateUnauthorized" , ManagerState.Unauthorized);
    constants.put("ManagerStatePoweredOff" , ManagerState.PoweredOff);
    constants.put("ManagerStatePoweredOn" , ManagerState.PoweredOn);

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
  public void pm_createCharacteristic(String uuid,int properties, int permission,ReadableMap value){

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