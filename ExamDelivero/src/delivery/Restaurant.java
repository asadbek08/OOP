package delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String name;
    private String category;
    private List<Integer> ratings = new ArrayList<>();

    private Map<String, Dish> dishes = new HashMap<>();

    public Restaurant(String name, String category){
        this.name = name;
        this.category = category;
    } 

    public String getName(){
        return this.name;
    }

    public String getCategory(){
        return this.category;
    }

    public void addDish(String name, float price) throws DeliveryException{
        if(dishes.containsKey(name)){
            throw new DeliveryException();
        }
        dishes.put(name, new Dish( name, price));
    }

    public Map<String, Dish> getDishes(){
        return dishes;
    }

    public void setRating(int rating){
        this.ratings.add(rating);
    }

    public double getAvarageRating(){
        return ratings.stream().mapToInt(Integer::intValue).sum()/ratings.size();
    }
}
