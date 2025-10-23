package bci.request.requesteExceptions;

public class NoRequestFoundException extends Exception {
    
    private static final long serialVersionUID = 202507171003L;

    private int _workId;
    private int _userId;

    public NoRequestFoundException(int userId, int workId) {
        _userId = userId;
        _workId = workId;
    }

    public int getWorkId() { return _workId; }
    public int getUserId() { return _userId; }
}
