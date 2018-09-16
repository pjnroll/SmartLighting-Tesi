package hardware;

import java.util.LinkedList;

public class Street {
    private static int count_id = 0;
    private int id;

    private String name;
    private LinkedList<Streetlight> streetlights;

    public Street(String name, LinkedList<Streetlight> streetlights) {
        id = count_id;
        count_id++;

        setName(name);
        this.streetlights = new LinkedList<>();
        setStreetlights(streetlights);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetlights(LinkedList<Streetlight> streetlights) {
        //this.streetlights = streetlights;
        for (Streetlight s : streetlights) {
            addStreetlight(s);
        }
    }

    public void addStreetlight(Streetlight streetlight) {
        if (streetlight != null && !streetlight.getAttached()) {
            streetlights.addLast(streetlight);
            streetlight.setAttached(true);
            streetlight.setStreet(this);
        }

    }

    public void turnOn() {
        for (Streetlight s : streetlights) {
            s.turnOnLamp();
        }
    }

    public void turnOff() {
        for (Streetlight s : streetlights) {
            s.turnOffLamp();
        }
    }

    @Override
    public String toString() {
        StringBuilder toRet = new StringBuilder();
        for (Streetlight s : streetlights) {
            toRet.append(s.getController().getLamp().getIntensity()).append("\t");
        }

        return toRet.toString();
    }
}