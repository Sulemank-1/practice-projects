import java.io.*;
import java.util.ArrayList;

public class StorageEngine {
    public static <T extends Serializable> void saveData(String filename, ArrayList<T> dataList){
        try (ObjectOutputStream output = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)))) {
            output.writeObject(dataList);
        } catch (IOException e) {
            System.out.println("Data couldn't be saved in file: " + e.getMessage());
        }
    }

    public static <T extends Serializable> ArrayList<T> loadData(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
            return (ArrayList<T>) input.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Missing class definition during object reconstruction.");
        } catch (StreamCorruptedException e) {
            System.out.println("File has been manually tampered with or corrupted! Access Blocked.");
        } catch (IOException e) {
            System.out.println("Error reading file. " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
