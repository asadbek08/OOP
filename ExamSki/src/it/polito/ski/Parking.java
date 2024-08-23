package it.polito.ski;

public class Parking {
    private String name;
    private int slots;

    public Parking(String name, int slots){
        this.name = name;
        this.slots = slots;
    }

    public int getSlots(){
        return this.slots;
    }
}
