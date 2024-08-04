package it.polito.emergency;

import it.polito.emergency.EmergencyApp.*;

public class Patient {
    private String fiscalCode;
    private  String name;
    private String surname;
    private String dateOfBirth;
    private String reason; 
    private String DateTime;
    private String depName;
    private String special;
    private PatientStatus stat;

    public Patient(String fiscalCode, String name, String surname, String dateOfBirth, String reason, String dateTimeAccepted){
        this.fiscalCode = fiscalCode;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.reason = reason;
        this.DateTime = dateTimeAccepted;

    }

    public String getFiscalCode() {
        return this.fiscalCode;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getReason() {
        return this.reason;
    }

    public String getDateTimeAccepted() {
        return this.DateTime;
    }

    public PatientStatus getStatus() {
        return this.stat;
    }
    public void setStatus(PatientStatus stat){
        this.stat = stat;
    }

    public void setDepName(String depname){
        this.depName = depname;
    }
    public String getDepName(){
        return  this.depName;
    }

    public void setSpec(String specname){
        this.special = specname;
    }
    public String getSpec(){
        return  this.special;
    }
}
