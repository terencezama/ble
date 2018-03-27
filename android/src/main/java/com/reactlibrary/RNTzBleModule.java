
package com.reactlibrary;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    constants.put("ManagerStateUnknown" , ManagerState.Unknown.ordinal());
    constants.put("ManagerStateResetting" , ManagerState.Resetting.ordinal());
    constants.put("ManagerStateUnsupported" , ManagerState.Unsupported.ordinal());
    constants.put("ManagerStateUnauthorized" , ManagerState.Unauthorized.ordinal());
    constants.put("ManagerStatePoweredOff" , ManagerState.PoweredOff.ordinal());
    constants.put("ManagerStatePoweredOn" , ManagerState.PoweredOn.ordinal());

    constants.put("PermissionsReadable", BluetoothGattCharacteristic.PERMISSION_READ);
    constants.put("PermissionsWriteable", BluetoothGattCharacteristic.PERMISSION_WRITE);
    constants.put("PermissionsReadEncryptionRequired", BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED);
    constants.put("PermissionsWriteEncryptionRequired", BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED);

    constants.put("CharacteristicBroadcast", BluetoothGattCharacteristic.PROPERTY_BROADCAST);
    constants.put("CharacteristicRead", BluetoothGattCharacteristic.PROPERTY_READ);
    constants.put("CharacteristicWriteWithoutResponse", BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE);
    constants.put("CharacteristicWrite", BluetoothGattCharacteristic.PROPERTY_WRITE);
    constants.put("CharacteristicNotify", BluetoothGattCharacteristic.PROPERTY_NOTIFY);
    constants.put("CharacteristicIndicate", BluetoothGattCharacteristic.PROPERTY_INDICATE);
    constants.put("CharacteristicAuthenticatedSignedWrites", BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE);
    constants.put("CharacteristicExtendedProperties", BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS);

    return constants;
  }
  //endregion



  //region Peripheral Manager
  @ReactMethod
  public void pm_init() {
    BlePeripheralManager.shared().initialize(reactContext);
  }

  @ReactMethod
  public void pm_createService(String uuid, Boolean primary){
    BlePeripheralManager.shared().createService(uuid,primary);
  }
  @ReactMethod
  public void pm_createCharacteristic(String uuid,int properties, int permission,ReadableMap value){
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(byteOut);
      out.writeObject(value.toHashMap());
    } catch (IOException e) {
      e.printStackTrace();
    }
    BlePeripheralManager.shared().createCharacteristic(uuid,properties,permission,byteOut.toByteArray());
  }

  @ReactMethod
  public void pm_appendCharacteristic(String characteristicId, String serviceId){
    BlePeripheralManager.shared().appendCharacteristic(characteristicId,serviceId);
  }

  @ReactMethod
  public void pm_addService(String serviceId){
    BlePeripheralManager.shared().addService(serviceId);
  }

  @ReactMethod
  public void pm_advertise(){
    BlePeripheralManager.shared().advertise();
  }
  //endregion
}