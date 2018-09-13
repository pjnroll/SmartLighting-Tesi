package exceptions;

import hardware.Component;

public class ComponentAlreadyAttachedException extends Exception {
    public ComponentAlreadyAttachedException() {
        super("Componente già installato");
    }

    public ComponentAlreadyAttachedException(Component c) {
        super("Componente " + c + " già installato");
    }
}
