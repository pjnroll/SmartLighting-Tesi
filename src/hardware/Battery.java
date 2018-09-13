package hardware;

public class Battery extends Component {
    private static int count_id = 0;

    private int id;
    private String name;
    private int voltage;

    private Controller controller;

    public Battery(String name, int voltage) {
        this.name = name;
        this.voltage = voltage;

        id = count_id;
        count_id++;
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
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    /********************/

    public String toString() {
        return "(Batteria: " + name + " " + voltage + " V)";
    }
}
