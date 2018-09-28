package hardware;

import exceptions.CarAlreadyRunningException;

import java.util.*;

public class Street implements Runnable {
    private static int count_id = 0;


    public static int[] ACTUAL_STREET;
    private int id;

    private String name;
    private int streetLength;

    private LinkedList<Streetlight> streetlights;

    private HashSet<Car> carsToSet;
    private TreeSet<Car> cars;

    private TreeSet<Sensor> sensors;

    private double totalConsumption;

    private int secondi;

    public Street(String name, LinkedList<Streetlight> streetlights, int streetLenght) {
        id = count_id;
        count_id++;

        setName(name);
        setStreetLength(streetLenght);

        ACTUAL_STREET = new int[streetLenght];
        Arrays.fill(ACTUAL_STREET, -1);

        carsToSet = new HashSet<>();
        cars = new TreeSet<>();
        this.streetlights = new LinkedList<>();
        setStreetlights(streetlights);
        sensors = new TreeSet<>();

        totalConsumption = 0.0;
        secondi = 0;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setStreetlights(LinkedList<Streetlight> streetlights) {
        for (Streetlight s : streetlights) {
            addStreetlight(s);
        }
    }

    public LinkedList<Streetlight> getStreetlights() {
        return streetlights;
    }

    private void setCar(Car car) throws CarAlreadyRunningException {
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

    public void startCar() {
        for (Car c : carsToSet) {
            cars.add(c);
            carsToSet.remove(c);
        }
    }

    private TreeSet<Car> getCars() {
        return cars;
    }

    public int getStreetLength() {
        return streetLength;
    }

    private TreeSet<Sensor> collectSensors() {
        TreeSet<Sensor> sensors = new TreeSet<>();
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

    private TreeSet<Sensor> getSensors() {
        return sensors;
    }

    public void remCar(Car car) {
        getCars().remove(car);
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

        StringBuilder corsia = new StringBuilder();
        for (int i = 0; i < getStreetLength(); i++) {
            corsia.append(ACTUAL_STREET[i]).append("\t");
        }


        return asse + "\n" + toRetStreetLights.toString() + "\n" + corsia + "\n\n\n";
    }

    private void setStreetLength(int streetLength) {
        this.streetLength = streetLength;
    }

    public double getConsumption() {
        double consumption = 0.0;


        return consumption;
    }

    private double getTotalWatts() {
        double toRet = 0.0;
        for (Streetlight s : streetlights) {
            Lamp l = s.getController().getLamp();
            double consumption = l.getWatt()*l.getIntensity()/100;
            toRet += consumption;
        }

        return toRet;
    }

    @Override
    public void run() {
        int tot = cars.size();
        for (Sensor s : getSensors()) {
            s.detect();
        }
        //System.out.println(this);
        do {
            totalConsumption += getTotalWatts();   // Consumo in Ws
            //int cont = 0;
            try {
                for (Car c : cars) {
                    if (c.getPosition() > 0) {
                        cont++;
                    }
                }
                Arrays.fill(ACTUAL_STREET, -1);
                //System.out.println("Auto in carreggiata: " + cont);
                Iterator<Car> it = cars.iterator();
                while (it.hasNext()) {
                    Car c = it.next();
                    if (c.getRunning()) {
                        c.run();
                    } else {
                        it.remove();
                    }
                }
                for (Sensor s : getSensors()) {
                    s.detect();
                }
                secondi++;
                //System.out.println(this);
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
            if (secondi % 100 == 0) {
                System.out.println("Completamento simulazione " + (100*(tot-cars.size()))/tot + "% dopo " + secondi + " secondi");
            }
            //System.out.println("Secondi " + secondi);
        } while (!getCars().isEmpty() && secondi < 3600);
        System.out.println("Completamento simulazione 100%");
        totalConsumption = totalConsumption/3600;
        System.out.println("Tempo impiegato " + secondi + " secondi");
        System.out.println("Consumo totale " + totalConsumption + "Wh\nConsumo totale " + totalConsumption/1000 + "kWh\nConsumo totale " + totalConsumption*0.15/1000 + "Eur");
    }

    public void foo() {
        int tot = cars.size();
        for (Sensor s : getSensors()) {
            //System.out.println("Pos " + s.getPosition());
            s.detect();
        }
        //System.out.println(this);
        do {
            totalConsumption += getTotalWatts();   // Consumo in Ws
            int cont = 0;
            try {
                // NON DECOMMENTARE
                /*for (Sensor s : getSensors()) {
                    s.detect();
                }*/
                for (Car c : cars) {
                    if (c.getPosition() > 0) {
                        cont++;
                    }
                }
                Arrays.fill(ACTUAL_STREET, -1);
                //System.out.println("Auto in carreggiata: " + cont);
                Iterator<Car> it = cars.iterator();
                while (it.hasNext()) {
                    Car c = it.next();
                    if (c.getRunning()) {
                        c.run();
                    } else {
                        it.remove();
                    }
                }
                for (Sensor s : getSensors()) {
                    s.detect();
                }
                //Thread.sleep(1);
                secondi++;
                //System.out.println(this);
            } catch (/*InterruptedException |*/ ConcurrentModificationException e) {
                e.printStackTrace();
            }
            if (secondi % 100 == 0) {
                System.out.println("Completamento simulazione " + (100*(tot-cars.size()))/tot + "%");
            }
        } while (!getCars().isEmpty());
        System.out.println("Completamento simulazione 100%");
        totalConsumption = totalConsumption/3600;
        System.out.println("Tempo impiegato " + secondi + " secondi");
        System.out.println("Consumo totale " + totalConsumption + "Wh\nConsumo totale " + totalConsumption/1000 + "kWh\nConsumo totale " + totalConsumption*0.15/1000 + "Eur");
    }
}