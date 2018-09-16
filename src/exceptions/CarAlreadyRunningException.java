package exceptions;

import hardware.Car;
import hardware.Street;

public class CarAlreadyRunningException extends Exception {
    public CarAlreadyRunningException() {
        super("Macchina già presente sulla strada");
    }

    public CarAlreadyRunningException(Car car) {
        super("Macchina " + car + " già presente sulla strada");
    }

    public CarAlreadyRunningException(Car car, Street street) {
        super("Macchina " + car + " già presente sulla strada " + street);
    }
}
