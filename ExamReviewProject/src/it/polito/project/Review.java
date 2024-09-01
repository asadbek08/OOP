package it.polito.project;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

public class Review {
    /**
	 * adds a new review with a given group
	 * 
	 * @param title		title of review
	 * @param topic	subject of review
	 * @param group  group of the review
	 * @return a unique id of the review
	 * @throws ReviewException in case of non-existing group
	 */

     private String title;
     private String topic;
     private String group;
     private String id;

     private Map<String, List<Option>> slots = new HashMap<>();
     private Map<String, List<String>> newSlotMap = new HashMap<>();
     private Map<String, List<Student>> prefs = new HashMap<>();
     private Map<String, Integer> occurances = new HashMap<>();
     private int countPref;

     private boolean openPoll=false;

     public Review(String title, String topic, String group, String id){
        this.title = title;
        this.topic = topic;
        this.group = group;
        this.id = id;
     }

     public String getTitle(){
        return this.title;
     }

     public String getTopic(){
        return this.topic;
     }

     public String getGroup(){
        return this.group;
     }

     public String getId(){
        return this.id;
     }

     public double addSlot(String date, String start, String end) throws ReviewException{
        Option newSlot = new Option(this.id, date, start, end);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start1 = LocalTime.parse(start, format);
        LocalTime end1 = LocalTime.parse(end, format);
        for(Map.Entry<String, List<Option>> option : slots.entrySet()){
            if(date.equals(option.getKey())){
               for(Option slot : option.getValue()){
                  LocalTime start2 = LocalTime.parse(slot.getStart());
                  LocalTime end2 = LocalTime.parse(slot.getEnd());
                  if(start1.isBefore(end2) && start2.isBefore(end1)){
                     throw new ReviewException();
                  }
               }
            }
         }
         if(slots.containsKey(date)){
            slots.get(date).add(newSlot);
         }else{
            List<Option> options = new ArrayList<>();
            options.add(newSlot);
            slots.put(newSlot.getDate(), options);
         }
         
         Duration duration = Duration.between(start1, end1);
         double hours = duration.toMinutes()/60.0;
         return hours;        
     }

     public Map<String, List<String>> getSlotList(){
         
         for(String date: slots.keySet()){
            List<String> ListSlots = new ArrayList<>();
            if(slots.get(date)!=null){
               
               for(Option option: slots.get(date)){
                  if(option!=null){
                     ListSlots.add(option.toString());
                  }  
               } 
            }
            newSlotMap.put(date, ListSlots);
         }
         return newSlotMap;
     }

     public int selectPreference(String email, String name, String surname, String date, String slot) throws ReviewException{
         Student stud = new Student(email, name, surname, surname, date, slot);
         if(newSlotMap==null || newSlotMap.get(date)==null || !newSlotMap.get(date).contains(slot)){
            throw new ReviewException();
         }
         if(prefs.containsKey(date)){
               prefs.get(date).add(stud);
               countPref++;
               return prefs.get(date).size();
         }else{
            List<Student> studList = new ArrayList<>();
            studList.add(stud);
            prefs.put(date, studList);
            countPref++;
            return studList.size();
         }
         
     }

     public Map<String, List<String>> getPrefs(){
         return prefs.entrySet().stream()
            .collect(Collectors.toMap(
               Map.Entry :: getKey,
               entry -> entry.getValue().stream()
                  .map(Student::toString)
                  .collect(Collectors.toList())
            ));
     }

     public Map<String, Integer> getOccurs(){

         occurances = prefs.values().stream().flatMap(List::stream).collect(Collectors.groupingBy(
            Student::getDateSlot,
            Collectors.collectingAndThen(
               Collectors.counting(),
               Long::intValue
            )
         ));
         return occurances;
     }

     public Map<String, List<String>> reviewPref(){
         return prefs.entrySet().stream()
            .collect(Collectors.toMap(
               Map.Entry::getKey,
               entry -> entry.getValue().stream()
                     
                     .map(x->x.getSlot()+"="+occurances.get(x.getDateSlot()))
                     .distinct()
                     .collect(Collectors.toList())
               ));
     }

     public void openPoll(){
         this.openPoll = true;
     }

     public void closePoll(){
         this.openPoll = false;
     }

     public boolean isOpen(){
         return this.openPoll;
     }

     public int getCount(){
         return this.countPref;
     }
}
