package it.polito.tvseriesdb;

import java.util.Map;

public class Actor {
    /**
	 * adds an actor whose name and surname is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param name the name of the actor
	 * @param surname the surname of the actor
	 * @param nationality the nationality of the actor
	 * @return number of actors inserted so far
	 * @throws TSException if actor has already been inserted.
	 */

     private String name;
     private String surname;
     private String nationality;
     
     public Actor(String name, String surname, String nationality){
        this.name = name;
        this.surname = surname;
        this.nationality =nationality;
     }

     

     public String getFullName(){
        return name + " " + surname;
     }

}