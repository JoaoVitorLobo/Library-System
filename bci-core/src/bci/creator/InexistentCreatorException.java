package bci.creator;

public class InexistentCreatorException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private String _name;

    public InexistentCreatorException(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }
}
