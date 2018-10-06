package hardware;

public class Component {
    private boolean attached;
    Controller controller;

    int position;

    /**
     * Returns if the component is already attached to a Controller
     * @return if is already attached
     */
    public boolean getAttached() {
        return attached;
    }

    /**
     * Attaches a component to a Controller
     * @param attached either true or false
     */
    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    /**
     * Return the Controller to which the component is attached
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Set the controller to which the component is attached
     * @param controller to attach
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Returns the component's position
     * @return the component's position
     */
    int getPosition() {
        return position;
    }

    /**
     * Method to set the component's position
     * @param position of the component
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
