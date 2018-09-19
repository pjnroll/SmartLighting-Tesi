package hardware;

public class Component {
    private boolean attached;
    private Controller controller;

    private int position;

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

    int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
