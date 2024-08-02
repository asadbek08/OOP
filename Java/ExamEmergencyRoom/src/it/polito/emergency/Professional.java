package it.polito.emergency;

public class Professional {
    private String id;
    private String name;
    private String surname;
    private String specialization;
    private String period;
    private String workingHours;

    public Professional(String id, String name, String surname, String specialization, String period){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.period = period;
    }
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getWorkingHours() {
        return this.workingHours;
    }
}
