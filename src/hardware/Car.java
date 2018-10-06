package hardware;

import java.util.Objects;

public class Car implements Comparable<Car> {
    final static double decel = 9.8;
    static final int HEADLIGHTS_LENGTH = 15;
    private static int count_id = 0;
    private int id;

    private int speed;
    private int position;

    private Street street;
    private boolean running;

    private int space;

    /**
     * Car's constructor
     * @param speed initial speed of the car
     * @param position initial position of the car
     */
    public Car(int speed, int position) {
        id = count_id;
        count_id++;

        space = 0;

        running = false;
        street = null;
        setSpeed(speed);
        setPosition(position);
        setSpace();
    }

    /**
     * Return the car's id
     * @return car's id
     */
    public int getId() {
        return id;
    }

    /**
     * Method to set the car's speed
     * @param speed car's speed
     */
    private void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Method that specify the distance traveled by the car
     */
    private void setSpace() {
        space = (int) (speed/3.6);
    }

    /**
     * Method to set the car's position
     * @param position the car's position
     */
    private void setPosition(int position) {
        this.position = position;
    }

    /**
     * Return the car's speed
     * @return the car's speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the car's position
     * @return the car's position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Method that specify the street in which the car is traveling
     * @param street where the car is
     */
    public void setStreet(Street street) {
        this.street = street;
    }

    /**
     * Method that return the street in which the car is traveling
     * @return the street
     */
    private Street getStreet() {
        return street;
    }

    /**
     * Method that specify either if a car is running or not
     * @param running in the street
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Returns if the car is still on the street
     * @return the car's state
     */
    public boolean getRunning() {
        return running;
    }

    /**
     * This method lets the car move
     */
    public void run() {
        position += space;
        if (position >= getStreet().getStreetLength()) {
            setRunning(false);
        } else if (position > 0 && position < getStreet().getStreetLength()) {
            // move
            Street.ACTUAL_STREET[position] = id;
            int d = position+1;
            while (d < position + HEADLIGHTS_LENGTH && d < getStreet().getStreetLength()) {
                Street.ACTUAL_STREET[d] = -2;
                d++;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                speed == car.speed &&
                position == car.position &&
                Objects.equals(street, car.street);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, speed, position, street);
    }

    @Override
    public int compareTo(Car o) {
        int toRetPos = o.position - this.position;
        int toRetSpeed = o.speed - this.speed;
        return (toRetPos == 0) ? ((toRetSpeed == 0) ? -1 : toRetSpeed) : toRetPos;
    }

    public String toString() {
        return "(Macchina: " + getId() + " " + "pos " + position + "   " + speed + "km/h";
    }

}
