package bci.work;

import bci.creator.Creator;
import java.util.ArrayList;
import java.util.List;

public class Book extends Work{
    private final List<Creator> _authors = new ArrayList<>();
    private final String _isbn;

    public Book(int id, String title, String category, String price, String qty, String isbn) {
        super(id, title, category, price, qty);
        _isbn = isbn;
    }  

    public String getIsbn() { return _isbn; } 

    @Override
    public void setCreator(Creator creator) { _authors.add(creator); }

    @Override
    public List<Creator> getCreators() { return _authors; }

    @Override
    public String creatorToString(){
        StringBuilder out = new StringBuilder();
        for(Creator c : _authors){
            out.append(c.getName()).append("; ");
        }
        out.setLength(out.length() - 2); // Remove the last "; "
        return out.toString();
    }

    @Override
    public String toString(){
        return getId() + " - " + stillAvailable() + " de " + getQty() + " - Livro - " + getTitle() + " - " + 
                    getPrice() + " - " + getCategory().getName() + " - " + creatorToString() + " - " + getIsbn();

    }
}
