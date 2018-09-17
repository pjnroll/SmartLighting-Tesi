package hardware;

public class Car implements Runnable {
    private static int count_id = 0;
    private int id;

    private int speed;
    private int position;

    private Street street;
    private boolean running;

    public Car(int speed, int position) {
        id = count_id;
        count_id++;

        running = false;
        street = null;
        setSpeed(speed);
        setPosition(position);
    }

    public int getId() {
        return id;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(int position) {
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

    public Street getStreet() {
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
        while (running && position < 140) {
            move();
            System.out.println(getStreet());
        }
    }

    public void move() {
        try {
            position++;
            Thread.sleep((long) (1/(speed/3.6)*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int fromKmhToMs(int speed) {
        return (int)((speed/3.6)*1000);
    }

    public String toString() {
        return "(Macchina: " + getId() + " " + speed + "km/h";
    }
}
