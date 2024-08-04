package it.polito.emergency;

public class Department {
    private String name;
    private int maxPatients;

    public Department(String name, int maxPatients){
        this.name = name;
        this.maxPatients = maxPatients;
    }

    public String getName(){
        return this.name;
    }

    public int getMaxPatients(){
        return this.maxPatients;
    }

    public void setMaxPatients(int max){
        this.maxPatients = max;
    }
}
