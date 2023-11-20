package models;

public class Order {
    private long id;
    private long petId;
    private int quantity;
    private String shipDate;
    private String orderStatus;
    private boolean complete=false;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public long getPetId(long petId){
        return petId;
    }

    public void setPetId(long petId){
        this.petId=petId;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public String getShipDate(){
        return shipDate;
    }

    public void setShipDate(String shipDate){
        this.shipDate=shipDate;
    }

    public String getOrderStatus(){
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus){
        this.orderStatus=orderStatus;
    }

    public boolean getComplete(boolean complete){
        return complete;
    }

    public void setComplete(boolean complete){
        this.complete=complete;
    }

    public static Order createOrder(){

        Order order = new Order();
        order.setId(8973);
        order.setPetId(8541);
        order.setQuantity(2);
        order.setShipDate("2018-12-31");
        order.setOrderStatus("available");
        order.setComplete(false);
        return order;
    }

}
