package it.polito.emergency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EmergencyApp {

    Map<String, Professional> profs = new HashMap<>();
    Map<String, List<String>> specs = new HashMap<>();
    Map<String, List<String>> periods = new HashMap<>();
    Map<String, Department> deps = new HashMap<>();
    Map<String, Patient> patients = new HashMap<>();

    public enum PatientStatus {
        ADMITTED,
        DISCHARGED,
        HOSPITALIZED
    }
    
    /**
     * Add a professional working in the emergency room
     * 
     * @param id
     * @param name
     * @param surname
     * @param specialization
     * @param period
     * @param workingHours
     */
    public void addProfessional(String id, String name, String surname, String specialization, String period) {
        //TODO: to be implemented
        
        Professional prof = new Professional(id, name, surname, specialization, period);
        if(specs.containsKey(specialization)){

            specs.get(specialization).add(id);
        }else{
            List<String> ids = new ArrayList<String>();
            ids.add(id);
            specs.put(specialization, ids);
        }
        if(periods.containsKey(period)){

            periods.get(period).add(id);
        }else{
            List<String> ids = new ArrayList<String>();
            ids.add(id);
            periods.put(period, ids);
        }
        profs.put(id, prof);
    
    }

    /**
     * Retrieves a professional utilizing the ID.
     *
     * @param id The id of the professional.
     * @return A Professional.
     * @throws EmergencyException If no professional is found.
     */    
    public Professional getProfessionalById(String id) throws EmergencyException {
        //TODO: to be implemented
        if (!profs.containsKey(id)){
            throw new EmergencyException("No professional found with id: "+id);
        }
        return profs.get(id);

    }

    /**
     * Retrieves the list of professional IDs by their specialization.
     *
     * @param specialization The specialization to search for among the professionals.
     * @return A list of professional IDs who match the given specialization.
     * @throws EmergencyException If no professionals are found with the specified specialization.
     */    
    public List<String> getProfessionals(String specialization) throws EmergencyException {
        //TODO: to be implemented
        if(!specs.containsKey(specialization)){
            throw new EmergencyException();
        }
        
        return specs.get(specialization);
    }

    /**
     * Retrieves the list of professional IDs who are specialized and available during a given period.
     *
     * @param specialization The specialization to search for among the professionals.
     * @param period The period during which the professional should be available, formatted as "YYYY-MM-DD to YYYY-MM-DD".
     * @return A list of professional IDs who match the given specialization and are available during the period.
     * @throws EmergencyException If no professionals are found with the specified specialization and period.
     */    
    public List<String> getProfessionalsInService(String specialization, String period) throws EmergencyException {
        //TODO: to be implemented
        String[] dates = period.split("to");
        String startdate = dates[0].trim();
        String enddate = dates[1].trim();
        if(!specs.containsKey(specialization) ){
            throw new EmergencyException();
        }
        List<String> ids =  profs.values().stream()
            .filter(x -> x.getSpecialization().equals(specialization))
            .filter(x-> {
                String[] Pdates = x.getPeriod().split("to");
                String Pstartdate = Pdates[0].trim();
                String Penddate = Pdates[1].trim();
                return (Pstartdate.compareTo(startdate)<=0 && Penddate.compareTo(enddate)>=0);
            })
            .map(Professional::getId)
            .collect(Collectors.toList());
        if(ids.isEmpty()){
            throw new EmergencyException();
        }  
        return ids;
        
    }

    /**
     * Adds a new department to the emergency system if it does not already exist.
     *
     * @param name The name of the department.
     * @param maxPatients The maximum number of patients that the department can handle.
     * @throws EmergencyException If the department already exists.
     */
    public void addDepartment(String name, int maxPatients) throws EmergencyException {
        //TODO: to be implemented
        Department dep = new Department(name, maxPatients);
        if (deps.containsKey(dep)){
            throw new EmergencyException();
        }
        
        deps.put(name, dep);
    }

    /**
     * Retrieves a list of all department names in the emergency system.
     *
     * @return A list containing the names of all registered departments.
     * @throws EmergencyException If no departments are found.
     */
    public List<String> getDepartments() throws EmergencyException {
        //TODO: to be implemented
        if (deps.isEmpty()){
            throw new EmergencyException("Empty");
        }
        return deps.values().stream().map(Department::getName).collect(Collectors.toList());
    }

    /**
     * Reads professional data from a CSV file and stores it in the application.
     * Each line of the CSV should contain a professional's ID, name, surname, specialization, period of availability, and working hours.
     * The expected format of each line is: matricola, nome, cognome, specializzazione, period, orari_lavoro
     * 
     * @param reader The reader used to read the CSV file. Must not be null.
     * @return The number of professionals successfully read and stored from the file.
     * @throws IOException If there is an error reading from the file or if the reader is null.
     */
    public int readFromFileProfessionals(Reader reader) throws IOException {
        //TODO: to be implemented
        if(reader==null){
            throw new IOException();
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        int count = 0;
        while((line = bufferedReader.readLine())!=null){
            String[] parts = line.split(",");
            
            String matricola = parts[0];
            String nome = parts[1];
            String cognome = parts[2];
            String specializzazione = parts[3];
            String period = parts[4];

            Professional prof = new Professional(matricola, nome, cognome,  specializzazione, period);
            profs.put(matricola, prof);
            count ++;    
            
        }
        return count;
    }

    /**
     * Reads department data from a CSV file and stores it in the application.
     * Each line of the CSV should contain a department's name and the maximum number of patients it can accommodate.
     * The expected format of each line is: nome_reparto, num_max
     * 
     * @param reader The reader used to read the CSV file. Must not be null.
     * @return The number of departments successfully read and stored from the file.
     * @throws IOException If there is an error reading from the file or if the reader is null.
     */    
    public int readFromFileDepartments(Reader reader) throws IOException {
        //TODO: to be implemented
        if(reader==null){
            throw new IOException();
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        int count = 0;
        while((line = bufferedReader.readLine())!=null){
            String[] parts = line.split(",");
            
            String name = parts[0];
            int maxPatients = Integer.parseInt(parts[1]);
            
            Department dep = new Department(name, maxPatients);
            deps.put(name, dep);
            count ++;    
            
        }
        return count;
       
    }

    /**
     * Registers a new patient in the emergency system if they do not exist.
     * 
     * @param fiscalCode The fiscal code of the patient, used as a unique identifier.
     * @param name The first name of the patient.
     * @param surname The surname of the patient.
     * @param dateOfBirth The birth date of the patient.
     * @param reason The reason for the patient's visit.
     * @param dateTimeAccepted The date and time the patient was accepted into the emergency system.
     */
    public Patient addPatient(String fiscalCode, String name, String surname, String dateOfBirth, String reason, String dateTimeAccepted) {
        //TODO: to be implemented
        Patient pat = new Patient(fiscalCode, name, surname, dateOfBirth, reason, dateTimeAccepted);
        if(patients.containsKey(fiscalCode)){
            return pat;
        }
        patients.put(fiscalCode, pat);
        
        patients.get(fiscalCode).setStatus(PatientStatus.ADMITTED);
        return patients.get(fiscalCode);
    }

    /**
     * Retrieves a patient or patients based on a fiscal code or surname.
     *
     * @param identifier Either the fiscal code or the surname of the patient(s).
     * @return A single patient if a fiscal code is provided, or a list of patients if a surname is provided.
     *         Returns an empty collection if no match is found.
     */    
    public List<Patient> getPatient(String identifier) throws EmergencyException {
        //TODO: to be implemented
        
        return patients.values().stream()
            .filter(x -> x.getFiscalCode().equals(identifier)||x.getSurname().equals(identifier))
            .collect(Collectors.toList());
    }

    /**
     * Retrieves the fiscal codes of patients accepted on a specific date, 
     * sorted by acceptance time in descending order.
     *
     * @param date The date of acceptance to filter the patients by, expected in the format "yyyy-MM-dd".
     * @return A list of patient fiscal codes who were accepted on the given date, sorted from the most recent.
     *         Returns an empty list if no patients were accepted on that date.
     */
    public List<String> getPatientsByDate(String date) {
        //TODO: to be implemented
        return patients.values().stream()
            .filter(x -> x.getDateTimeAccepted().equals(date))
            .sorted(Comparator.comparing(Patient:: getDateTimeAccepted))
            .map(Patient::getName)
            .collect(Collectors.toList());
    }

    /**
     * Assigns a patient to a professional based on the required specialization and checks availability during the request period.
     *
     * @param fiscalCode The fiscal code of the patient.
     * @param specialization The required specialization of the professional.
     * @return The ID of the assigned professional.
     * @throws EmergencyException If the patient does not exist, if no professionals with the required specialization are found, or if none are available during the period of the request.
     */
    public String assignPatientToProfessional(String fiscalCode, String specialization) throws EmergencyException {
        //TODO: to be implemented
        
        if (!specs.containsKey(specialization)){
            throw new EmergencyException();
        }
        
        final String date= patients.values().stream()
            .filter(x->x.getFiscalCode().equals(fiscalCode))
            .map(Patient::getDateTimeAccepted)
            .findFirst()
            .orElseThrow(()->new EmergencyException());


        String proffID =  profs.values().stream()
            .filter(x -> x.getSpecialization().equals(specialization))
            .filter(x-> {
                String[] dates = x.getPeriod().split("to");
                String startdate = dates[0].trim();
                String enddate = dates[1].trim();
                return (startdate.compareTo(date)<=0 && enddate.compareTo(date)>=0);
            })
            .findFirst()
            .map(Professional::getId)
            .orElseThrow(()-> new EmergencyException());
        patients.get(fiscalCode).setSpec(specialization);
        return proffID;
    }

    public Report saveReport(String professionalId, String fiscalCode, String date, String description) throws EmergencyException {
        //TODO: to be implemented
        if(!profs.containsKey(professionalId)){
            throw new EmergencyException();
        }
        Report report = new Report(professionalId, fiscalCode, date, description);
        return report;
    }

    /**
     * Either discharges a patient or hospitalizes them depending on the availability of space in the requested department.
     * 
     * @param fiscalCode The fiscal code of the patient to be discharged or hospitalized.
     * @param departmentName The name of the department to which the patient might be admitted.
     * @throws EmergencyException If the patient does not exist or if the department does not exist.
     */
    public void dischargeOrHospitalize(String fiscalCode, String departmentName) throws EmergencyException {
        //TODO: to be implemented
        // int counter = 0;
        // for(Patient pat: patients){
        //     if(pat.getFiscalCode()==fiscalCode){
        //         counter++;
        //     }
        // }
        // if(counter==0){
        //     throw new EmergencyException();
        // }

        Patient patient = patients.get(fiscalCode);
        Department depo = deps.get(departmentName);

        if(patient==null && depo==null){
            throw new EmergencyException();
        }

        if(depo.getMaxPatients()>0){
            patient.setStatus(PatientStatus.HOSPITALIZED);
            patient.setDepName(departmentName);
            depo.setMaxPatients(getNumberOfPatients()-1);
        }else{
            patient.setStatus(PatientStatus.DISCHARGED);
        }
        
    }

    /**
     * Checks if a patient is currently hospitalized in any department.
     *
     * @param fiscalCode The fiscal code of the patient to verify.
     * @return 0 if the patient is currently hospitalized, -1 if not hospitalized or discharged.
     * @throws EmergencyException If no patient is found with the given fiscal code.
     */
    public int verifyPatient(String fiscalCode) throws EmergencyException{
        //TODO: to be implemented
        if(!patients.containsKey(fiscalCode)){
            throw new EmergencyException();
        }
        Patient p = patients.get(fiscalCode);
        if(p.getStatus()==PatientStatus.HOSPITALIZED){
            return 0;
        }else{
            return -1;
        }
    }

    /**
     * Returns the number of patients currently being managed in the emergency room.
     *
     * @return The total number of patients in the system.
     */    
    public int getNumberOfPatients() {

        //TODO: to be implemented

        List<Patient> pats = patients.values().stream()
            .filter(x->x.getStatus().equals(PatientStatus.ADMITTED))
            .collect(Collectors.toList());
        return pats.size();
    }

    /**
     * Returns the number of patients admitted on a specified date.
     *
     * @param dateString The date of interest provided as a String (format "yyyy-MM-dd").
     * @return The count of patients admitted on that date.
     */
    public int getNumberOfPatientsByDate(String date) {
        //TODO: to be implemented
        List<Patient> pats = patients.values().stream()
            .filter(x->x.getDateTimeAccepted().equals(date))
            .collect(Collectors.toList());
        return pats.size();
    }

    public int getNumberOfPatientsHospitalizedByDepartment(String departmentName) throws EmergencyException {
        //TODO: to be implemented

        List<Patient> pats = patients.values().stream()
            .filter(x->x.getDepName().equals(departmentName))
            .collect(Collectors.toList());
        if (pats.isEmpty()){
            throw new EmergencyException();
        }
        return pats.size();
    }

    /**
     * Returns the number of patients who have been discharged from the emergency system.
     *
     * @return The count of discharged patients.
     */
    public int getNumberOfPatientsDischarged() {
        //TODO: to be implemented
        List<Patient> pats = patients.values().stream()
            .filter(x->x.getStatus().equals(PatientStatus.DISCHARGED))
            .collect(Collectors.toList());
        return pats.size();
    }

    /**
     * Returns the number of discharged patients who were treated by professionals of a specific specialization.
     *
     * @param specialization The specialization of the professionals to filter by.
     * @return The count of discharged patients treated by professionals of the given specialization.
     */
    public int getNumberOfPatientsAssignedToProfessionalDischarged(String specialization) {
        //TODO: to be implemented
        int count = patients.values().stream()
            .filter(x -> x.getSpec().equals(specialization))
            .filter(x->x.getStatus().equals(PatientStatus.DISCHARGED))
            .collect(Collectors.toList()).size();
        return count;
    }
}
