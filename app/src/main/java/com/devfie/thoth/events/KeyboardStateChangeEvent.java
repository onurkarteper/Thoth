package com.devfie.thoth.events;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class KeyboardStateChangeEvent {

    Boolean isOpen;

    public KeyboardStateChangeEvent(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
