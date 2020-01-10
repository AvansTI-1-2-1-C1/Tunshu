package Utils.CallBacks;

import Utils.Enums.RemoteCommand;

public interface RemoteControlCallBack {

    /**
     * creates a contract that states, if implemented the methods here WILL be part of the class
     * we use this to create callbacks
     *
     * @param command the selected command will be called back to the classes that implement this.
     */
    void onButtonPress(RemoteCommand command);
}
