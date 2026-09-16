import java.io.*;
import java.util.*;

public class StorageEngine {

    public static <K extends Serializable, V extends Serializable> void saveData(String filename, Map<K, V> dataList) {
        try (ObjectOutputStream output = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)))) {
            output.writeObject(dataList);
        } catch (IOException e) {
            System.out.println("Data couldn't be saved in file: " + e.getMessage());
        }
    }

    public static <K extends Serializable, V extends Serializable> Map<K, V> loadData(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
            return (Map<K, V>) input.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Missing class definition during object reconstruction.");
        } catch (StreamCorruptedException e) {
            System.out.println("File has been manually tampered with or corrupted! Access Blocked.");
        } catch (IOException e) {
            System.out.println("Error reading file. " + e.getMessage());
        }
        return new HashMap<>();
    }
}