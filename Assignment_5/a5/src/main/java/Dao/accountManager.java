package Dao;

import java.util.HashMap;

public class accountManager {
    private static accountManager acc;
    HashMap<String, String> map;
    public accountManager(){
        map = new HashMap<>();
    }
    public static accountManager getInstance(){
        if(acc == null){
            synchronized (accountManager.class){
                if(acc == null) acc = new accountManager();
            }
        }
        return acc;
    }
    public boolean contains(String username){
        return map.containsKey(username);
    }
    public boolean checkPassword(String username, String password){
        return map.get(username).equals(password);
    }
    public void add(String username, String password){
        map.put(username, password);
    }
}
