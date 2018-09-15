package hardware;

import java.util.HashSet;

public class Controller {
    private static int count_id = 0;
    private int id;

    private String name;
    private HashSet<Component> components;

    private Lamp lamp;
    private Battery battery;

    public Controller(String name, HashSet<Component> components) {
        this.name = name;
        this.setComponents(components);
        id = count_id;
        count_id++;
    }

    public Controller(String name, Lamp lamp) {
        this.name = name;
        components = new HashSet<>();
        this.lamp = lamp;
    }

    public Controller(String name, Battery battery) {
        setName(name);
        setBattery(battery);
    }

    public Controller(String name) {
        setName(name);
    }

    /** GETTERS SETTERS */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashSet<Component> getComponents() {
        return components;
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

    public void setComponents(HashSet<Component> components) {
        for (Component c : components) {
            try {
                addComponent(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setSensor(Sensor sensor) {
        components.add(sensor);
        sensor.setAttached(true);
        sensor.setController(this);
    }

    private void setLamp(Lamp lamp) {
        this.lamp = lamp;
        lamp.setAttached(true);
        lamp.setController(this);
    }

    private void setBattery(Battery battery) {
        this.battery = battery;
        battery.setAttached(true);
        battery.setController(this);
    }
    /********************/

    /**
     * This method lets adding a Component to the Controller
     * @param c
     */
    public void addComponent(Component c) throws Exception {
        if (c != null && !c.getAttached()) {
            if (c instanceof Sensor) {
                setSensor((Sensor) c);
            } else if (c instanceof Lamp) {
                setLamp((Lamp) c);
            } else if (c instanceof Battery) {
                setBattery((Battery) c);
            }
        } else if (c == null) {
            throw new NullPointerException("Il componente è nullo");
        } else {
            throw new Exception("Il componente " + c + " è gia collegato");
        }
    }

    private void remSensor(Sensor sensor) {
        if (sensor.getAttached() && getComponents().contains(sensor)) {
            getComponents().remove(sensor);
            sensor.setAttached(false);
            sensor.setController(null);
        }
    }

    private void remLamp(Lamp lamp) {
        if (lamp.getAttached() && getLamp().equals(lamp)) {
            this.lamp = null;
            lamp.setAttached(false);
            lamp.setController(null);
        }
    }

    private void remBattery(Battery battery) {
        if (battery.getAttached() && getBattery().equals(battery)) {
            this.battery = null;
            battery.setAttached(false);
            battery.setController(null);
        }
    }

    public void remComponent(Component c) throws Exception {
        if (c != null && c.getAttached() && c.getController().equals(this)) {
            if (c instanceof Sensor) {
                remSensor((Sensor) c);
            } else if (c instanceof Lamp) {
                remLamp((Lamp) c);
            } else if (c instanceof Battery) {
                remBattery((Battery) c);
            }
        } else if (c == null) {
            throw new NullPointerException("Il componente è nullo");
        } else {
            throw new Exception("Il componente " + c + " è gia collegato");
        }
    }

    public String toString() {
        return "[Controller: " + name + ", " + components + ", " + lamp + ", " + battery + "]";
    }
}