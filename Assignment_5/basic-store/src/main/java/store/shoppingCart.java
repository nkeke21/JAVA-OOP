package store;

import java.util.ArrayList;
import java.util.HashMap;

public class shoppingCart{

    private HashMap<Product, Integer> map;

    public shoppingCart(){

        map = new HashMap<>();
    }

    public void addProduct(Product prod, int value){

        if(value <= 0){
            map.remove(prod);
            return;
        }

        map.put(prod, value);
    }

    public boolean contains(Product prod){
        return map.containsKey(prod);
    }

    public ArrayList<Product> getProducts(){

        ArrayList<Product> res = new ArrayList<>();

        for(Product prod: map.keySet()) res.add(prod);
        return res;

    }

    public int getValue(Product key){
        return map.get(key);
    }
}
