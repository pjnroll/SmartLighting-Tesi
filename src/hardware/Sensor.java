package hardware;

import helper.SENSOR_TYPE;

public class Sensor extends Component {
    private static int count_id = 0;
    private int id;

    private SENSOR_TYPE sensor_type;
    private String name;
    private int minThreshold;
    private int maxThreshold;

    // TODO Gestire altri costruttori e le eccezioni
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

//    /**
//     * Return true if the Sensor is already installed on a Controller
//     * @return
//     */
//    public boolean isAttached() {
//        return getAttached();
//    }

//    /**
//     * This method return true iff it can attach the Sensor to the Controller
//     *
//     * Maybe deprecated
//     * @param controller
//     * @return
//     */
//    public boolean attachTo(Controller controller) {
//        boolean toRet = false;
//
//        if (controller != null && !isAttached()) {
//            controller.getComponents().add(this);
//            setAttached(true);
//            this.controller = controller;
//
//            toRet = true;
//        }
//        return toRet;
//    }

    public String toString() {
        return "(Sensore: " + sensor_type.name() + " range " + minThreshold + "-" + maxThreshold + ")";
    }
}
