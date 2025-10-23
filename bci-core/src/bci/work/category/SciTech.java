package bci.work.category;

import bci.work.Category;

public class SciTech extends Category {
    @Override
    public String getName() {
        return "Técnica e Científica";
    }

    @Override
    public boolean canBeBorrowed() {
        return true;
    }
}