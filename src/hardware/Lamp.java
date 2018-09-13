package hardware;

import helper.LAMP_TYPE;

public class Lamp extends Component {
    private LAMP_TYPE lamp_type;
    private int watt;

    private Controller controller;

    public Lamp(LAMP_TYPE lamp_type, int watt) {
        this.lamp_type = lamp_type;
        this.watt = watt;
    }

    /** GETTERS SETTERS */
    public LAMP_TYPE getLamp_type() {
        return lamp_type;
    }

    public int getWatt() {
        return watt;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    /********************/

    @Override
    public String toString() {
        return "(Lampada: " + lamp_type.name() + " " + watt + " W)";
    }
}