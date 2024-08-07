package it.polito.library;

public class Reader {
    private String id;
    private String name;
    private String surname;
    private boolean StatRenting;
    private int rentalCounts=0;

    public Reader(String id, String name, String surname){
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getID(){
        return this.id;
    }

    public String getName(){
        return this.name+" "+this.surname;
    }

    public void SetRenting(){
        this.StatRenting=true;
    }
    public void SetNotRenting(){
        this.StatRenting=false;
    }

    public boolean getReaderStatus(){
        return this.StatRenting;
    }
    public void incrementRentals(){
        this.rentalCounts++;
    }

    public int getNumRentals(){
        return rentalCounts;
    }
}
