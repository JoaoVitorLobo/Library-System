package bci.work.category;

import bci.work.Category;

public class Fiction extends Category {
    @Override
    public String getName() {
        return "Ficção";
    }

    @Override
    public boolean canBeBorrowed() {
        return true;
    }
}
