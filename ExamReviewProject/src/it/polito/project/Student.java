package it.polito.project;

public class Student {
    /**
	 * Records a preference of a student for a specific slot/option of a review.
	 * Preferences can be recorded only for review for which poll has been opened.
	 * 
	 * @param email		email of participant
	 * @param name		name of the participant
	 * @param surname	surname of the participant
	 * @param reviewId	id of the review
	 * @param date		date of the selected slot
	 * @param slot		time range of the slot
	 * @return the number of preferences for the slot
	 * @throws ReviewException	in case of invalid id or slot
	 */

     private String email;
     private String surname;
     private String name;
     private String reviewId;
     private String date;
     private String slot;

     public Student(String email, String name, String surname, String reviewId,  String date, String slot){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.reviewId = reviewId;
        this.date = date;
        this.slot = slot;
     }

     public String toString(){
        return this.date+"T" +this.slot+"=" + this.email;
     }

     public String getDateSlot(){
        return this.date+"T"+this.slot;
     }

     public String getSlot(){
        return this.slot;
     }

}
