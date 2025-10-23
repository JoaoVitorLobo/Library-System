package bci.user.userExceptions;
import bci.exceptions.LibraryKeyException;

public class UserIsAlreadyActiveException extends LibraryKeyException{

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;


    public UserIsAlreadyActiveException(int key) {
        super(key);
	}
}
