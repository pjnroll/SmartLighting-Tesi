package test;

import oldexceptions.IntensityOutOfBoundException;
import oldhardware.Street;
import oldhardware.StreetLamp;

import java.util.Arrays;
import java.util.Random;

public class Test implements Runnable {
    private final static int N_STREETLAMPS = 20;
    private final static int INTENSITY_BASE = 20;

    private Street mStreet;
    private StreetLamp[] streetLamps;

    /*private int[] firstOutputs;*/
    private int[] intensities;  // Output array with the intensities to assign at each lamppost

    private int[] input;        // Array of sensors' values always up to date. They probably represent the cars' velocity
    private int[] dumped;       // Last stored situation of the sensors' values. It's used to be compared with input array,
                                // to figure out if there is a new detection

    private int inputValue;

    private int time;           // The time that the car (should) spend to reach the next sensor

    private Thread prOS;

    private boolean isDetected; // When a sensor detect a new car, it becomes true;
    private boolean isHandled;  // When the system successfully change the intensity of a lamppost, whose sensor detects
                                // a car, it becomes true and the control comes back to the main system

    private int sensorDetected; // The sensor id that detects a car

    public Test() {
        input = new int[N_STREETLAMPS];
        dumped = new int[N_STREETLAMPS];

        // Creo la strada
        mStreet = new Street("Via Orabona");
        streetLamps = new StreetLamp[N_STREETLAMPS];
        intensities = new int[N_STREETLAMPS];

        // Creo i lampioni
        for (int i = 0; i < N_STREETLAMPS; i++) {
            try {
                streetLamps[i] = new StreetLamp(INTENSITY_BASE);
            } catch (IntensityOutOfBoundException e) {
                e.printStackTrace();
            }
        }

        // Aggiungo i lampioni alla strada
        for (StreetLamp streetLamp : streetLamps) {
            mStreet.addStreetLamp(streetLamp);
        }

        Arrays.fill(intensities, 20);

        System.out.println(mStreet.toString());

        prOS = new Thread(this, "prOS Thread");
        prOS.start();

        new Sensor();   // Start every sensor


    }

    public int[] generateIntensities(int position, int[] output) {
        int[] toRet = new int[output.length];

        Arrays.fill(toRet, 20);

        try {
            toRet[position] = 100;
            toRet[position + 1] = 100;
            toRet[position + 2] = 80;
            toRet[position + 3] = 60;
            toRet[position + 4] = 40;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Index out of bound");
        }

        return toRet;
    }

    @Override
    public void run() {
        while (true) {
            update();
        }
    }

    public void update() {
        // Se c'è un aggiornamento;
        // ci sarà un aggiornamento quando lo stato attuale dei sensori
        // è diverso da quello precedente
        if (!Arrays.equals(input, dumped)) {
            dumped = Arrays.copyOf(input, input.length);
            intensities = generateIntensities(sensorDetected, dumped);

            // Assegno le nuove intensità ai lampioni
            int k = 0;
            for (StreetLamp streetLamp : mStreet.getStreetLamps()) {
                streetLamp.setIntensity(intensities[k]);
                k++;
            }
            isHandled = true;
            System.out.println(mStreet);
        }
    }

    public static void main(String... args) {
        //TODO Remember to handle the oldexceptions
        new Test();
    }

    public class Sensor implements Runnable {
        private final static int N_STREETLAMPS = 20;

        private Thread myThread;

        Sensor() {
            isDetected = false;
            isHandled = false;

            time = 0;


            //  No sensors detected
            Arrays.fill(input, 0);
            Arrays.fill(dumped, 0);

            myThread = new Thread(this, "SensorThread");
            myThread.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random ran = new Random();

            sensorDetected = 0;
            inputValue = ran.nextInt(81) + 50;

            //  Calcola il tempo che l'auto impiega ad arrivare al sensore successivo
            time = ((35 * 36) / (inputValue * 10)) * 100;

            while (sensorDetected <= 15) {
                input[sensorDetected] = inputValue;
                isDetected = true;

                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                input[sensorDetected] = 0;
                sensorDetected += 1;
            }
        }

        @Override
        public void run() {
            while (true) {
                while (isDetected && !isHandled) {
                    prOS.run();
                }
                break;
            }
        }

    }
}