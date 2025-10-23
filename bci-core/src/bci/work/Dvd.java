package bci.work;

import bci.creator.Creator;
import java.util.ArrayList;
import java.util.List;

public class Dvd extends Work {
    private  Creator _director;
    private  String _igac;

    public Dvd(int id, String title, String category, String price, String qty, String igac) {
        super(id, title, category, price, qty);
        _igac = igac;
    }
    
    public void setDirector(Creator director) { _director = director; }
    public String getIgac() { return _igac; }

    @Override
    public List<Creator> getCreators() { 
        List<Creator> director = new ArrayList<>();
        director.add(_director);
        return director; 
    }

    @Override
    public void setCreator(Creator creator) { _director = creator; } 

    @Override
    public String creatorToString(){
        return _director.getName();
    }
    
    @Override
    public String toString(){
        return getId() + " - " + stillAvailable() + " de " + getQty() + " - DVD - " + getTitle() + " - " + 
                    getPrice() + " - " + getCategory().getName() + " - " + creatorToString() + " - " + getIgac();
    }
}
