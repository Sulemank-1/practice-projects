import java.util.Date;

public class Task {
    //Data Fields
    private int id;
    private String description;
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    private static int maxId = 1;

    //Constructors
    public Task(String description){
        this.id = maxId++;
        this.description = description;
        this.status = Status.TO_DO;
        this.createdAt = new Date();
    }

    public Task(int id, String description, Status status){
        this.id = id;
        this.description = description;
        this.status = status;
    }

    //Getters
    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    //Setters
    public void setDescription(String description){
        this.description = description;
        this.updatedAt = new Date();
    }

    public void setStatus(Status status){
        this.status = status;
        this.updatedAt = new Date();
    }

    public static void setMaxId(int newMaxId) {
        maxId = newMaxId;
    }


    //Methods
    @Override
    public String toString(){
        return getId() +": " + getDescription() + ", " + getStatus();
    }
}
