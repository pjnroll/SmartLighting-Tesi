package hardware;

import helper.SENSOR_TYPE;

import java.util.Arrays;

public class Sensor extends Component {
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
    public void setSensor_type(SENSOR_TYPE sensor_type) {
        this.sensor_type = sensor_type;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }

    public void setMaxThreshold(int maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public int getId() {
        return id;
    }

    public SENSOR_TYPE getSensor_type() {
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

    public void detect(int value) {
        int myPos = getPosition();
        if (getSensor_type().equals(SENSOR_TYPE.PIR)) {
            int range = getMaxThreshold()-getMinThreshold();
            //System.out.println(getController().getStreetlight().getId() + " CONTROLLA DA " + (myPos-(range/2)) + " a " + (myPos+(range/2)));
            int[] streetNow = getController().getStreetlight().getStreet().getArrayStreet();
            boolean detected = false;
            for (int i = myPos-(range/2); i < myPos+(range/2) && !detected; i++) {
                if (i > -1 && i < streetNow.length && streetNow[i] != -1) {
                    detected = true;
                }
            }
            if (detected) {
                getController().dimLamp(100);
            } else {
                getController().dimLamp(20);
            }
            /*if (!Arrays.equals(getController().getStreetlight().getStreet().getArrayStreet(), empty)) {
                getController().dimLamp(100);
            } else {
                getController().dimLamp(20);
            }*/


        } else if (getSensor_type().equals(SENSOR_TYPE.LDR)) {
            // il fascio di luce emesso dai fanali è di circa 10 metri, quindi
            // value corrisponde all'intensità
            if (myPos > value && myPos <= value+getMaxThreshold()-getMinThreshold()) { // 35 >= 16 && 35 <= 16+10
                getController().dimLamp(100);
            } else {
                getController().dimLamp(20);
            }

        }
    }

    /*public void read(int pos) {
        Street thisStreet = getController().getStreetlight().getStreet();
        getController().di
    }*/


    public String toString() {
        return "(Sensore: " + sensor_type.name() + " range " + minThreshold + "-" + maxThreshold + ")";
    }
}
