package hardware;

public class Battery extends Component {
    private static int count_id = 0;
    private int id;

    private String name;
    private int watt;

    public Battery(String name, int watt) {
        id = count_id;
        count_id++;

        setName(name);
        setWatt(watt);

        setAttached(false);
        setController(null);
    }

    /**
     * Zero is the default watt value, but it must be set the actual value
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

    private void setWatt(int watt) {
        this.watt = watt;
    }
    /********************/

    public String toString() {
        return "(Batteria: " + name + " " + watt + " V)";
    }
}
