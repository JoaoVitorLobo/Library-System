package bci.user.situation;

import bci.user.Situation;

public class Suspended extends Situation{

    @Override
    public boolean isActive(){ return false;}

    @Override
    public String toString() { return "SUSPENSO"; }

}
