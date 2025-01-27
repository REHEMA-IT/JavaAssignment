import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    public static void saveBooksToFile(ArrayList<Book> books, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(books);
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Book> loadBooksFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Book>) ois.readObject();
        }
    }
}