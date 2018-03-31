package hello;

public class Greeting {

    private final long id;
    private final String name;
    private final String item;
    private final String size;
    private final String price;

    public Greeting(long id, String name, String item, String size, String price) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.size = size;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItem() {
        return item;
    }

    public String getSize() {
        return size;
    }

    public String getPrice() {
        return price;
    }
}
