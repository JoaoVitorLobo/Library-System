package bci.user;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class HistoryManager implements Serializable{
    private List<Boolean> _history = new LinkedList<>();
    private final int MAX_SIZE = 5;

    public void addToHistory(Boolean value) {
        _history.addFirst(value);
        if (_history.size() > MAX_SIZE) {
            _history.removeLast(); 
        }
    }

    public List<Boolean> getHistory(){
        return _history;
    }
}
 