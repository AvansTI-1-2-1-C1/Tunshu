package InterfaceLayer.HeadInterfaces;

public interface Switchable {

    /**
     * this interface will manage the objects that
     * need to be turned on and of frequently
     *  @return the state of the object
     */
    boolean isOn();
    void on();
    void off();
}
