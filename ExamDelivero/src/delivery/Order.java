package delivery;

public class Order {
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

     private String[] dishNames;
     private int[] quantities;
     private String customerName;
     private String resName;
     private int deliveryTime;
     private int deliveryDistance;

     private boolean Assigned=false;

     public Order(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance){
        this.dishNames = dishNames;
        this.quantities = quantities;
        this.customerName = customerName;
        this.resName = restaurantName;
        this.deliveryDistance = deliveryDistance;
        this.deliveryTime = deliveryTime;
     }

     public int getDistance(){
        return this.deliveryDistance;
     }

     public int getTime(){
        return this.deliveryTime;
     }

     public void setAssigned(){
        this.Assigned = true;
     }

     public boolean ifAssigned(){
        return this.Assigned;
     }


}
