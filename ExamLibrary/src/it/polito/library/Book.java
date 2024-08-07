package it.polito.library;

public class Book {
    private String id;
    private String title;
    private boolean statusRented;
    private boolean iseverrented;

    public Book(String title, String id){
        this.title = title;
        this.id = id;
    }

    public void SetRented(){
        this.statusRented = true;
    }

    public void SetNotRented(){
        this.statusRented = false;
    }

    public boolean getStatus(){
        return this.statusRented;
    }
    public String getTitle(){
        return this.title;
    }

    public String getID(){
        return this.id;
    }

    public void setEverRented(){
        this.iseverrented = true;
    }

    public boolean everRented(){
        return this.iseverrented;
    }
    
}
