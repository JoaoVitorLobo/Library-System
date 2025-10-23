package bci.request.requesteExceptions;

public class ReturnDateExceededException extends Exception {
    
    private static final long serialVersionUID = 202507171003L;

    private int _userId;
    private int _workId;
    private int _fine;
    private int _totalFine;

    public ReturnDateExceededException(int userId, int workId, int fine, int totalFine) {
        _userId = userId;
        _workId = workId;
        _fine = fine;
        _totalFine = totalFine;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }
    
    public int getFine() {
        return _fine;
    }

    public int getTotalFine() {
        return _totalFine;
    }
}
