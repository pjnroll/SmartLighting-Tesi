package hardware;

public class Battery extends Component {
    private static int count_id = 0;
    private int id;

    private String name;
    private int watt;

    /**
     * Class that describes a battery
     * @param name battery's brand
     * @param watt battery's watt
     */
    public Battery(String name, int watt) {
        id = count_id;
        count_id++;

        setName(name);
        setWatt(watt);

        setAttached(false);
        setController(null);
    }

    /** GETTERS SETTERS */
    /**
     * Method that returns the battery's name
     * @param name battery's brand
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Method that returns the battery's watt
     * @param watt battery's watt
     */
    private void setWatt(int watt) {
        this.watt = watt;
    }
    /********************/

    public String toString() {
        return "(Batteria: " + name + " " + watt + " V)";
    }
}
