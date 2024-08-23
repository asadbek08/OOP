package it.polito.ski;

import java.util.ArrayList;
import java.util.List;

public class LiftTypes {
    
    private String code;
    private String category;
    private int capacity;
    private List<Lift> lifts=  new ArrayList<>() ;

    public LiftTypes(String code, String category, int capacity){
        this.code = code;
        this.category =category;
        this.capacity = capacity;
    }

    public String liftCode(){
        return this.code;
    }

    public String getCategory(){
        return this.category;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void addLift(Lift lift){
        lifts.add(lift);
    }

    public List<Lift> getLifts(){
        return this.lifts;
    }
}
