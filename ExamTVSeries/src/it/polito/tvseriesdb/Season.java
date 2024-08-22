package it.polito.tvseriesdb;

import java.util.HashSet;
import java.util.Set;

public class Season {
    /**
	 * adds a season to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numEpisodes	number of episodes of the season
	 * @param releaseDate	release date for the season (format "gg:mm:yyyy")
	 * @return number of seasons inserted so far for the TV series
	 * @throws TSException in case of non-existing TV Series or wrong releaseDate
	 */
    
    private int seasonNumber;
    private String releaseDate;
    private int numEpisodes;

    private Set<String> episoSet = new HashSet<>();

    public Season(int seasonNumber, String releaseDate, int numEpisodes){
        this.seasonNumber = seasonNumber;
        this.releaseDate = releaseDate;
        this.numEpisodes = numEpisodes;
    }

    public String getDate(){
        return this.releaseDate;
    }

    public int addEpisode(String episodeTitle) throws TSException{
        if(episoSet.size()>=numEpisodes){
            throw new TSException();
        }
        if(!episoSet.add(episodeTitle)){
            throw new TSException();
        }

        return episoSet.size();

    }

    public boolean isComplete(){
        return numEpisodes==episoSet.size();
    }

    public int getNum(){
        return this.seasonNumber;
    }

    
    
}

