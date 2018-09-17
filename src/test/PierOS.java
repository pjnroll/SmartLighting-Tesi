package test;

import exceptions.CarAlreadyRunningException;
import exceptions.ComponentAlreadyAttachedException;
import exceptions.ControllerAlreadyAttachedException;
import hardware.*;
import helper.LAMP_TYPE;
import helper.SENSOR_TYPE;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class PierOS {
    public static void main(String args[]) {
        /*Lamp lamp = new Lamp(LAMP_TYPE.LED, 25);

        Sensor s1 = new Sensor(SENSOR_TYPE.LDR, "LDR1", 0, 20);
        Sensor s2 = new Sensor(SENSOR_TYPE.PIR, "PIR1", 0, 20);
        Battery battery = new Battery("VARTA", 9);
        HashSet<Component> components = new HashSet<>();
        components.add(s1);
        components.add(s2);
        components.add(lamp);
        components.add(battery);

        Controller controller = null;
        try {
            controller = new Controller("Arduino", components);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(controller);

        Streetlight streetlight = null;
        try {
            streetlight = new Streetlight(controller);
        } catch (ControllerAlreadyAttachedException e) {
            e.printStackTrace();
        }
        Streetlight streetlight2 = null;
        try {
            streetlight2 = new Streetlight(controller);
        } catch (ControllerAlreadyAttachedException e) {
            e.printStackTrace();
        }
        System.out.println(streetlight);
        System.out.println(streetlight2);*/

        Lamp[] lamps = new Lamp[20];
        for (int i = 0; i < 20; i++) {
            lamps[i] = new Lamp(LAMP_TYPE.LED, 25);
        }

        Sensor[] ldrSensors = new Sensor[20];
        for (int i = 0; i < 20; i++) {
            ldrSensors[i] = new Sensor(SENSOR_TYPE.LDR, ("LDR"+i), 0, 20);
        }

        Sensor[] pirSensors = new Sensor[20];
        for (int i = 0; i < 20; i++) {
            pirSensors[i] = new Sensor(SENSOR_TYPE.PIR, ("PIR"+i), 0, 20);
        }

        Battery[] batteries = new Battery[20];
        for (int i = 0; i < 20; i++) {
            batteries[i] = new Battery("BATT"+i, 9);
        }

        Controller[] controllers = new Controller[20];
        for (int i = 0; i < 20; i++) {
            HashSet<Component> components = new HashSet<>();
            components.add(lamps[i]);
            components.add(ldrSensors[i]);
            components.add(pirSensors[i]);
            components.add(batteries[i]);

            controllers[i] = new Controller("CONTROLLER"+i, components);
        }

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

        Street strada = new Street("Street", street);

        Car car = new Car(90, 0);  // 90km/h
        /*Car car2 = new Car(80, 0);  // 80km/h
        Car car3 = new Car(70, 0);  // 80km/h
        Car car4 = new Car(105, 0);  // 80km/h*/
        HashSet<Car> cars = new HashSet<>();
        cars.add(car);
        /*cars.add(car2);
        cars.add(car3);
        cars.add(car4);*/
        try {
            strada.setCars(cars);
        } catch (CarAlreadyRunningException e) {
            e.printStackTrace();
        }

        strada.turnOn();

        //Random random = new Random();
        strada.start();
        //System.out.println(strada);
    }
}