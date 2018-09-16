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
        StringBuilder asse = new StringBuilder();
        StringBuilder toRetStreetLights = new StringBuilder();

        Streetlight[] lampioni = new Streetlight[streetlights.size()];
        int j = 0;
        for (Streetlight s : streetlights) {
            lampioni[j] = s;
            j++;
        }

        int k = 0;
        for (int i = 0; i < 140; i++) {
            asse.append(i + 1).append("\t");
            if (i == lampioni[k].getPosition()) {
                toRetStreetLights.append(lampioni[k].getController().getLamp().getIntensity());
                k++;
            }
            toRetStreetLights.append("\t");
        }

        /*for (Streetlight s : streetlights) {
            toRetStreetLights.append(s.getController().getLamp().getIntensity()).append("\t");
        }*/

        StringBuilder toRetCar = new StringBuilder();
        for (int i = 0; i < getCar().getPosition(); i++) {
            toRetCar.append(" ");
        }
        toRetCar.append("X");

        return asse + "\n" + toRetStreetLights.toString() + "\n" + toRetCar + "\n\n";
    }
}