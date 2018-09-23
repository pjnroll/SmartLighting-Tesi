package hardware;

import helper.SENSOR_TYPE;

public class Sensor extends Component implements Comparable<Sensor> {
    private static int count_id = 0;
    private int id;

    private SENSOR_TYPE sensor_type;
    private String name;
    private int minThreshold;
    private int maxThreshold;

    // TODO Gestire altri costruttori e le eccezioni

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
        int myPos = getPosition();                                  //35
        int range = getMaxThreshold() - getMinThreshold();            //10
        int[] streetNow = getController().getStreetlight().getStreet().getArrayStreet();
        boolean detected = false;

        if (getSensor_type().equals(SENSOR_TYPE.PIR)) {
            //System.out.println(getController().getStreetlight().getId() + " CONTROLLA DA " + (myPos-(range/2)) + " a " + (myPos+(range/2)));
            for (int i = myPos - (range / 2); i < myPos + (range / 2) && !detected; i++) {
                if (i > -1 && i < streetNow.length && streetNow[i] != -1) {
                    detected = true;
                }
            }
        } else if (getSensor_type().equals(SENSOR_TYPE.LDR)) {
            if (streetNow[myPos] != -1) {
                detected = true;
            }
        }

        if (detected) {
            // Accendo i lampioni per garantire una visibilità per almeno 5 secondi
            getController().dimLamp(100);
            Street myStreet = getController().getStreetlight().getStreet();
            Car c;
            int speed;
            int spaceToTurnOn;

            for (int i = myPos; i > 0; i--) {
                c = myStreet.findCarByPosition(i);
                if (c != null) {
                    speed = c.getSpeed();
                    for (Streetlight s : myStreet.getStreetlights()) {
                        if (s.getPosition() == myPos) {
                            int dist = (myStreet.getStreetLength()-1)/(myStreet.getStreetlights().size()-1);
                            int stopTime = (int) ((speed*speed)/15.68);
                            spaceToTurnOn = (int) ((speed/3.6) + stopTime);
                            int lampToTurnOn = spaceToTurnOn/dist;
                            int index = myPos;
                            while (index + dist <= myStreet.getStreetLength() && lampToTurnOn > 0) {
                                index += dist;
                                myStreet.findStreetlightByPosition(index).getController().dimLamp(100);
                                lampToTurnOn--;
                            }
                        }
                    }
                }
            }
        } else {
            getController().dimLamp(20);
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
