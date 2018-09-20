package hardware;

import exceptions.CarAlreadyRunningException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class Street {
    private static int count_id = 0;
    private int id;

    private String name;
    private int streetLength;

    private LinkedList<Streetlight> streetlights;
    private HashSet<Car> cars;

    private HashSet<Sensor> sensors;

    private int[] street;

    public Street(String name, LinkedList<Streetlight> streetlights, int streetLenght) {
        id = count_id;
        count_id++;

        setName(name);
        setStreetLength(streetLenght);

        street = new int[getStreetLength()];
        Arrays.fill(street, -1);

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

    private void setName(String name) {
        this.name = name;
    }

    private void setStreetlights(LinkedList<Streetlight> streetlights) {
        for (Streetlight s : streetlights) {
            addStreetlight(s);
        }
    }

    private LinkedList<Streetlight> getStreetlights() {
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

    private HashSet<Car> getCars() {
        return cars;
    }

    public int getStreetLength() {
        return streetLength;
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

    private void addStreetlight(Streetlight streetlight) {
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

    public void setInStreet(int position, int value) {
        street[position] = value;
    }

    public int getFromStreet(int position) {
        return street[position];
    }

    public int[] getArrayStreet() {
        return street;
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
        for (int i = 0; i < streetLength; i++) {
            asse.append(i).append("\t");
            if (k < lampioni.length && i == lampioni[k].getPosition()) {
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
        return /*asse + "\n" + */toRetStreetLights.toString() + "\n" + corsie + "\n\n\n\n";
    }

    public void setStreetLength(int streetLength) {
        this.streetLength = streetLength;
    }
}