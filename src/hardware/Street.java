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
    private Lane[] lanes;

    private Car car;

    public Street(String name, LinkedList<Streetlight> streetlights, Lane[] lanes) {
        id = count_id;
        count_id++;

        setName(name);
        car = null;
        cars = new HashSet<>();
        this.streetlights = new LinkedList<>();
        setStreetlights(streetlights);

        this.lanes = new Lane[3];
        setLanes(lanes);
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

    public void setLanes(Lane[] lanes) {
        if (lanes != null) {
            System.arraycopy(lanes, 0, this.lanes, 0, lanes.length);
        }
    }

    /*public void setCar(Car car) throws CarAlreadyRunningException {
        if (car != null && !car.getRunning()) {
            this.car = car;
            car.setRunning(true);
            car.setStreet(this);
        } else if (car == null) {
            throw new NullPointerException();
        } else {
            throw new CarAlreadyRunningException(car);
        }
    }*/

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

    /*public void remCar(Car car) {
        if (car.getRunning() && getCar().equals(car)) {
            this.car = null;
            car.setRunning(false);
            car.setStreet(null);
        }
    }*/

    public HashSet<Car> getCars() {
        return cars;
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

    /*public void start() {
        if (car != null && car.getRunning()) {
            Thread myCar = new Thread(car);
            myCar.start();
        }
    }*/

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

        /*StringBuilder toRetCar = new StringBuilder();
        for (int i = 0; i < getCar().getPosition(); i++) {
            toRetCar.append("\t");
        }
        toRetCar.append("X");*/
        Car[] macchine = new Car[cars.size()];
        k = 0;
        for (Car c : cars) {
            macchine[k] = c;
            k++;
        }
        /*StringBuilder[] toRetCars = new StringBuilder[getCars().size()];
        for (int n = 0; n < getCars().size(); n++) {
            for (int i = 0; i < macchine[n].getPosition(); i++) {
                toRetCars[n].append("\t");
            }
            toRetCars[n].append("X");
        }*/
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