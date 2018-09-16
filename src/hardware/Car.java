package hardware;

import java.util.Random;

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
        while (running) {
            //move();
        }
    }

    public void move() {
        setPosition(position++);
    }

    public String toString() {
        return "(Macchina: " + getId() + " " + speed + "km/h";
    }
}
