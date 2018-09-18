package hardware;

import exceptions.CarAlreadyRunningException;

import java.util.HashSet;
import java.util.LinkedList;

public class Street implements Runnable {
    private static int count_id = 0;
    private int id;

    private String name;
    private LinkedList<Streetlight> streetlights;
    private HashSet<Car> cars;

    private HashSet<Sensor> sensors;

    private Car car;

    public Street(String name, LinkedList<Streetlight> streetlights) {
        id = count_id;
        count_id++;

        setName(name);
        car = null;
        cars = new HashSet<>();
        this.streetlights = new LinkedList<>();
        setStreetlights(streetlights);
        sensors = new HashSet<>();
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

    public LinkedList<Streetlight> getStreetlights() {
        return streetlights;
    }

    public void setCar(Car car) throws CarAlreadyRunningException {
        if (car != null && !car.getRunning()) {
            cars.add(car);
            car.setRunning(true);
            car.setStreet(this);
        } else if (car == null) {
            throw new NullPointerException();
        } else {
            throw new CarAlreadyRunningException(car);
        }
    }

    public void setCars(HashSet<Car> cars) throws CarAlreadyRunningException {
        if (cars != null) {
            for (Car c : cars) {
                setCar(c);
            }
        }
    }

    public HashSet<Car> getCars() {
        return cars;
    }

    private HashSet<Sensor> collectSensors() {
        HashSet<Sensor> sensors = new HashSet<>();
        for (Streetlight s : getStreetlights()) {
            HashSet<Component> components = s.getController().getComponents();
            for (Component c : components) {
                if (c instanceof Sensor) {
                    sensors.add((Sensor) c);
                }
            }
        }
        return sensors;
    }

    public void addStreetlight(Streetlight streetlight) {
        if (streetlight != null && !streetlight.getAttached()) {
            streetlights.addLast(streetlight);
            streetlight.setAttached(true);
            streetlight.setStreet(this);
        }

    }

    public void turnOn() {
        sensors = collectSensors();
        for (Streetlight s : streetlights) {
            s.turnOnLamp();
        }
    }

    public HashSet<Sensor> getSensors() {
        return sensors;
    }

    public void turnOff() {
        for (Streetlight s : streetlights) {
            s.turnOffLamp();
        }
    }

    public Streetlight findStreetlightByPosition(int position) {
        Streetlight toRet = null;
        for (Streetlight s : getStreetlights()) {
            if (s.getPosition() == position) {
                toRet = s;
            }
        }
        return toRet;
    }

    public Car findCarByPosition(int position) {
        Car toRet = null;
        for (Car c : getCars()) {
            if (c.getPosition() == position) {
                toRet = c;
            }
        }
        return toRet;
    }

    public void start() {
        if (cars != null) {
            for (Car c : cars) {
                if (c.getRunning()) {
                    Thread myThread = new Thread(c);
                    myThread.start();
                }
            }
        }
    }

    @Override
    public void run() {

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

        Car[] macchine = new Car[cars.size()];
        k = 0;
        for (Car c : cars) {
            macchine[k] = c;
            k++;
        }

        String[] toRetCars = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            toRetCars[i] = "";
            for (int z = 0; z < macchine[i].getPosition(); z++) {
                toRetCars[i] += "\t";
            }
            toRetCars[i] += macchine[i].getId();
        }

        StringBuilder corsie = new StringBuilder();
        for (int i = 0; i < getCars().size(); i++) {
            corsie.append(toRetCars[i]).append("\n");
        }
        return asse + "\n" + toRetStreetLights.toString() + "\n" + corsie + "\n\n\n\n";
    }
}