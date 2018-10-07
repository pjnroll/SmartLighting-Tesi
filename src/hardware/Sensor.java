package hardware;

import helper.SENSOR_TYPE;

import static hardware.Car.decel;
import static hardware.Street.coeff;

public class Sensor extends Component implements Comparable<Sensor> {
    private static int count_id = 0;
    private int id;

    private SENSOR_TYPE sensor_type;
    private String name;
    private int minThreshold;
    private int maxThreshold;

    private int range;
    private int dist;

    /**
     * This let creating a Sensor, either a PIR or a LDR.
     * When a PIR sensor is created, min threshold is equal to 0 and max threshold is the max detection distance
     * When a LDR sensor is created, it's going to have min and max threshold in which it works
     * @param sensor_type
     * @param name
     * @param minThreshold
     * @param maxThreshold
     */
    public Sensor(SENSOR_TYPE sensor_type, String name, int minThreshold, int maxThreshold) {
        id = count_id;
        count_id++;

        setSensor_type(sensor_type);
        setName(name);
        setMinThreshold(minThreshold);
        setMaxThreshold(maxThreshold);

        range = maxThreshold-minThreshold;

        setAttached(false);
        setController(null);
    }

    /** GETTERS SETTERS */
    private void setSensor_type(SENSOR_TYPE sensor_type) {
        this.sensor_type = sensor_type;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }

    private void setMaxThreshold(int maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public int getId() {
        return id;
    }

    private SENSOR_TYPE getSensor_type() {
        return sensor_type;
    }

    public String getName() {
        return name;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public int getMaxThreshold() {
        return maxThreshold;
    }
    /********************/

    public void detect() {
        boolean detected = false;
        int intensity = super.controller.getLamp().getIntensity();

        if (getSensor_type().equals(SENSOR_TYPE.LDR)) {
            if (Street.ACTUAL_STREET[getPosition()] != -1) {
                detected = true;
            }
        } else if (getSensor_type().equals(SENSOR_TYPE.PIR)) {
            int myPos = super.position;

            for (int i = myPos - (range / 2); i < myPos + (range / 2) && !detected; i++) {
                if (i > -1 && i < Street.ACTUAL_STREET.length && Street.ACTUAL_STREET[i] != -1) {
                    detected = true;
                }
            }
        }

        if (detected) {
            // Accendo i lampioni per garantire una visibilità per almeno 5 secondi
            getController().dimLamp(100);
            Street myStreet = super.controller.streetlight.getStreet();
            Car c;
            int speed;
            int spaceToTurnOn;
            boolean found = false;
            for (int i = super.position; i > super.position-Car.HEADLIGHTS_LENGTH && !found; i--) {
                if (Street.ACTUAL_STREET[i] != -1 && Street.ACTUAL_STREET[i] != -2) {
                    c = myStreet.findCarByPosition(i);
                    if (c != null) {    // guardia
                        //System.out.println("Trovato " + c + " in posizione " + i);
                        found = true;
                        speed = (int) (c.getSpeed()/3.6);
                        dist = (Street.ACTUAL_STREET.length-1)/(myStreet.getStreetlights().size()-1);    // distanza tra i lampioni
                        int stopSpace = (int) ((speed*speed)/(2*coeff*decel)); // spazio frenata = (v*v)/(2*coeff*a)   [coeff = 0.05, 0.4, 0.8; a = g = 9.8m/s^2)
                        int securitySpace = (speed)*5;    // velocità * 5 secondi
                        spaceToTurnOn = (stopSpace + securitySpace);
                        int lampToTurnOn = spaceToTurnOn/dist;

                        int index = super.position;
                        while (index + dist <= myStreet.getStreetLength() && lampToTurnOn > 0) {
                            index += dist;
                            myStreet.findStreetlightByPosition(index).getController().dimLamp(100);
                            lampToTurnOn--;
                        }
                    }
                }
            }
        } else if (intensity > 20) {
            intensity -= 20;
            getController().dimLamp(intensity);
        }
    }

    public String toString() {
        return "(Sensore: " + sensor_type.name() + " range " + minThreshold + "-" + maxThreshold + ")";
    }

    @Override
    public int compareTo(Sensor o) {
        int toRet = -1;
        if (this.getPosition() < o.getPosition()) {
            toRet = 1;
        }

        return toRet;
    }
}
