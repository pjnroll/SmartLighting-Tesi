package hardware;

import exceptions.ComponentAlreadyAttachedException;

import java.util.HashSet;

public class Controller {
    private static int count_id = 0;
    private int id;

    private String name;
    private HashSet<Component> components;

    private Lamp lamp;
    private Battery battery;

    private boolean attached;
    Streetlight streetlight;

    private int position;

    /**
     * Full constructor
     * @param name
     * @param components
     */
    public Controller(String name, HashSet<Component> components) {
        this(name);

        setComponents(components);
    }

    /**
     * Empty constructor
     * @param name
     */
    private Controller(String name) {
        id = count_id;
        count_id++;

        setName(name);
        components = new HashSet<>();

        setAttached(false);
        setStreetlight(null);
    }

    /** GETTERS SETTERS */
    public HashSet<Component> getComponents() {
        return components;
    }

    public Lamp getLamp() {
        return lamp;
    }

    private Battery getBattery() {
        return battery;
    }

    private void setName(String name) {
        this.name = name;
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

    public void setPosition(int position) {
        this.position = position;
        for (Component c : getComponents()) {
            c.setPosition(position);
        }
    }

    /**
     * This method lets adding a Component to the Controller
     * @param c
     */
    private void setComponent(Component c) throws Exception {
        if (c != null && !c.getAttached()) {
            if (c instanceof Sensor) {
                setSensor((Sensor) c);
            } else if (c instanceof Lamp) {
                setLamp((Lamp) c);
            } else if (c instanceof Battery) {
                setBattery((Battery) c);
            }
            c.setPosition(position);
        } else if (c == null) {
            throw new NullPointerException("Il componente è nullo");
        } else {
            throw new ComponentAlreadyAttachedException(c);
        }
    }

    private void setComponents(HashSet<Component> components) {
        for (Component c : components) {
            try {
                setComponent(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setStreetlight(Streetlight streetlight) {
        this.streetlight = streetlight;
    }

    public Streetlight getStreetlight() {
        return streetlight;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public boolean getAttached() {
        return attached;
    }
    /********************/

    /*
    Methods to remove the components
     */
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

    private void remComponent(Component c) throws Exception {
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

    public void dimLamp(int value) {
        getLamp().setIntensity(value);
    }

    public String toString() {
        return "[Controller: " + name + ", " + components + ", " + lamp + ", " + battery + "]";
    }
}