package it.polito.project;

public class Option {
    /**
	 * Add a new option slot for a review as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same review.
	 * 
	 * @param reviewId	id of the review
	 * @param date		date of slot
	 * @param start		start time
	 * @param end		end time
	 * @return the length in hours of the slot
	 * @throws ReviewException in case of slot overlap or wrong review id
	 */

     private String reviewID;
     private String date;
     private String start;
     private String end;

     public Option(String reviewID, String date, String start, String end){
        this.reviewID = reviewID;
        this.date = date;
        this.start = start;
        this.end = end;
     }


     public String getDate(){
        return this.date;
     }

     public String getStart(){
        return this.start;
     }

     public String getEnd(){
        return this.end;
     }

     public String toString(){
         return this.start + "-" + this.end;
     }
}
