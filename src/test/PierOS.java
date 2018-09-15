package test;

import exceptions.ControllerAlreadyAttachedException;
import hardware.*;
import helper.LAMP_TYPE;
import helper.SENSOR_TYPE;

import java.util.HashSet;

public class PierOS {
    public static void main(String args[]) {
        Lamp lamp = new Lamp(LAMP_TYPE.LED, 25);

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
        System.out.println(streetlight2);


    }
}