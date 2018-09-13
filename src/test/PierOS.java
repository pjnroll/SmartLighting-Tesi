package test;

import hardware.Battery;
import hardware.Controller;
import hardware.Lamp;
import helper.LAMP_TYPE;
import helper.SENSOR_TYPE;
import hardware.Sensor;

import java.util.HashSet;

public class PierOS {
    public static void main(String args[]) {
        //Sensor LDR1 = new Sensor(SENSOR_TYPE.LDR, 10, 50);

        //System.out.println(LDR1);
        Lamp lamp = new Lamp(LAMP_TYPE.LED, 25);
        Controller controller = new Controller("Arduino", lamp);
        Sensor s1 = new Sensor(SENSOR_TYPE.LDR, "LDR1", 0, 20);
        Sensor s2 = new Sensor(SENSOR_TYPE.PIR, "PIR1", 0, 20);
        Battery battery = new Battery("VARTA", 9);
        HashSet<Sensor> sensors = new HashSet<>();
        sensors.add(s1);
        sensors.add(s2);
        controller.addComponent(battery);
        controller.setSensors(sensors);

        System.out.println(controller);

    }
}