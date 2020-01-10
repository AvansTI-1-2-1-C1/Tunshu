package Utils.CallBacks;

import Utils.Enums.BluetoothStateCommands;
import Utils.Enums.DriveCommands;

public interface BluetoothCallBack {
    void onInput(DriveCommands command);

    String getState(BluetoothStateCommands command);

    void setState(BluetoothStateCommands command,String value);
}
