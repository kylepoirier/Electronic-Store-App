//Base class for all products the store will sell
public class Product{
 private double price;
 private int stockQuantity;
 private int soldQuantity;
 
 public Product(double initPrice, int initQuantity){
   price = initPrice;
   stockQuantity = initQuantity;
 }
 
 public int getStockQuantity(){
   return stockQuantity;
 }

 public void removeStockQuantity(int amount) {stockQuantity -= amount;}

 public void addStockQuantity(int amount) {stockQuantity += amount;}
 
 public int getSoldQuantity(){
   return soldQuantity;
 }

 public void addSoldQuantity(int amount) {soldQuantity += amount;}

 public void removeSoldQuantity(int amount) {soldQuantity -= amount;}
 
 public double getPrice(){
   return price;
 }
 
 //Returns the total revenue (price * amount) if there are at least amount items in stock
 //Return 0 otherwise (i.e., there is no sale completed)
 public double sellUnits(int amount){
   if(amount > 0 && stockQuantity >= amount){
     stockQuantity -= amount;
     soldQuantity += amount;
     return price * amount;
   }
   return 0.0;
 }

}