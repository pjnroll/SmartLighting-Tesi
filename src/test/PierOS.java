package test;

import exceptions.CarAlreadyRunningException;
import exceptions.ControllerAlreadyAttachedException;
import hardware.*;
import helper.LAMP_TYPE;
import helper.SENSOR_TYPE;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class PierOS {
    public static void main(String args[]) {
        /**
         * Creo 20 lampade
         */
        Lamp[] lamps = new Lamp[20];
        for (int i = 0; i < 20; i++) {
            lamps[i] = new Lamp(LAMP_TYPE.LED, 25);
        }

        /**
         * Creo 20 sensori LDR
         */
        Sensor[] ldrSensors = new Sensor[20];
        for (int i = 0; i < 20; i++) {
            ldrSensors[i] = new Sensor(SENSOR_TYPE.LDR, ("LDR"+i), 0, 10);
        }

        /**
         * Creo 20 sensori PIR
         */
        /*Sensor[] pirSensors = new Sensor[20];
        for (int i = 0; i < 20; i++) {
            pirSensors[i] = new Sensor(SENSOR_TYPE.PIR, ("PIR"+i), 0, 20);
        }*/

        /**
         * Creo 20 batterie
         */
        Battery[] batteries = new Battery[20];
        for (int i = 0; i < 20; i++) {
            batteries[i] = new Battery("BATT"+i, 9);
        }

        /**
         * Creo 20 controller a cui aggiungo le lampade, i sensori e le batterie
         */
        Controller[] controllers = new Controller[20];
        for (int i = 0; i < 20; i++) {
            HashSet<Component> components = new HashSet<>();
            components.add(lamps[i]);
            components.add(ldrSensors[i]);
            //components.add(pirSensors[i]);
            components.add(batteries[i]);

            controllers[i] = new Controller("CONTROLLER"+i, components);
        }

        /**
         * Creo 20 lampioni a cui aggiungo i controller
         */
        Streetlight[] streetlights = new Streetlight[20];
        int position = 0;
        for (int i = 0; i < 20; i++) {
            try {
                streetlights[i] = new Streetlight(controllers[i], position);
                position += 35;     // metri di distanza dal lampione successivo
            } catch (ControllerAlreadyAttachedException e) {
                e.printStackTrace();
            }
        }

        LinkedList<Streetlight> street = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            street.addLast(streetlights[i]);
        }

        /**
         * Creo una strada a cui aggiungo i lampioni
         */
        Street strada = new Street("Street", street);

        /**
         * Creo le auto e le aggiungo alla strada
         */
        Random random = new Random();
        Car car = new Car(90, 0);  // 90km/h
        /*Car car2 = new Car((random.nextInt(40) + 50), random.nextInt(140));  // 80km/h
        Car car3 = new Car((random.nextInt(40) + 50), random.nextInt(140));  // 80km/h*/
        HashSet<Car> cars = new HashSet<>();
        cars.add(car);
        /*cars.add(car2);
        cars.add(car3);*/
        try {
            strada.setCars(cars);
        } catch (CarAlreadyRunningException e) {
            e.printStackTrace();
        }

        /**
         * Accendo i lampioni
         */
        strada.turnOn();

        System.out.println("Posizioni iniziali");
        for (Car c : cars) {
            System.out.println(c.getPosition());
        }

        /**
         * Avvio le auto
         */
        strada.start();
        //System.out.println(strada);
    }
}