package bci.work.workExceptions;

import bci.exceptions.LibraryKeyException;

public class InexistentWorkIdException extends LibraryKeyException {
    
        @java.io.Serial
        private static final long serialVersionUID = 202507171003L;

        public InexistentWorkIdException(int key) {
            super(key);
        }
}
