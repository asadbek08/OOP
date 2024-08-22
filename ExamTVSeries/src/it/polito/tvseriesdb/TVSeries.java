package it.polito.tvseriesdb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TVSeries {
    /**
	 * adds a TV series whose name is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param title the title of the TV Series
	 * @param tService the name of the transmission service 
	 * broadcasting the TV series.
	 * @param genre the genre of the TV Series
	 * @return number of TV Series inserted so far
	 * @throws TSException if TV Series is already inserted or transmission service does not exist.
	 */
    private String title;
    private String tService;
    private String genre;
    private Set<Actor> cast = new HashSet<>();
    private Map<Integer, Season> seasons = new HashMap<>();
    private List<Integer> reviews = new ArrayList<>();
    

    public TVSeries(String title, String tService, String genre){
        this.title = title;
        this.tService = tService;
        this.genre = genre;
    }

    public void addCast(Actor actor){
        cast.add(actor);
    }

    public int getNumcast(){
        return cast.size();
    }

    public String getName(){
        return this.title;
    }

    public String getService(){
        return this.tService;
    }

    public String getGenre(){
        return this.genre;
    }

    public int addSeason(int numEpisodes, String releaseDate) throws TSException{
        int seasonNumber = seasons.size()+1;
        if(seasons.size()>=1){
            String[] currentreleaseDate = releaseDate.split(":");
            String[] previousDate = seasons.get(seasonNumber-1).getDate().split(":");
            if(currentreleaseDate[2].compareTo(previousDate[2])<0){
                throw new TSException("year problem");
            } else if(currentreleaseDate[2].compareTo(previousDate[2])==0){
                if(currentreleaseDate[1].compareTo(previousDate[1])<0){
                    throw new TSException("month problem");
                } else if(currentreleaseDate[1].compareTo(previousDate[1])==0){
                        if(currentreleaseDate[0].compareTo(previousDate[0])<0){
                            throw new TSException("day problem");
                        }
                  }
               
              }
            }
        
        
        seasons.put(seasonNumber, new Season(seasonNumber, releaseDate, numEpisodes));
        return seasons.size();
    }

    public Season getSeason(int seasonNumber){
        return seasons.get(seasonNumber);
    }

    public Set<Season> getSeasons(){
        return seasons.values().stream().collect(Collectors.toSet());
    }

    public boolean hasIncompeletes(){
        for(Season season : seasons.values()){
            if(!season.isComplete()){
                return true;
            }
        }
        return false;
    }

    public List<Integer> getIncompletes(){
        List<Integer> incompletes = new ArrayList<>();
        for(Season season : seasons.values()){
            if(!season.isComplete()){
                incompletes.add(season.getNum());
            }
        }
        return incompletes;
    }

    public void addReview(int score){
        this.reviews.add(score);
    }

    public double getAvg(){
        int sum = 0;

        // Iterate through the list and sum the elements
        for (int number : reviews) {
            sum += number;
        }
        if(reviews.size()>0){
            return (double) sum/reviews.size();
        }else{
            return 0.0;
        }
        

    }

    public String getMostAwaited(String currDate){
        return seasons.values().stream()
            .filter(season -> compareDate( season.getDate(), currDate))
            .max(Comparator.comparingInt(Season::getNum))
            .map(season -> this.title + " " + season.getNum())
            .orElse("");
    }

    public Set<Actor> getCast(){
        return this.cast;
    }
    public static boolean compareDate(String date1, String date2){
        String[] dateCurr = date1.split(":");
        String[] dateOther = date2.split(":");
        if(dateCurr[2].compareTo(dateOther[2])<0){
            return false;
        } else if(dateCurr[2].compareTo(dateOther[2])==0){
            if(dateCurr[1].compareTo(dateOther[1])<0){
                return false;
            } else if(dateCurr[1].compareTo(dateOther[1])==0){
                    if(dateCurr[0].compareTo(dateOther[0])<0){
                        return false;
                    }
              }
           
          }
        return true;
    }


}
