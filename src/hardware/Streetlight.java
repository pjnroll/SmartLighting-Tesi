package hardware;

public class Streetlight {
    Controller controller;
    Component[] components;

    public Streetlight(Controller controller, Component[] components) {
        this.controller = controller;
        this.components = components;
    }
}
