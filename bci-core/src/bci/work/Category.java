package bci.work;

import java.io.Serializable;

public abstract class Category implements Serializable{
    public abstract String getName();
    public abstract boolean canBeBorrowed(); //Pq as de referencia não podem ser emprestadas

}
