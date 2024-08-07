package it.polito.library;

public class Rental {
    private String readerID;
    private String bookID;
    private String startingDate;
    private String endingDate;
    private boolean ongoing;

    public Rental(String readerID, String bookID, String startingDate){
        this.bookID = bookID;
        this.readerID = readerID;
        this.startingDate = startingDate;
    }

    public void setEndingDate(String endingDate){
        this.endingDate = endingDate;
    }

    public String getBookID(){
        return this.bookID;
    }

    public String getReaderID(){
        return this.readerID;
    }

    public String toString(){
        if(this.endingDate==null){
            
            return this.startingDate + " ONGOING";
        }else{
            
            return this.startingDate +" "+this.endingDate;
        }
    }

    public void setOngoing(){
        if(this.endingDate==null){
            this.ongoing = true;
        }else{
            this.ongoing = false;
        }
    }
    public boolean getStatus(){
        setOngoing();
        return this.ongoing;
    }
}
