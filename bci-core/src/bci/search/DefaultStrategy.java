package bci.search;

import bci.work.Work;
import java.util.List;
import java.util.ArrayList;

public class DefaultStrategy extends SearchStrategy {
    
    private final List<Work> _works;
    
    public DefaultStrategy(List<Work> works, String term) { 
        super(term);
        _works = works;
     }

    @Override
    public List<Work> searchWork() {
        List<Work> results = new ArrayList<>();
        for(Work work : _works) {
            if(work.getTitle().toLowerCase().contains(getTerm().toLowerCase()) || 
                work.creatorToString().toLowerCase().contains(getTerm().toLowerCase())) {
                results.add(work);
            }
        }   
        return results; 
    }
    
}
