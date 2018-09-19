package hardware;

import helper.LAMP_STATUS;
import helper.LAMP_TYPE;

public class Lamp extends Component {
    private static int count_id = 0;
    private int id;

    private LAMP_STATUS status;
    private LAMP_TYPE lamp_type;
    private int watt;
    private int intensity;

    public Lamp(LAMP_TYPE lamp_type, int watt) {
        id = count_id;
        count_id++;

        setStatus(LAMP_STATUS.OFF);
        setLamp_type(lamp_type);
        setWatt(watt);

        setAttached(false);
        setController(null);
    }

    /** GETTERS SETTERS */
    public void setStatus(LAMP_STATUS status) {
        this.status = status;
    }

    private void setLamp_type(LAMP_TYPE lamp_type) {
        this.lamp_type = lamp_type;
    }

    private void setWatt(int watt) {
        this.watt = watt;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getId() {
        return id;
    }

    public LAMP_STATUS getStatus() {
        return status;
    }

    public LAMP_TYPE getLamp_type() {
        return lamp_type;
    }

    public int getWatt() {
        return watt;
    }

    public int getIntensity() {
        return intensity;
    }
    /********************/

    @Override
    public String toString() {
        return "(Lampada: " + lamp_type.name() + " " + watt + " W)";
    }
}