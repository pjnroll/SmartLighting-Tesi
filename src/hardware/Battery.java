package hardware;

public class Battery extends Component {
    private static int count_id = 0;
    private int id;

    private String name;
    private int voltage;

    public Battery(String name, int voltage) {
        id = count_id;
        count_id++;

        setName(name);
        setVoltage(voltage);

        setAttached(false);
        setController(null);
    }

    /**
     * Zero is the default voltage value, but it must be set the actual value
     * WARNING
     * @param name
     */
    public Battery(String name) {
        this(name,0);
    }

    /** GETTERS SETTERS */
    private void setName(String name) {
        this.name = name;
    }

    private void setVoltage(int voltage) {
        this.voltage = voltage;
    }
    /********************/

    public String toString() {
        return "(Batteria: " + name + " " + voltage + " V)";
    }
}
