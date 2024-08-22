package it.polito.tvseriesdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    /**
	 * Add a new user to the database, with a unique username.
	 * 
	 * @param username	username of the user
	 * @param genre		user favourite genre
	 * @return number of registered users
	 * @throws TSException in case username is already registered
	 */

    private String username;
    private String genre;
    private List<TVSeries> likedSeries = new ArrayList<>();
    private Map<String, Double> reviews = new HashMap<>();

    public User(String username, String genre){
        this.username = username;
        this.genre = genre;
    }

    public int addLiked(TVSeries serie){
        this.likedSeries.add(serie);
        return this.likedSeries.size();
    }
    public String getGenre(){
        return this.genre;
    }
    public List<TVSeries> getLiked(){
        return this.likedSeries;
    }

    public void addReview(String tvSeriesTitle, double avg){
        reviews.put(tvSeriesTitle, avg);
    }

    public double getUserAvg(){
        double sum = 0;

        // Iterate through the list and sum the elements
        for (double number : reviews.values()) {
            sum += number;
        }
        return (double) sum/reviews.size();
    }

    public Map<String, Double> getrevies(){
        return this.reviews;
    }

}
