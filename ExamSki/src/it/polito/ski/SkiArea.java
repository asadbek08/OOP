package it.polito.ski;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkiArea {

	/**
	 * Creates a new ski area
	 * @param name name of the new ski area
	 */
	private String name;
	private Map<String, LiftTypes> liftTypes;
	private Map<String, Lift> lifts;
	private Map<String, Slope> slopes;
	private Map<String, Parking> parkings;
	private Map<String, List<Lift>> liftsFromParking;

	public SkiArea(String name) {
		this.name = name;
		this.liftTypes = new HashMap<>();
		this.lifts = new HashMap<>();
		this.slopes = new HashMap<>();
		this.parkings = new HashMap<>();	
		this.liftsFromParking = new HashMap<>();
    }

	/**
	 * Retrieves the name of the ski area
	 * @return name
	 */
	public String getName() { return this.name; }

    /**
     * define a new lift type providing the code, the category (Cable Cabin, Chair, Ski-lift)
     * and the capacity (number of skiers carried) of a single unit
     * 
     * @param code		name of the new type
     * @param category	category of the lift
     * @param capacity	number of skiers per unit
     * @throws InvalidLiftException in case of duplicate code or if the capacity is <= 0
     */
    public void liftType(String code, String category, int capacity) throws InvalidLiftException {
		if(liftTypes.containsKey(code) || capacity<=0){
			throw new InvalidLiftException();
		}
		LiftTypes liftType =  new LiftTypes(code, category, capacity);
		liftTypes.put(code, liftType);
    }
    
    /**
     * retrieves the category of a given lift type code
     * @param typeCode lift type code
     * @return the category of the type
     * @throws InvalidLiftException if the code has not been defined
     */
    public String getCategory(String typeCode) throws InvalidLiftException {
		if(!liftTypes.containsKey(typeCode)){
			throw new InvalidLiftException();
		}
		LiftTypes lift = liftTypes.get(typeCode);
		return lift.getCategory();
    }

    /**
     * retrieves the capacity of a given lift type code
     * @param typeCode lift type code
     * @return the capacity of the type
     * @throws InvalidLiftException if the code has not been defined
     */
    public int getCapacity(String typeCode) throws InvalidLiftException {
		if(!liftTypes.containsKey(typeCode)){
			throw new InvalidLiftException();
		}
        LiftTypes liftType = liftTypes.get(typeCode);
		return liftType.getCapacity();
    }


    /**
     * retrieves the list of lift types
     * @return the list of codes
     */
	public Collection<String> types() {
		return liftTypes.keySet();
	}
	
	/**
	 * Creates new lift with given name and type
	 * 
	 * @param name		name of the new lift
	 * @param typeCode	type of the lift
	 * @throws InvalidLiftException in case the lift type is not defined
	 */
    public void createLift(String name, String typeCode) throws InvalidLiftException{
		if(!liftTypes.containsKey(typeCode)){
			throw new InvalidLiftException();
		}

		Lift lift = new Lift(name, typeCode);

		lifts.put(name, lift);
		liftTypes.get(typeCode).addLift(lift);
    }
    
	/**
	 * Retrieves the type of the given lift
	 * @param lift 	name of the lift
	 * @return type of the lift
	 */
	public String getType(String lift) {
		return lifts.get(lift).getType();
	}

	/**
	 * retrieves the list of lifts defined in the ski area
	 * @return the list of names sorted alphabetically
	 */
	public List<String> getLifts(){
		return lifts.keySet().stream().sorted().collect(Collectors.toList());
    }

	/**
	 * create a new slope with a given name, difficulty and a starting lift
	 * @param name			name of the slope
	 * @param difficulty	difficulty
	 * @param lift			the starting lift for the slope
	 * @throws InvalidLiftException in case the lift has not been defined
	 */
    public void createSlope(String name, String difficulty, String lift) throws InvalidLiftException {
		if(!lifts.containsKey(lift)){
			throw new InvalidLiftException();
		}

		slopes.put(name, new Slope(name, difficulty, lift));
    }
    
    /**
     * retrieves the name of the slope
     * @param slopeName name of the slope
     * @return difficulty
     */
	public String getDifficulty(String slopeName) {
		return slopes.get(slopeName).getDifficulty();
	}

	/**
	 * retrieves the start lift
	 * @param slopeName name of the slope
	 * @return starting lift
	 */
	public String getStartLift(String slopeName) {
		return slopes.get(slopeName).getLift();
	}

	/**
	 * retrieves the list of defined slopes
	 * 
	 * @return list of slopes
	 */
    public Collection<String> getSlopes(){
		return slopes.keySet();
    }

    /**
     * Retrieves the list of slopes starting from a given lift
     * 
     * @param lift the starting lift
     * @return the list of slopes
     */
    public Collection<String> getSlopesFrom(String lift){
		return slopes.values().stream()
			.filter(x->x.getLift().equals(lift))
			.map(Slope::getName)
			.collect(Collectors.toList());
    }

    /**
     * Create a new parking with a given number of slots
     * @param name 	new parking name
     * @param slots	slots available in the parking
     */
    public void createParking(String name, int slots){
		parkings.put(name, new Parking(name, slots));
    }

    /**
     * Retrieves the number of parking slots available in a given parking
     * @param parking	parking name
     * @return number of slots
     */
	public int getParkingSlots(String parking) {
		return parkings.get(parking).getSlots();
	}

	/**
	 * Define a lift as served by a given parking
	 * @param lift		lift name
	 * @param parking	parking name
	 */
	public void liftServedByParking(String lift, String parking) {
		List<Lift> listLift = new ArrayList<>();
		listLift.add(lifts.get(lift));
		liftsFromParking.put(parking, listLift);
	}

	
	/**
	 * Retrieves the list of lifts served by a parking.
	 * @param parking	parking name
	 * @return the list of lifts
	 */
	public Collection<String> servedLifts(String parking) {
		return liftsFromParking.get(parking).stream().map(Lift::getName).collect(Collectors.toList());
	}

	/**
	 * Checks whether the parking is proportional to the capacity of the lift it is serving.
	 * A parking is considered proportionate if its size divided by the sum of the capacity of the lifts 
	 * served by the parking is less than 30.
	 * 
	 * @param parkingName name of the parking to check
	 * @return true if the parking is proportionate
	 */
	public boolean isParkingProportionate(String parkingName) {
		List<Lift> chosenLifts = liftsFromParking.get(parkingName);
		int sum = 0;
		for(Lift lift:chosenLifts){
			int capacity = liftTypes.values().stream()
				.filter(x->x.getLifts().contains(lift))
				.mapToInt(LiftTypes::getCapacity)
				.sum();
			sum  = sum + capacity;
		}
		if(parkings.get(parkingName).getSlots()/sum<30){
			return true;
		}else{
			return false;
		}

		
	}

	/**
	 * reads the description of lift types and lift descriptions from a text file. 
	 * The contains a description per line. 
	 * Each line starts with a letter indicating the kind of information: "T" stands for Lift Type, 
	 * while "L" stands for Lift.
	 * A lift type is described by code, category and seat number. 
	 * A lift is described by the name and the lift type.
	 * Different data on a line are separated by ";" and possible spaces surrounding the separator are ignored.
	 * If a line contains the wrong number of information it should be skipped and
	 * the method should continue reading the following lines. 
	 * 
	 * @param path 	the path of the file
	 * @throws IOException	in case IO error
	 * @throws InvalidLiftException in case of duplicate type or non-existent lift type
	 */
    public void readLifts(String path) throws IOException, InvalidLiftException {
		BufferedReader fileread = new BufferedReader(new FileReader(path));
		String line;
		while((line = fileread.readLine())!=null){
			String[] lineParts = line.split(";");
			
			
			if(lineParts.length==4 && lineParts[0].trim().equals("T")){
				if(liftTypes.containsKey(lineParts[1].trim())){
					throw new InvalidLiftException("Type problem");
				}
				liftType(lineParts[1].trim(), lineParts[2].trim(), Integer.parseInt(lineParts[3].trim()));
			}
			if(lineParts.length==3 && lineParts[0].trim().equals("L")){
				if(!liftTypes.containsKey(lineParts[2].trim())){
					throw new InvalidLiftException("Lift problem");
				}
				createLift(lineParts[1].trim(), lineParts[2].trim());
			}	
		}
    }

}
