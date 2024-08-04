package it.polito.med;

public class Appointment {
    // * @param ssn		ssn of the patient
    // * @param name		name of the patient
    // * @param surname	surname of the patient
    // * @param code		code id of the doctor
    // * @param date		date of the appointment
    // * @param slot		slot to be booked
    // * @return a unique id for the appointment
    // * @throws MedException	in case of invalid code, date or slot
    //The `setAppointment()` method allows the system to define an appointment for a patient in 
    //an existing slot of a doctor's schedule. It requires providing the patient's SSN, name, surname, 
    //the doctor's code ID, the appointment date, and the slot to be booked. The method returns a unique 
    //ID for the appointment. If any of the provided code, date, or slot values are invalid, a `MedException` is thrown.
    private String ID;
    private String ssn;
    private String name;
    private String surname;
    private String code;
    private String date;
    private String slot;
    private int counter = 0;
    private boolean Accepted=false;
    public Appointment(String ssn, String name, String surname, String code, String date, String slot){
        
        this.ssn = ssn;
        this.code = code;
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.slot = slot;
        this.counter++;
    }

    public String appID(){
        this.ID = this.counter + "";

        return this.ID;
    }

    public String getDocId(){
        return this.code;
    }

    public String getPatientId(){
        return this.ssn;
    }

    public String getTime(){
        return this.slot.split("-")[0];
    }

    public String getDate(){
        return this.date;
    }
    
    public String toString(){
        return this.slot+"="+this.ssn;
    }

    public boolean getStat(){
        return this.Accepted;
    }

    public void setStatAccepted(){
        this.Accepted = true;
    }

}
