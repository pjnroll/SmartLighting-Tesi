package test;

import exceptions.CarAlreadyRunningException;
import exceptions.ControllerAlreadyAttachedException;
import hardware.*;
import helper.LAMP_TYPE;
import helper.SENSOR_TYPE;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class MainClass {
    private final static int n_cars = 30000;

    public static void main(String args[]) {
        int n_streetlights = 577;
        int distance = 35;
        int streetLength = ((n_streetlights-1)*distance)+1;

        /**
         * Creo 20 lampade
         */
        Lamp[] lamps = new Lamp[n_streetlights];
        for (int i = 0; i < n_streetlights; i++) {
            lamps[i] = new Lamp(LAMP_TYPE.LED, 150);
        }

        /**
         * Creo 20 sensori LDR
         */
        Sensor[] ldrSensors = new Sensor[n_streetlights];
        for (int i = 0; i < n_streetlights; i++) {
            ldrSensors[i] = new Sensor(SENSOR_TYPE.LDR, ("LDR" + i), 0, 10);
        }

        /**
         * Creo 20 batterie
         */
        Battery[] batteries = new Battery[n_streetlights];
        for (int i = 0; i < n_streetlights; i++) {
            batteries[i] = new Battery("BATT"+i, 9);
        }

        /**
         * Creo 20 controller a cui aggiungo le lampade, i sensori e le batterie
         */
        Controller[] controllers = new Controller[n_streetlights];
        for (int i = 0; i < n_streetlights; i++) {
            HashSet<Component> components = new HashSet<>();
            components.add(lamps[i]);
            components.add(ldrSensors[i]);
            components.add(batteries[i]);

            controllers[i] = new Controller("CONTROLLER"+i, components);
        }

        /**
         * Creo 20 lampioni a cui aggiungo i controller
         */
        Streetlight[] streetlights = new Streetlight[n_streetlights];
        int position = 0;
        for (int i = 0; i < n_streetlights; i++) {
            try {
                streetlights[i] = new Streetlight(controllers[i], position);
                position += distance;     // metri di distanza dal lampione successivo
            } catch (ControllerAlreadyAttachedException e) {
                e.printStackTrace();
            }
        }

        LinkedList<Streetlight> street = new LinkedList<>();
        for (int i = 0; i < n_streetlights; i++) {
            street.addLast(streetlights[i]);
        }

        /**
         * Creo una strada a cui aggiungo i lampioni
         */
        Street strada = new Street("Street", street, streetLength);

        /**
         * Creo le auto e le aggiungo alla strada
         */
        Random random = new Random();
        HashSet<Car> cars = new HashSet<>();
        Car[] macchine = new Car[n_cars];
        for (int i = 0 ; i < n_cars; i++) {
            macchine[i] = new Car((random.nextInt(50) + 60), (int) (random.nextInt(streetLength)-streetLength*2));
            cars.add(macchine[i]);
        }

        try {
            strada.setAllCars(cars);
        } catch (CarAlreadyRunningException e) {
            e.printStackTrace();
        }

        /**
         * Accendo i lampioni
         */
        strada.turnOn();

        /**
         * Avvio le auto
         */
        Thread myStreet = new Thread(strada);
        myStreet.start();
    }
}