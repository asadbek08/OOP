package it.polito.ski;

public class Slope {
    /**
	 * create a new slope with a given name, difficulty and a starting lift
	 * @param name			name of the slope
	 * @param difficulty	difficulty
	 * @param lift			the starting lift for the slope
	 * @throws InvalidLiftException in case the lift has not been defined
	 */

    private String name;
    private String difficulty;
    private String lift;

    public Slope(String name, String difficulty, String lift){
        this.name = name;
        this.difficulty = difficulty;
        this.lift = lift;
    }

    public String getName(){
        return this.name;
    }

    public String getDifficulty(){
        return this.difficulty;
    }

    public String getLift(){
        return this.lift;
    }
}
