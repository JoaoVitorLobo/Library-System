package bci.user.userExceptions;
import bci.exceptions.LibraryKeyException;

public class InexistentUserIdException extends LibraryKeyException {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;


    public InexistentUserIdException(int key) {
        super(key);
	}
}
