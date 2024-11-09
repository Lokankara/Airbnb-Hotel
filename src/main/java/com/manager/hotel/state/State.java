package com.manager.hotel.state;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class State<T, V> {
    private T value;
    private V error;
    private StatusNotification status;

    public State(StatusNotification status, T value, V error) {
        this.value = value;
        this.error = error;
        this.status = status;
    }

    public static <T, V> StateBuilder<T, V> builder() {
        return new StateBuilder<>();
    }
}
