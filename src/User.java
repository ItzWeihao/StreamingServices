import java.util.*;

public class User {
    String username;
    LinkedList<Media> myList;

    public User(String username, String Password) {
        this.username = username;
        myList = new LinkedList<Media>();
    }

    public void addToList(Media Media) {
        myList.add(0, Media);
    }

    public void removeFromList(Media Media) {
        myList.remove(Media);
    }

    public LinkedList<Media> getList() {
        return myList;
    }
    public String getUsername(){
        return username;
    }
}