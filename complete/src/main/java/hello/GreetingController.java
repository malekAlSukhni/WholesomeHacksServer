package hello;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int TYPE = 1;
    private static final int ITEM = 2;
    private static final int SIZE = 3;
    private static final int PRICE = 4;
    private static final int CURRENT = 5;

    HashMap<String, ArrayList<ArrayList<String>>> globalMapByType = new HashMap();
    HashMap<String, ArrayList<ArrayList<String>>> globalMapByOwner = new HashMap();
    HashMap<String, ArrayList<ArrayList<String>>> globalMapByOwned = new HashMap();

    @RequestMapping("/get")
    public ArrayList<Greeting> greetingget(@RequestParam(value="type", defaultValue="World") String type) {
        return doGet("list", type);
    }

    @RequestMapping("/put")
    public Boolean greetingput(@RequestParam(value="name", defaultValue="World") String name,
                               @RequestParam(value="item", defaultValue="World") String item,
                               @RequestParam(value="type", defaultValue="World") String type,
                               @RequestParam(value="size", defaultValue="World") String size,
                               @RequestParam(value="price", defaultValue="World") String price) {
        return doPut(name, item, type, size, price);
    }

    public synchronized ArrayList<Greeting> doGet(String reqType, String type) {

        ArrayList<Greeting> finalreturn = new ArrayList<>();

        switch(reqType){
            case "list":
                String itemType = type;
                ArrayList<ArrayList<String>> list = globalMapByType.get(itemType);
                if(list == null){
                    break;
                }
                for(ArrayList<String> a : list) {
                    Greeting g = new Greeting(Long.parseLong(a.get(ID)), a.get(NAME), a.get(ITEM), a.get(SIZE), a.get(PRICE));
                    finalreturn.add(g);
                }
        }

        return finalreturn;
    }

    public synchronized boolean doPut(String name, String item, String type, String size, String price) {

        String currentOwner = "";
        String id = Integer.toString((int) counter.incrementAndGet());

        if(globalMapByType.get(type) != null){
            ArrayList<ArrayList<String>> listOfItems = globalMapByType.get(type);
            ArrayList<String> info = new ArrayList<>();
            info.add(id);
            info.add(name);
            info.add(item);
            info.add(size);
            info.add(price);
            info.add(currentOwner);
            listOfItems.add(info);
            globalMapByType.put(type, listOfItems);
        } else {
            ArrayList<ArrayList<String>> newListOfItems = new ArrayList<>();
            ArrayList<String> info = new ArrayList<>();
            info.add(id);
            info.add(name);
            info.add(item);
            info.add(size);
            info.add(price);
            info.add(currentOwner);
            newListOfItems.add(info);
            globalMapByType.put(type, newListOfItems);
        }

        if(globalMapByOwner.get(name) != null){
            ArrayList<ArrayList<String>> listOfItems = globalMapByOwner.get(name);
            ArrayList<String> info = new ArrayList<>();
            info.add(id);
            info.add(type);
            info.add(item);
            info.add(size);
            info.add(price);
            info.add(currentOwner);
            listOfItems.add(info);
            globalMapByOwner.put(name, listOfItems);
        } else {
            ArrayList<ArrayList<String>> newListOfItems = new ArrayList<>();
            ArrayList<String> info = new ArrayList<>();
            info.add(id);
            info.add(type);
            info.add(item);
            info.add(size);
            info.add(price);
            info.add(currentOwner);
            newListOfItems.add(info);
            globalMapByOwner.put(name, newListOfItems);
        }

        return true;
    }
}
