package com.reactlibrary;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.soloader.NoopSoSource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Terence on 27/03/2018.
 * https://gist.github.com/Tinker-S/80705230ec59d952414f
 */

public class BlePeripheralManager {

    private Map<String,BluetoothGattService> services = new HashMap<>();
    private Map<String,BluetoothGattCharacteristic> characteristics = new HashMap<>();

    ReactApplicationContext context;
    BluetoothManager manager;
    BluetoothAdapter adapter;
    BluetoothLeAdvertiser advertiser;
    BluetoothGattServer gattServer;

    //region Emitter
    private void pm_notify(WritableMap params){
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("PeripheralManager",params);
    }
    //endregion


    //region Initialization
    private static final BlePeripheralManager ourInstance = new BlePeripheralManager();
    public static BlePeripheralManager shared() {
        return ourInstance;
    }

    private BlePeripheralManager() {

    }
    private void initialize(ReactApplicationContext context){
        this.context = context;

        services.clear();
        characteristics.clear();

        if(manager == null){
            manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        }

        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) == false || manager == null){
            pm_notify(didUpdateStateEvent(RNTzBleModule.ManagerState.Unsupported));
            return;
        }
        if(adapter == null){
            adapter = manager.getAdapter();
        }

        if(adapter.isEnabled() == false){
            pm_notify(didUpdateStateEvent(RNTzBleModule.ManagerState.PoweredOff));
            return;
        }

        if(adapter.isMultipleAdvertisementSupported() == false){
            pm_notify(didUpdateStateEvent(RNTzBleModule.ManagerState.Unsupported));
            return;
        }

        //all good notify all is good
        pm_notify(didUpdateStateEvent(RNTzBleModule.ManagerState.PoweredOn));

        gattServer  = manager.openGattServer(context,bluetoothGattServerCallback);


    }
    //endregion



    //region Peripheral Utils
    public void createService(String suuid, Boolean primary){
        UUID uuid                       = UUID.fromString(suuid);
        int serviceType                 = (primary)?BluetoothGattService.SERVICE_TYPE_PRIMARY:BluetoothGattService.SERVICE_TYPE_SECONDARY;
        BluetoothGattService service = new BluetoothGattService(uuid,serviceType);
        services.put(suuid,service);
    }

    public void createCharacteristic(String suuid, int properties, int permissions, byte[] value){
        UUID uuid                                   = UUID.fromString(suuid);
        BluetoothGattCharacteristic characteristic  = new BluetoothGattCharacteristic(uuid,properties,permissions);
        if(value != null){
            characteristic.setValue(value);
        }
        characteristics.put(suuid,characteristic);
    }
    public void appendCharacteristic(String characteristicId, String serviceId){
        BluetoothGattService service                = services.get(serviceId);
        BluetoothGattCharacteristic characteristic  = characteristics.get(characteristicId);
        service.addCharacteristic(characteristic);
    }
    public void addService(String serviceId){
        BluetoothGattService service = services.get(serviceId);
        gattServer.addService(service);

    }
    public void advertise(){
        if(advertiser == null){
            advertiser = adapter.getBluetoothLeAdvertiser();
        }

        AdvertiseSettings.Builder settingBuilder;
        settingBuilder = new AdvertiseSettings.Builder();
        settingBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
        settingBuilder.setConnectable(true);
        settingBuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);

        AdvertiseData.Builder advBuilder;
        advBuilder = new AdvertiseData.Builder();
//        mAdapter.setName("PeripheralAndroid"); //8 characters works, 9+ fails
        advBuilder.setIncludeDeviceName(true);

        advertiser.startAdvertising(settingBuilder.build(),advBuilder.build(),advertiseCallback);
    }
    //endregion


    //region Event
    public WritableMap didUpdateStateEvent(RNTzBleModule.ManagerState state){
        WritableMap body = Arguments.createMap();
        body.putInt("state",state.ordinal());
        body.putString("desc","ManagerState"+state.name());

        WritableMap event = Arguments.createMap();
        event.putMap("didUpdateState",body);
        event.putString("event","didUpdateState");
        return event;
    }
    //endregion

    //region BluetoothServerCallback
    private final BluetoothGattServerCallback bluetoothGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            super.onServiceAdded(status, service);
            Log.d("GattServer", "Our gatt server service was added.");

        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
        }

        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            super.onDescriptorReadRequest(device, requestId, offset, descriptor);
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            super.onExecuteWrite(device, requestId, execute);
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            super.onNotificationSent(device, status);
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            super.onMtuChanged(device, mtu);
        }
    };
    //endregion

    //region AdvertiserCallback
    private AdvertiseCallback advertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartFailure(int errorCode) {
            Log.d("advertise", "onStartFailure");
        }

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Log.d("advertise", "onStartSuccess");
        }
    };
    //endregion
}
