package exceptions;

import hardware.Controller;

public class ControllerAlreadyAttachedException extends Exception {
    public ControllerAlreadyAttachedException() {
        super("Controller già installato");
    }

    public ControllerAlreadyAttachedException(Controller c) {
        super("Controller " + c + " già installato");
    }
}