package hardware;

public class Streetlight {
    private static int count_id = 0;

    private int id;
    private Controller controller;
    private Component[] components;

    public Streetlight(Controller controller, Component[] components) {
        this.controller = controller;
        this.components = components;

        id = count_id;
        count_id++;
    }

    public String toString() {
        return "{Lampione: " + id + " " + controller + " " + components + "}";
    }
}
