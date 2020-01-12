package Utils.CallBacks;

import Utils.Enums.BluetoothStateCommands;
import Utils.Enums.DriveCommands;

public interface BluetoothCallBack {

    /**
     * callback to set a command
     *
     * @param command is a Enum list of available commands
     */
    void onInput(DriveCommands command);

    /**
     * callback to get the state
     *
     * @param command is a Enum list of available commands
     */
    String getState(BluetoothStateCommands command);

    /**
     * callback to set the state
     *
     * @param command is a Enum list of available commands
     */
    void setState(BluetoothStateCommands command, String value);
}
