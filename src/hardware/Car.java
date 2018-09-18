package hardware;

public class Car implements Runnable {
    private static int count_id = 0;
    private int id;

    private int speed;
    private int position;

    private Street street;
    private boolean running;

    private double time;
    private double space;

    public Car(int speed, int position) {
        id = count_id;
        count_id++;

        time = 0;
        space = 0;

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
            for (Car c : getStreet().getCars()) {
                System.out.println("Macchina " + c.getId() + ": " + c.getSpeed() + "km/h");
            }
            System.out.println(getStreet());
            move();
        }
    }

    public void move() {
        try {
            /**
             * Tempo per percorrere un metro
             */
            position++;
            double quantum = 1/(speed/3.6)*1000;
            time += quantum;
            System.out.println("Tempo trascorso per " + getId() + ": " + time /1000 + "s");   // Log
            Thread.sleep((long) (quantum));

            /**
             * Spazio percorso in un secondo
             */
            /*double metri = speed/3.6;
            position += metri;
            System.out.println("Spazio percorso da " + getId() + ": " + position + "m");    // Log
            Thread.sleep(1000);*/
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
