package test;

import exceptions.CarAlreadyRunningException;
import exceptions.ControllerAlreadyAttachedException;
import hardware.*;
import helper.LAMP_TYPE;
import helper.SENSOR_TYPE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class PierOS {
    public static void main(String args[]) {
        int n_streetlights = 50;
        int distance = 20;
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
        /*Sensor[] ldrSensors = new Sensor[n_streetlights];
        for (int i = 0; i < n_streetlights; i++) {
            ldrSensors[i] = new Sensor(SENSOR_TYPE.LDR, ("LDR" + i), 0, 10);
        }*/

        /**
         * Creo 20 sensori PIR
         */
        Sensor[] pirSensors = new Sensor[n_streetlights];
        for (int i = 0; i < n_streetlights; i++) {
            pirSensors[i] = new Sensor(SENSOR_TYPE.PIR, ("PIR"+i), 0, 20);
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
            //components.add(ldrSensors[i]);
            components.add(pirSensors[i]);
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
        Car[] macchine = new Car[10];
        for (int i = 0 ; i < 10; i++) {
            macchine[i] = new Car((random.nextInt(40) + 50), 0);
            cars.add(macchine[i]);
        }

        try {
            strada.setCars(cars);
        } catch (CarAlreadyRunningException e) {
            e.printStackTrace();
        }

        /*Car car = new Car((random.nextInt(40) + 50), 0);  // 90km/h
        Car car2 = new Car((random.nextInt(40) + 50), 0);  // 80km/h
        Car car3 = new Car((random.nextInt(40) + 50), 0);  // 80km/h*/

        /*cars.add(car);
        cars.add(car2);
        cars.add(car3);*/
        /*for (Car c : cars) {
            try {
                strada.setCar(c);
            } catch (CarAlreadyRunningException e) {
                e.printStackTrace();
            }
        }*/
        /*cars.add(car);
        cars.add(car2);
        cars.add(car3);*/

        /*try {
            strada.setCars(cars);
        } catch (CarAlreadyRunningException e) {
            e.printStackTrace();
        }*/

        /**
         * Accendo i lampioni
         */
        strada.turnOn();

        /**
         * Avvio le auto
         */
        Thread myStreet = new Thread(strada);
        myStreet.start();
        //System.out.println(strada);
    }
}