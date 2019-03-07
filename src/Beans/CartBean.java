package Beans;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class CartBean implements Serializable {
    private LinkedHashMap<String, CartItemBean> itemsById;

    public CartBean(){
        itemsById = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, CartItemBean> getItemsById() {
        return itemsById;
    }

    public void setItemsById(LinkedHashMap<String, CartItemBean> itemsById) {
        this.itemsById = itemsById;
    }

    public CartItemBean getItemById(String id){
        return itemsById.get(id);
    }

    public void removeItemById(String id){
        if(!itemsById.containsKey(id))
            return;

        CartItemBean item = itemsById.get(id);

        if(item.getNumberOrdered() > 1)
            item.setNumberOrdered(item.getNumberOrdered() - 1);
    }

    public void throwItemById(String id){
        if(!itemsById.containsKey(id))
            return;
        itemsById.remove(id);
    }

    public void addItem(CartItemBean item){
        if(!itemsById.containsKey(item.getProduct())) {
            itemsById.put(item.getProduct(), item);
            return;
        }
        CartItemBean bean = itemsById.get(item.getProduct());
        bean.setNumberOrdered(bean.getNumberOrdered() + 1);
    }

    public int getItemsAmount(){
        int result = 0;
        for (String product: itemsById.keySet()) {
            result += itemsById.get(product).getNumberOrdered();
        }
        return result;
    }

    public double getTotalPrice() {
        double result = 0;
        for (String product: itemsById.keySet()) {
            result += itemsById.get(product).getPrice() * itemsById.get(product).getNumberOrdered();
        }
        return result;
    }
}