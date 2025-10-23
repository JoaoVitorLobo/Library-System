package bci.creator;

import bci.work.Work;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Creator implements Serializable {
    private String _name;
    private List<Work> _works = new ArrayList<>();

    public String getName() {
        return _name;
    }

    public Creator(String name) {
        _name = name;
    }

    public void addCreatorWorks(Work work) { _works.add(work); }

    public List<Work> getWorksByCreator() { return _works.stream().sorted().toList(); }

    public void removeCreatorWork(Work work) { _works.remove(work); }
}
