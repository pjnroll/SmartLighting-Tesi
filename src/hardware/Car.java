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

    private void setSpeed(int speed) {
        this.speed = speed;
    }

    private void setPosition(int position) {
        this.position = position;
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
        while (running && position < getStreet().getStreetLength()) {
            System.out.println(getStreet());
            getStreet().setInStreet(position, getId());

            // imposto l'intensità luminosa dei fanali sulla strada
            for (int i = this.getPosition()+1; (position+11 < getStreet().getArrayStreet().length) && i < position+11; i++) {
                if (getStreet().getFromStreet(i) == -1) {
                    getStreet().setInStreet(i, -2); // -2 = luce dei fanali
                }
            }
            for (Sensor s : getStreet().getSensors()) {
                s.detect();
            }
            move();
        }
    }

    private void move() {
        try {
            /**
             * Tempo per percorrere un metro
             */
            position++;
            double quantum = 1/(speed/3.6)*1000;
            time += quantum;
            //System.out.println("Tempo trascorso per " + getId() + ": " + time /1000 + "s");   // Log
            Thread.sleep((long) (quantum));
            if (getStreet().getFromStreet(position-1) == getId()) {
                getStreet().setInStreet(position-1, -1);
            }

            /**
             * Spazio percorso in un secondo
             */
            /*double metri = speed/3.6;
            position += metri;
            System.out.println("Spazio percorso da " + getId() + ": " + position + "m");    // Log
            Thread.sleep(1000);
            if (getStreet().getFromStreet(position-1) == getId()) {
                for (int i = (int)(position-metri); i < position; i++) {
                    getStreet().setInStreet(i, -1);
                }
            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "(Macchina: " + getId() + " " + speed + "km/h";
    }
}
