package hardware;

public class Car implements Runnable, Comparable<Car> {
    public static final int HEADLIGHTS_LENGTH = 15;
    private static int count_id = 0;
    private int id;

    private int speed;
    private int position;

    private Street street;
    private boolean running;

    private double time;
    private int space;

    public Car(int speed, int position) {
        id = count_id;
        count_id++;

        time = 0;
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
        int prevPos = position;
        position += space;
        // prelevo la street e alla fine la aggiorno
        if (position >= getStreet().getStreetLength()) {
            //clean(prevPos, getStreet().getStreetLength());
            setRunning(false);
        } else if (position > 0 && position < getStreet().getStreetLength()){
            //System.out.println("Spazio percorso da " + getId() + ": " + position + "m");    // Log

            //clean(prevPos, position);
            move();
        }
            //while (running && position < getStreet().getStreetLength()) {
            //System.out.println(getStreet());

            /*// imposto l'auto sulla strada
            getStreet().setInStreet(position, getId());

            // imposto l'intensità luminosa dei fanali sulla strada
            int d = getPosition()+1;
            while (d < getStreet().getStreetLength() && d < position+11) {
                if (getStreet().getFromStreet(d) == -1) {
                    getStreet().setInStreet(d, -2);
                }
                d++;
            }*/



            /*if ((position-1) < getStreet().getStreetLength() && getStreet().getFromStreet(position-1) == getId()) {
                for (int i = (int)prevPos; i < position; i++) {
                    if (getStreet().getFromStreet(i) == -1) {
                        getStreet().setInStreet(i, -1);
                    }
                }
            }*/

            /*for (int i = this.getPosition()+1; (position+11 < getStreet().getArrayStreet().length) && i < position+11; i++) {
                if (getStreet().getFromStreet(i) == -1) {
                    getStreet().setInStreet(i, -2); // -2 = luce dei fanali
                }
            }*/
            /*for (Sensor s : getStreet().getSensors()) {
                s.detect();
            }*/

            /**
             * Spazio percorso in un secondo
             */
            /*double metri = speed/3.6;
            double prevPos = position;
            position += metri;
            System.out.println("Spazio percorso da " + getId() + ": " + position + "m");    // Log
            *//*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*//*
            if ((position-1) < getStreet().getStreetLength() && getStreet().getFromStreet(position-1) == getId()) {
                for (int i = (int)prevPos; i < position; i++) {
                    if (getStreet().getFromStreet(i) == -1) {
                        getStreet().setInStreet(i, -1);
                    }
                }
            }*/
        //}
    }

    private void move() {
        Street.ACTUAL_STREET[position] = id;
        int d = position+1;
        while (d < position + HEADLIGHTS_LENGTH && d < getStreet().getStreetLength()) {
            //getStreet().setInStreet(d, -2);
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
