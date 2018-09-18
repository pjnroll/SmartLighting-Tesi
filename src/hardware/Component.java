package hardware;

public class Component {
    private boolean attached;
    private Controller controller;

    public boolean getAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
