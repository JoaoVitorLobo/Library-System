package bci.exceptions;

import java.io.Serial;

public class LibraryKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final int _key;

    public LibraryKeyException(int key) {
        _key = key;
    }

    public int getKey() {
        return _key;
    }

}
