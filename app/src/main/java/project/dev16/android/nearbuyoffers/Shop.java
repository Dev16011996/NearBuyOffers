package project.dev16.android.nearbuyoffers;

/**
 * Created by DEV on 24-09-2016.
*/
public class Shop {
    //name and address string
    private String name;
    private String item;
    private String quantity;
    private String discount;

    public Shop() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }


    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getDiscount() {
        return discount;
    }


}

