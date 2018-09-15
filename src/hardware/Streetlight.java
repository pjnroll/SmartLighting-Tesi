package hardware;

import exceptions.ControllerAlreadyAttachedException;

public class Streetlight {
    private static int count_id = 0;
    private int id;

    private Controller controller;

    public Streetlight(Controller controller) throws ControllerAlreadyAttachedException {
        id = count_id;
        count_id++;

        setController(controller);
    }


    /** GETTERS SETTERS */
    public void setController(Controller controller) throws ControllerAlreadyAttachedException {
        if (controller != null && !controller.getAttached()) {
            this.controller = controller;
            controller.setAttached(true);
            controller.setStreetlight(this);
        } else if (controller == null) {
            throw new NullPointerException("Il controller è nullo");
        } else {
            throw new ControllerAlreadyAttachedException(controller);
        }
    }

    public int getId() {
        return id;
    }

    public Controller getController() {
        return controller;
    }

    /********************/

    public void remController(Controller controller) {
        if (controller.getAttached() && getController().equals(controller)) {
            this.controller = null;
            controller.setAttached(false);
            controller.setStreetlight(null);
        }
    }

    public String toString() {
        return "{Lampione: " + id + " " + controller + "}";
    }
}