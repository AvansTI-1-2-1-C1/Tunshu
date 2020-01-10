package Utils.CallBacks;

import Utils.Enums.BluetoothStateCommands;
import Utils.Enums.DriveCommands;

public interface BluetoothCallBack {

    /**
     * TODO
     *
     * @param command
     */
    void onInput(DriveCommands command);

    /**
     * TODO
     *
     * @param command
     */
    String getState(BluetoothStateCommands command);

    /**
     * TODO
     *
     * @param command
     */
    void setState(BluetoothStateCommands command, String value);
}
