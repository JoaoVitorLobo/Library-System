package bci.request.requesteExceptions;

public class RulesException extends Exception {
    
    private static final long serialVersionUID = 202507171003L;

    private final int _ruleId;
    
    public RulesException(int ruleId) {
        _ruleId = ruleId;
    }

    public int getRuleId() {
        return _ruleId;
    }
    
}
