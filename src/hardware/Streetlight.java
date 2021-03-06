package hardware;

import exceptions.ControllerAlreadyAttachedException;
import helper.LAMP_STATUS;

public class Streetlight {
    private static int count_id = 0;
    private int id;

    private Controller controller;

    private boolean attached;
    private Street street;

    private int position;

    public Streetlight(Controller controller, int position) throws ControllerAlreadyAttachedException {
        id = count_id;
        count_id++;

        setPosition(position);
        setController(controller);
        setAttached(false);
        setStreet(null);
    }

    /** GETTERS SETTERS */
    private void setController(Controller controller) throws ControllerAlreadyAttachedException {
        if (controller != null && !controller.getAttached()) {
            this.controller = controller;
            controller.setAttached(true);
            controller.setStreetlight(this);
            controller.setPosition(position);
        } else if (controller == null) {
            throw new NullPointerException("Il controller è nullo");
        } else {
            throw new ControllerAlreadyAttachedException(controller);
        }
    }

    public int getId() {
        return id;
    }

    private void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public Controller getController() {
        return controller;
    }

    public boolean getAttached() {
        return attached;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }
    /********************/

    public void remController(Controller controller) {
        if (controller.getAttached() && getController().equals(controller)) {
            this.controller = null;
            controller.setAttached(false);
            controller.setStreetlight(null);
        }
    }


    public void turnOnLamp() {
        Lamp toTurnOn = getController().getLamp();
        if (toTurnOn != null && toTurnOn.getStatus().equals(LAMP_STATUS.OFF)) {
            toTurnOn.setStatus(LAMP_STATUS.ON);
            toTurnOn.setIntensity(20);
        }
    }

    public void turnOffLamp() {
        Lamp toTurnOff = getController().getLamp();
        if (toTurnOff != null && toTurnOff.getStatus().equals(LAMP_STATUS.ON)) {
            toTurnOff.setIntensity(20);
            toTurnOff.setStatus(LAMP_STATUS.OFF);
        }
    }


    public String toString() {
        return "{Lampione: " + id + " " + controller + "}";
    }
}