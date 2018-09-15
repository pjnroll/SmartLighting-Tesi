package hardware;

import helper.LAMP_TYPE;

public class Lamp extends Component {
    private static int count_id = 0;
    private int id;

    private LAMP_TYPE lamp_type;
    private int watt;

    public Lamp(LAMP_TYPE lamp_type, int watt) {
        id = count_id;
        count_id++;

        setLamp_type(lamp_type);
        setWatt(watt);
    }

    /** GETTERS SETTERS */
    public void setLamp_type(LAMP_TYPE lamp_type) {
        this.lamp_type = lamp_type;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }

    public int getId() {
        return id;
    }

    public LAMP_TYPE getLamp_type() {
        return lamp_type;
    }

    public int getWatt() {
        return watt;
    }
    /********************/

    @Override
    public String toString() {
        return "(Lampada: " + lamp_type.name() + " " + watt + " W)";
    }
}