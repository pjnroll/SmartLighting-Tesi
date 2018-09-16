package hardware;

public class Car {
    private static int count_id = 0;
    private int id;

    private int speed;

    private Street street;
    private boolean running;

    public Car(int speed) {
        id = count_id;
        count_id++;

        running = false;
        street = null;
        setSpeed(speed);
    }

    public int getId() {
        return id;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public Street getStreet() {
        return street;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getRunning() {
        return running;
    }
}
