
# react-native-tz-ble

## Getting started

`$ npm install react-native-tz-ble --save`

### Mostly automatic installation

`$ react-native link react-native-tz-ble`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-tz-ble` and add `RNTzBle.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNTzBle.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNTzBlePackage;` to the imports at the top of the file
  - Add `new RNTzBlePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-tz-ble'
  	project(':react-native-tz-ble').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-tz-ble/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-tz-ble')
  	```


# Usage
```javascript
import RNTzBle from 'react-native-tz-ble';

// TODO: What to do with the module?
RNTzBle;
```
  
## Peripheral Manager
for interacting with the Peripheral Manager the prefix is pm
RNTzBle.pm_\<method\>

### Quick Flow
```
//Use peripheral Manager
RNTzBle.pm_init();
//Create a service 
// you can use uuidgen on mac

let serviceId 	= @"560D13CB-86C4-499F-B550-BB0D1E0F7944";
let serviceId 	= "560D13CB-86C4-499F-B550-BB0D1E0F7944";
let cid         = "16E2768A-4FE5-4340-9952-BD951BEA3DDC";
RNTzBle.pm_createService(serviceId,true);
RNTzBle.pm_createCharacteristic(cid,RNTzBle.CharacteristicRead,RNTzBle.PermissionsReadable,{
      location: '4 Privet Drive, Surrey',
      description: '...',
    });
RNTzBle.pm_appendCharacteristic(cid,serviceId);
RNTzBle.pm_addService(serviceId);
RNTzBle.pm_advertise();

```

**To start the peripheral manager**

```
RNTzBle.pm_init();
```

### Characteristics
- properties
	- RNTzBle.CharacteristicBroadcast
	- RNTzBle.CharacteristicRead
	- RNTzBle.CharacteristicWriteWithoutResponse
	- RNTzBle.CharacteristicWrite
	- RNTzBle.CharacteristicNotify
	- RNTzBle.CharacteristicIndicate
	- RNTzBle.CharacteristicAuthenticatedSignedWrites
	- RNTzBle.CharacteristicExtendedProperties
- permissions
	- RNTzBle.PermissionsReadable
	- RNTzBle.PermissionsWriteable
	- RNTzBle.PermissionsReadEncryptionRequired
	- RNTzBle.PermissionsWriteEncryptionRequired
