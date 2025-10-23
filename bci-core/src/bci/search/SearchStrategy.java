package bci.search;

import bci.work.Work;
import java.util.List;

public abstract class SearchStrategy {
    private final String _term;

    public SearchStrategy(String term) { _term = term; }
    public String getTerm() { return _term; }
    public abstract List<Work> searchWork();
}
