package hardware;

import exceptions.CarAlreadyRunningException;

import java.io.*;
import java.util.*;

public class Street implements Runnable {
    public final static double coeff = 0.8;
    public final static int OPERATIVE_HOURS = 11;

    private static int count_id = 0;


    public static int[] ACTUAL_STREET;
    private int id;

    private String name;
    private int streetLength;

    private LinkedList<Streetlight> streetlights;

    private HashSet<Car> allCars;
    private TreeSet<Car> inCars;
    private TreeSet<Car> outCars;

    private TreeSet<Sensor> sensors;

    private double totalConsumption;

    private int capacity;
    private int density;
    private int secondi;

    public Street(String name, LinkedList<Streetlight> streetlights, int streetLenght) {
        id = count_id;
        count_id++;

        setName(name);
        setStreetLength(streetLenght);

        ACTUAL_STREET = new int[streetLenght];
        Arrays.fill(ACTUAL_STREET, -1);

        allCars = new HashSet<>();

        inCars = new TreeSet<>();
        outCars = new TreeSet<>();

        this.streetlights = new LinkedList<>();
        setStreetlights(streetlights);
        sensors = new TreeSet<>();

        totalConsumption = 0.0;

        capacity = (streetLenght/4)*3;
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
            allCars.add(car);
            car.setRunning(true);
            car.setStreet(this);
        } else if (car == null) {
            throw new NullPointerException();
        } else {
            throw new CarAlreadyRunningException(car);
        }
    }

    public void setAllCars(HashSet<Car> allCars) throws CarAlreadyRunningException {
        if (allCars != null) {
            for (Car c : allCars) {
                setCar(c);
            }
        }
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

    /*public void remCar(Car car) {
        getAllCars().remove(car);
    }*/

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
        HashMap<Integer, Car> cars = new HashMap<>();
        for (Car c : inCars) {
            cars.put(c.getPosition(), c);
        }

        return cars.get(position);
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
            asse.append(i).append("\t\t\t");
            if (k < lampioni.length && i == lampioni[k].getPosition()) {
                toRetStreetLights.append(lampioni[k].getController().getLamp().getIntensity());
                k++;
            }
            toRetStreetLights.append("\t\t\t");
        }

        Car[] macchine = new Car[allCars.size()];
        k = 0;
        for (Car c : allCars) {
            macchine[k] = c;
            k++;
        }

        String[] toRetCars = new String[allCars.size()];
        for (int i = 0; i < allCars.size(); i++) {
            toRetCars[i] = "";
            for (int z = 0; z < macchine[i].getPosition(); z++) {
                toRetCars[i] += "\t\t\t";
            }
            toRetCars[i] += macchine[i].getId();
        }

        StringBuilder corsia = new StringBuilder();
        for (int i = 0; i < getStreetLength(); i++) {
            corsia.append(ACTUAL_STREET[i]).append("\t\t\t");
        }


        return asse + "\n" + toRetStreetLights.toString() + "\n" + corsia + "\n\n\n";
    }

    private void setStreetLength(int streetLength) {
        this.streetLength = streetLength;
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
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Pj94\\Desktop\\simulazione.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fw);

            boolean empty = true;
            int cont = 0;
            density = (int) Math.ceil(allCars.size()/(3600*OPERATIVE_HOURS));    // Auto al secondo; 39.600 = 3.600s * 11h, durata funzionamento del sistema
            int densityMax = density;
            int densityMin = density--;
            int tot = allCars.size();
            for (Sensor s : getSensors()) {
                s.detect();
            }

            // Divido le auto tra quelle in strada e quelle fuori dalla strada
            Iterator<Car> allCarsIt = allCars.iterator();
            while (allCarsIt.hasNext()) {
                Car c = allCarsIt.next();
                if (c.getRunning()) {
                    c.run();
                } else {
                    allCarsIt.remove();
                }
                if (c.getPosition() > -1) {
                    inCars.add(c);
                    cont++;
                    empty = false;
                } else {
                    outCars.add(c);
                }

                allCarsIt.remove();
            }

            do {
                totalConsumption += getTotalWatts();   // Consumo in Ws

                if (secondi > 21600 && secondi < 36000) {
                    density = densityMin;
                } else {
                    density = densityMax;
                }

                try {
                    Arrays.fill(ACTUAL_STREET, -1);
                    Iterator<Car> inCarsIt = inCars.iterator();
                    while (inCarsIt.hasNext()) {
                        Car c = inCarsIt.next();
                        if (c.getRunning()) {
                            c.run();
                        } else {
                            inCarsIt.remove();
                        }
                    }
                    if (inCars.size() < capacity) {
                        Iterator<Car> outCarsIt = outCars.iterator();
                        int go = 0;
                        while (outCarsIt.hasNext() && go < density) {
                            Car c = outCarsIt.next();
                            if (c.getRunning()) {
                                c.run();
                                if (c.getPosition() > -1) {
                                    go++;
                                    inCars.add(c);
                                    cont++;
                                    empty = false;
                                    outCarsIt.remove();
                                }
                            }
                        }
                    }
                    if (!empty) {
                        for (Sensor s : getSensors()) {
                            s.detect();
                        }
                    }
                    secondi++;
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
                if (secondi % 1000 == 0) {
                    System.out.println("Completamento simulazione " + (100*cont/tot) + "% dopo " + secondi + " secondi");
                    System.out.println("Auto in carreggiata: " + inCars.size());
                    System.out.println(this);
                    bufferedWriter.write(this.toString());
                }
                //System.out.println("Secondi " + secondi);
            } while ((!inCars.isEmpty() || !outCars.isEmpty()) && secondi < 50400);
            System.out.println("Completamento simulazione 100%\nAuto transitate " + cont);
            totalConsumption /= 3600;
            System.out.println("Tempo impiegato " + secondi + " secondi");
            System.out.println("Consumo totale " + totalConsumption + "Wh\nConsumo totale " + totalConsumption/1000 + "kWh\nConsumo totale " + totalConsumption*0.15/1000 + "Eur");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}