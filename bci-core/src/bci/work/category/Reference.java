package bci.work.category;

import bci.work.Category;

public class Reference extends Category {
    @Override
    public String getName() {
        return "Referência";
    }

    @Override
    public boolean canBeBorrowed() {
        return false;
    }
}
