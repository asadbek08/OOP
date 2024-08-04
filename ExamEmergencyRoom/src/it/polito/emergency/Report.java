package it.polito.emergency;

import it.polito.emergency.EmergencyApp.PatientStatus;

public class Report {
    private String id;
    private  String professionalId;
    private String fiscalCode;
    private String description; 
    private String date;
    private int count = 0;
    

    public Report(String professionalId, String fiscalCode, String date, String description){
        this.fiscalCode = fiscalCode;
        this.professionalId = professionalId;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        count ++;
        return count + "";
    }

    public String getProfessionalId() {
        return this.professionalId;
    }

    public String getFiscalCode() {
        return this.fiscalCode;
    }

    public String getDate() {
        return this.date;
    }


    public String getDescription() {
        return this.description;
    }
}
