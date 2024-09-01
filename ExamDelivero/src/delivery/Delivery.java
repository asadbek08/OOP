package delivery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Delivery {
	// R1
	private List<String> cats = new ArrayList<>();
	private Map<String, Restaurant> rests = new HashMap<>();
	private  Map<String, List<Restaurant>> restByCat = new HashMap<>();
	private Map<Integer, Order> orders = new HashMap<>();

	public int OrderId=0;


    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		if(cats.contains(category)){
			throw new DeliveryException();
		}
		cats.add(category);

	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return cats;
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		if(!cats.contains(category)){
			throw new DeliveryException();
		}
		Restaurant restaurant = new Restaurant(name, category);
		rests.put(name,restaurant);
		if(restByCat.containsKey(category)){
			restByCat.get(category).add(restaurant);
		}else{
			List<Restaurant> listRest = new ArrayList();
			listRest.add(restaurant);
			restByCat.put(category, listRest);
		}
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		if(restByCat.get(category).isEmpty() ){
			return List.of();
		}else{
			return restByCat.get(category).stream()
				.map(Restaurant::getName)
				.collect(Collectors.toList());
		}
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		rests.get(restaurantName).addDish(name, price);
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
        return rests.entrySet().stream()
				.collect(Collectors.toMap(
					Map.Entry::getKey, 
					entry-> entry.getValue().getDishes().values().stream()
							.filter(x->x.getPrice()>=minPrice && x.getPrice()<=maxPrice)
							.map(Dish::getName)
							.collect(Collectors.toList())
					)
				);
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		if(!rests.containsKey(restaurantName)|| rests.get(restaurantName).getDishes().isEmpty()){
			return List.of();
		}
        return rests.get(restaurantName).getDishes().values().stream()
				.map(Dish::getName)
				.sorted()
				.collect(Collectors.toList());
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
        return restByCat.get(category).stream()
				.flatMap(x -> x.getDishes().keySet().stream())
				.collect(Collectors.toList());	
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
	    int id = OrderId+1;
		Order order = new Order(dishNames, quantities, customerName, restaurantName, deliveryTime, deliveryDistance);
		orders.put(id, order);
		OrderId++;
		return id;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
        return orders.entrySet().stream()
			.filter(x->x.getValue().getTime()==deliveryTime && x.getValue().getDistance()<maxDistance)
			.sorted(Comparator.comparingInt(x->x.getValue().getTime()))
			.limit(maxOrders)
			.peek(entry-> entry.getValue().setAssigned())
			.map(Map.Entry::getKey)
			.collect(Collectors.toList());
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
        return orders.values().stream()
			.filter(Order::ifAssigned)
			.collect(Collectors.toList()).size();
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		rests.get(restaurantName).setRating(rating);
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
        return rests.values().stream()
			.sorted(Comparator.comparingDouble(Restaurant::getAvarageRating).reversed())
			.map(Restaurant::getName)
			.collect(Collectors.toList());
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
        return restByCat.entrySet().stream()
				.collect(Collectors.toMap(
					Map.Entry::getKey,
			      	x->x.getValue().stream().mapToLong(res->res.getDishes().size()).sum()));
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
        return rests.values().stream()
			.sorted(Comparator.comparingDouble(Restaurant::getAvarageRating).reversed())
			.map(Restaurant::getName)
			.collect(Collectors.toList()).get(0);
	}
}
