package HardwareLayer.Navigation;

import Utils.Enums.DriveCommands;

public interface BluetoothCallBack {
    void onInput(DriveCommands command);
}
