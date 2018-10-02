package hardware;

import java.util.Objects;

public class Car implements Runnable, Comparable<Car> {
    public final static double decel = 9.8;
    static final int HEADLIGHTS_LENGTH = 15;
    private static int count_id = 0;
    private int id;

    private int speed;
    private int position;

    private Street street;
    private boolean running;

    private int space;

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

    public int getId() {
        return id;
    }

    private void setSpeed(int speed) {
        this.speed = speed;
    }

    private void setSpace() {
        space = (int) (speed/3.6);
    }

    private void setPosition(int position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPosition() {
        return position;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    private Street getStreet() {
        return street;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getRunning() {
        return running;
    }

    public boolean isOnStreet() {
        return position > -1;
    }

    @Override
    public void run() {
        position += space;
        if (position >= getStreet().getStreetLength()) {
            setRunning(false);
        } else if (position > 0 && position < getStreet().getStreetLength()) {
            move();
        }
    }

    private void move() {
        Street.ACTUAL_STREET[position] = id;
        int d = position+1;
        while (d < position + HEADLIGHTS_LENGTH && d < getStreet().getStreetLength()) {
            Street.ACTUAL_STREET[d] = -2;
            d++;
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
