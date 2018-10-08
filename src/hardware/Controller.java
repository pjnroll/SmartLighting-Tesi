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
     * @param name controller's brand
     * @param components the components to be attached to the controller
     */
    public Controller(String name, HashSet<Component> components) {
        this(name);

        setComponents(components);
    }

    /**
     * Empty constructor
     * @param name controller's brand
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
     * @param c the component to be attached
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

    /**
     * Private method to set each component to the controller
     * @param components all the components
     */
    private void setComponents(HashSet<Component> components) {
        for (Component c : components) {
            try {
                setComponent(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to attach a streetlight to the controller
     * @param streetlight the streetlight to attach
     */
    public void setStreetlight(Streetlight streetlight) {
        this.streetlight = streetlight;
    }

    /**
     * This return the streetlight at which is attached this controller
     * @return the streetlight attached
     */
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

    /**
     * Method to remove a sensor from the controller
     * @param sensor the sensor to remove
     */
    private void remSensor(Sensor sensor) {
        if (sensor.getAttached() && getComponents().contains(sensor)) {
            getComponents().remove(sensor);
            sensor.setAttached(false);
            sensor.setController(null);
        }
    }

    /**
     * Method to remove a lamp from the controller; it checks if the specified lamp is the one attached
     * @param lamp the lamp to remove
     */
    private void remLamp(Lamp lamp) {
        if (lamp.getAttached() && getLamp().equals(lamp)) {
            this.lamp = null;
            lamp.setAttached(false);
            lamp.setController(null);
        }
    }

    /**
     * Method to remove a battery from the controller; it checks if the specified battery is the one attached
     * @param battery the battery to remove
     */
    private void remBattery(Battery battery) {
        if (battery.getAttached() && getBattery().equals(battery)) {
            this.battery = null;
            battery.setAttached(false);
            battery.setController(null);
        }
    }

    /**
     * Method to remove a generic component from the controller
     * @param c the generic component
     * @throws Exception if the component is not attached
     */
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
            throw new Exception("Il componente " + c + " non è collegato");
        }
    }

    /**
     * Method the change the lamp's intensity
     * @param value
     */
    public void dimLamp(int value) {
        getLamp().setIntensity(value);
    }

    public String toString() {
        return "[Controller: " + name + ", " + components + ", " + lamp + ", " + battery + "]";
    }
}