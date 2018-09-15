package hardware;

public class Streetlight {
    private static int count_id = 0;
    private int id;

    private Controller controller;

    public Streetlight(Controller controller) {
        id = count_id;
        count_id++;

        this.controller = controller;
    }


    /** GETTERS SETTERS */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public int getId() {
        return id;
    }

    public Controller getController() {
        return controller;
    }

    /********************/

    public String toString() {
        return "{Lampione: " + id + " " + controller + "}";
    }
}