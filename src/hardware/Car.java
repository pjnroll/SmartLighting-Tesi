package hardware;

public class Car implements Runnable, Comparable<Car> {
    private static final int HEADLIGHTS_LENGTH = 15;
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

    public String toString() {
        return "(Macchina: " + getId() + " " + speed + "km/h";
    }

    @Override
    public int compareTo(Car o) {
        int toRet = this.position - o.position;
        return (toRet == 0) ? -1 : toRet;
    }
}
