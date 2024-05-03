package com.ra;

import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.entity.OrderStatus;

public class Common {

    public static Long getLong(String value, String message) throws IdFormatException {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException exception) {
            throw new IdFormatException(message);
        }
    }

    public static int getInt(String value, String message) throws IdFormatException {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException exception) {
            throw new IdFormatException(message);
        }
    }

    public static OrderStatus getOrderStatus(String value, String message) throws DataNotFoundException {
        try {
            return OrderStatus.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new DataNotFoundException(message);
        }
    }
}
