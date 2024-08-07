package it.polito.med;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MedManager {

	private Map<String, Set<String>>  specs = new HashMap<>();
	private Map<String, Doctor> docs = new HashMap<>();
	private Map<String, Appointment> apps = new HashMap<>();
	private int appCounter;
	private String currentDate;
	
	/**
	 * add a set of medical specialities to the list of specialities
	 * offered by the med centre.
	 * Method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param specialities the specialities
	 */
	public void addSpecialities(String... specialities) {
		for(String spec: specialities){
			specs.put(spec, new HashSet());
		}
	}

	/**
	 * retrieves the list of specialities offered in the med centre
	 * 
	 * @return list of specialities
	 */
	public Collection<String> getSpecialities() {
		return specs.keySet();
	}
	
	
	/**
	 * adds a new doctor with the list of their specialities
	 * 
	 * @param id		unique id of doctor
	 * @param name		name of doctor
	 * @param surname	surname of doctor
	 * @param speciality speciality of the doctor
	 * @throws MedException in case of duplicate id or non-existing speciality
	 */
	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {
		if(!specs.containsKey(speciality)|| docs.containsKey(id)){
			throw new MedException();
		}
		if(specs.containsKey(speciality)){
			specs.get(speciality).add(id);
		}

		Doctor doc = new Doctor(id, name, surname, speciality);
		
		docs.put(id, doc);
	}
	
	public Map<String, Doctor> getDocs(){
		return docs;
	}
	/**
	 * retrieves the list of doctors with the given speciality
	 * 
	 * @param speciality required speciality
	 * @return the list of doctor ids
	 */
	public Collection<String> getSpecialists(String speciality) {
		return specs.get(speciality);
	}

	/**
	 * retrieves the name of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the name
	 */
	public String getDocName(String code) {
		return docs.get(code).getName();
	}

	/**
	 * retrieves the surname of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the surname
	 */
	public String getDocSurname(String code) {
		return docs.get(code).getSurname();
	}

	/**
	 * Define a schedule for a doctor on a given day.
	 * Slots are created between start and end hours with a 
	 * duration expressed in minutes.
	 * 
	 * @param code	doctor id code
	 * @param date	date of schedule
	 * @param start	start time
	 * @param end	end time
	 * @param duration duration in minutes
	 * @return the number of slots defined
	 */
	public int addDailySchedule(String code, String date, String start, String end, int duration) {
		
		int counter = 0;
		int hourmins = Integer.parseInt(start.split(":")[0])*60 + Integer.parseInt(start.split(":")[1]);
		int endhourmins = Integer.parseInt(end.split(":")[0])*60 + Integer.parseInt(end.split(":")[1]);
		Doctor doc = docs.get(code);
		String tempstart = start;
		while(hourmins<endhourmins){
			String nexthour = String.format("%02d", (hourmins + duration)/60)+":"+String.format("%02d",(hourmins+duration)%60);
			doc.addSlot(date, tempstart, nexthour);
			hourmins = hourmins + duration;
			counter++;
			tempstart = nexthour;
		}
		return counter;
	}

	/**
	 * retrieves the available slots available on a given date for a speciality.
	 * The returned map contains an entry for each doctor that has slots scheduled on the date.
	 * The map contains a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-14:30" describes a slot starting at 14:00 and lasting 30 minutes.
	 * 
	 * @param date			date to look for
	 * @param speciality	required speciality
	 * @return a map doc-id -> list of slots in the schedule
	 */
	public Map<String, List<String>> findSlots(String date, String speciality) {
		Set<String> docids = specs.get(speciality);
		Map<String, List<String>> returnMap = new HashMap<>();

		for(String id: docids){
			Doctor doc = docs.get(id);
			
			List<String> dateslot = doc.getdailyslots(date);
			
			if (dateslot == null) {
				System.out.println("No slots found for doctor id: " + id + " on date: " + date);
				continue;
			}
	
			if (!dateslot.isEmpty()) {
				returnMap.put(id, dateslot);
			}
		}

		return returnMap;
	}

	/**
	 * Define an appointment for a patient in an existing slot of a doctor's schedule
	 * 
	 * @param ssn		ssn of the patient
	 * @param name		name of the patient
	 * @param surname	surname of the patient
	 * @param code		code id of the doctor
	 * @param date		date of the appointment
	 * @param slot		slot to be booked
	 * @return a unique id for the appointment
	 * @throws MedException	in case of invalid code, date or slot
	 */
	public String setAppointment(String ssn, String name, String surname, String code, String date, String slot) throws MedException {
		Doctor doc = docs.get(code);

		if(doc==null){
			throw new MedException();
		}
		String AppId = "APP"+(++appCounter);
		Appointment app = new Appointment(AppId, ssn, name, surname, code, date, slot);
		apps.put(app.appID(), app);

		return app.appID();
	}

	/**
	 * retrieves the doctor for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor code id
	 */
	public String getAppointmentDoctor(String idAppointment) {
		return apps.get(idAppointment).getDocId();
	}

	/**
	 * retrieves the patient for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor patient ssn
	 */
	public String getAppointmentPatient(String idAppointment) {
		return apps.get(idAppointment).getPatientId();
	}

	/**
	 * retrieves the time for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return time of appointment
	 */
	public String getAppointmentTime(String idAppointment) {
		return apps.get(idAppointment).getTime();

	}

	/**
	 * retrieves the date for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return date
	 */
	public String getAppointmentDate(String idAppointment) {
		return apps.get(idAppointment).getDate();

	}

	/**
	 * retrieves the list of a doctor appointments for a given day.
	 * Appointments are reported as string with the format
	 * "hh:mm=SSN"
	 * 
	 * @param code doctor id
	 * @param date date required
	 * @return list of appointments
	 */
	public Collection<String> listAppointments(String code, String date) {
		return apps.values().stream()
			.filter(x-> x.getDocId().equals(code))
			.filter(x-> x.getDate().equals(date))
			.map(Appointment::toString)
			.collect(Collectors.toSet());
		
	}

	/**
	 * Define the current date for the medical centre
	 * The date will be used to accept patients arriving at the centre.
	 * 
	 * @param date	current date
	 * @return the number of total appointments for the day
	 */
	public int setCurrentDate(String date) {
		currentDate = date;
		return apps.values().stream()
			.filter(x-> x.getDate().equals(date))
			.collect(Collectors.toSet()).size();
	}

	/**
	 * mark the patient as accepted by the med centre reception
	 * 
	 * @param ssn SSN of the patient
	 */
	public void accept(String ssn) {
		Appointment filtered= apps.values().stream()
			.filter(x -> x.getPatientId().equals(ssn))
			.collect(Collectors.toList()).get(0);
		filtered.setStatAccepted();
	}

	/**
	 * returns the next appointment of a patient that has been accepted.
	 * Returns the id of the earliest appointment whose patient has been
	 * accepted and the appointment not completed yet.
	 * Returns null if no such appointment is available.
	 * 
	 * @param code	code id of the doctor
	 * @return appointment id
	 */
	public String nextAppointment(String code) {
		Set<Appointment> app=  apps.values().stream()
			.filter(x -> x.getDocId().equals(code))
			.filter(Appointment::getStat)
			.filter(x -> !x.getStatComp())
			.sorted(Comparator.comparing(Appointment::toString))
			.collect(Collectors.toSet());
		if(app==null|| app.isEmpty()){
			return null;
		}
		return app.stream()
			.map(Appointment::appID)
			.collect(Collectors.toList()).get(0);
	}

	/**
	 * mark an appointment as complete.
	 * The appointment must be with the doctor with the given code
	 * the patient must have been accepted
	 * 
	 * @param code		doctor code id
	 * @param appId		appointment id
	 * @throws MedException in case code or appointment code not valid,
	 * 						or appointment with another doctor
	 * 						or patient not accepted
	 * 						or appointment not for the current day
	 */
	public void completeAppointment(String code, String appId)  throws MedException {
		Doctor doc = docs.get(code);
		if(doc==null){
			throw new MedException();
		}
		Appointment app = apps.get(appId);
		if(app==null){
			throw new MedException();
		}

		if(!app.getDocId().equals(code)){
			throw new MedException();
		}

		if(!app.getStat()||!app.getDate().equals(currentDate)){
			throw new MedException();
		}

		app.setStatCompleted();
	}

	/**
	 * computes the show rate for the appointments of a doctor on a given date.
	 * The rate is the ratio of accepted patients over the number of appointments
	 *  
	 * @param code		doctor id
	 * @param date		reference date
	 * @return	no show rate
	 */
	public double showRate(String code, String date) {
		return (double)apps.values().stream()
			.filter(x -> x.getDate().equals(date))
			.filter(x -> x.getDocId().equals(code))
			.filter(Appointment::getStat)
			.collect(Collectors.toList()).size()/
			apps.values().stream()
			.filter(x -> x.getDate().equals(date))
			.filter(x -> x.getDocId().equals(code))
			.collect(Collectors.toList()).size();
	}

	/**
	 * computes the schedule completeness for all doctors of the med centre.
	 * The completeness for a doctor is the ratio of the number of appointments
	 * over the number of slots in the schedule.
	 * The result is a map that associates to each doctor id the relative completeness
	 * 
	 * @return the map id : completeness
	 */
	public Map<String, Double> scheduleCompleteness() {
		Map<String, Double> completeness = new HashMap<>();

		for (Doctor doc : docs.values()){
			int numSlots = doc.numberOfSlots();
			int numberOfAppointments = apps.values().stream()
						.filter(x->x.getDocId().equals(doc.getId()))
						.collect(Collectors.toList()).size();
			double complete = (double) numberOfAppointments/numSlots;
			completeness.put(doc.getId(), complete);
		}
		return completeness;
	}


	
}
