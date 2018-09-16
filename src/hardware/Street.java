package hardware;

import exceptions.CarAlreadyRunningException;
import exceptions.ComponentAlreadyAttachedException;

import java.util.LinkedList;

public class Street {
    private static int count_id = 0;
    private int id;

    private String name;
    private LinkedList<Streetlight> streetlights;
    private Car car;

    public Street(String name, LinkedList<Streetlight> streetlights) {
        id = count_id;
        count_id++;

        setName(name);
        car = null;
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
        for (Streetlight s : streetlights) {
            addStreetlight(s);
        }
    }

    public void setCar(Car car) throws CarAlreadyRunningException {
        if (car != null && !car.getRunning()) {
            this.car = car;
            car.setRunning(true);
            car.setStreet(this);
            System.out.println("Diversa da null aggiunta");
        } else if (car == null) {
            throw new NullPointerException();
        } else {
            throw new CarAlreadyRunningException(car);
        }
    }

    public void remCar(Car car) {
        if (car.getRunning() && getCar().equals(car)) {
            this.car = null;
            car.setRunning(false);
            car.setStreet(null);
        }
    }

    public Car getCar() {
        return car;
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
        StringBuilder toRetStreetLights = new StringBuilder();
        for (Streetlight s : streetlights) {
            toRetStreetLights.append(s.getController().getLamp().getIntensity()).append("\t");
        }

        /*StringBuilder toRetCar = new StringBuilder();
        for (int i = 0; i < getCar().getPosition(); i++) {
            toRetCar.append("\t");
        }
        toRetCar.append("X");*/
        String toRetCar = "";
        for (int i = 0; i < getCar().getPosition(); i++) {
            toRetCar += " ";
        }
        toRetCar += "X";

        return toRetStreetLights.toString() + "\n" + toRetCar;
    }
}