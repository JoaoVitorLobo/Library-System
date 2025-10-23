package bci.user;

import java.io.Serializable;

public abstract class Situation implements Serializable{
    public abstract boolean isActive();
}
