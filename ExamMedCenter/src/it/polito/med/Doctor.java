package it.polito.med;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Doctor {
    private String id;
    private String name;
    private String surname;
    private String speciality;
    private Map<String,List<String>> dailyslots = new HashMap<>();

    public Doctor(String id, String name, String surname, String speciality){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.speciality = speciality;
    }

    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

    public List<String> getdailyslots(String date){
        return dailyslots.get(date);
    }

    public void addSlot(String date, String start, String end){
        
        
        if(!dailyslots.containsKey(date)){
            List<String> slots = new ArrayList<>();
            slots.add(start+"-"+end);
            this.dailyslots.put(date, slots);
        }else{
            dailyslots.get(date).add(start+"-"+end);
        }
        

        
    }
}
