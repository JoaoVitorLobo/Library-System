package bci.user.situation;

import bci.user.Situation;

public class Active extends Situation{

    @Override
    public boolean isActive(){ return true;}


    @Override
    public String toString() { return "ACTIVO"; }    

}
