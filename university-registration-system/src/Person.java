import java.io.Serializable;

public abstract class Person implements Serializable {
    //Data Fields
    private int id;
    private String name;

    //Constructor
    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Methods
    public abstract String getRole();
}
