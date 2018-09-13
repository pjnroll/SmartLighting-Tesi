package hardware;

import java.util.HashSet;

public class Controller extends Component {
    private static int count_id = 0;

    private int id;
    private String name;
    private HashSet<Sensor> sensors;
    private Lamp lamp;
    private Battery battery;

    public Controller(String name, HashSet<Sensor> sensors, Lamp lamp, Battery battery) {
        this.name = name;
        this.sensors = sensors;
        this.lamp = lamp;
        this.battery = battery;

        id = count_id;
        count_id++;
    }

    public Controller(String name, HashSet<Sensor> sensors) {
        this(name, sensors, null, null);
    }

    public Controller(String name, Lamp lamp) {
        this(name, new HashSet<>(), lamp, null);
    }

    public Controller(String name, Battery battery) {
        this(name, new HashSet<>(), null, battery);
    }

    public Controller(String name) {
        this(name, new HashSet<>(), null, null);
    }

    /** GETTERS SETTERS */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashSet<Sensor> getSensors() {
        return sensors;
    }

    public Lamp getLamp() {
        return lamp;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSensors(HashSet<Sensor> sensors) {
        this.sensors = sensors;
        for (Sensor s : sensors) {
            s.setController(this);
            s.setAttached(true);
        }
    }

    private void setLamp(Lamp lamp) {
        this.lamp = lamp;
        lamp.setAttached(true);
    }

    private void setBattery(Battery battery) {
        this.battery = battery;
        battery.setAttached(true);
    }
    /********************/

    /**
     * This method lets adding a Component to the Controller
     * @param c
     */
    public void addComponent(Component c) throws Exception {
        if (c != null && !c.getAttached()) {
            if (c instanceof Sensor) {
                sensors.add((Sensor) c);
                ((Sensor) c).setController(this);
            } else if (c instanceof Lamp) {
                setLamp((Lamp) c);
                ((Lamp) c).setController(this);
            } else if (c instanceof Battery) {
                setBattery((Battery) c);
                ((Battery) c).setController(this);
            }
        } else if (c == null) {
            throw new NullPointerException("Il componente è nullo");
        } else {
            throw new Exception("Il componente " + c + " è gia collegato");
        }
    }

    public String toString() {
        return "[Controller: " + name + ", " + sensors + ", " + lamp + ", " + battery + "]";
    }
}