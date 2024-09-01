package delivery;

public class Dish {
    /**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */

     private String name;
     private float price;

     public Dish(String name,  float price){
        this.name = name;
        
        this.price = price;
     }

     public String getName(){
        return this.name;
     }

     public float getPrice(){
        return this.price;
     }
}
