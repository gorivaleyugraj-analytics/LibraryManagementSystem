import java.io.*;
import java.util.*;

class Book implements Serializable {
    int id;
    String title;
    String author;
    boolean isIssued;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String toString() {
        return id + " | " + title + " | " + author + " | " + (isIssued ? "Issued" : "Available");
    }
}

public class LibraryManagement {
    // ✅ Fixed path for AIDE project
    static final String FILE_NAME = "/storage/emulated/0/AppProjects/LibraryManagement/library.dat";

    public static void addBook(Book book) {
        try {
            ArrayList<Book> books = readBooks();
            books.add(book);
            writeBooks(books);
            System.out.println("✅ Book added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e);
        }
    }

    public static void displayBooks() {
        try {
            ArrayList<Book> books = readBooks();
            if (books.isEmpty()) {
                System.out.println("No books available.");
                return;
            }
            for (Book b : books) {
                System.out.println(b);
            }
        } catch (Exception e) {
            System.out.println("Error displaying books: " + e);
        }
    }

    public static void searchBook(int id) {
        try {
            ArrayList<Book> books = readBooks();
            for (Book b : books) {
                if (b.id == id) {
                    System.out.println("Found: " + b);
                    return;
                }
            }
            System.out.println("❌ Book not found.");
        } catch (Exception e) {
            System.out.println("Error searching book: " + e);
        }
    }

    public static void issueBook(int id) {
        try {
            ArrayList<Book> books = readBooks();
            for (Book b : books) {
                if (b.id == id) {
                    if (!b.isIssued) {
                        b.isIssued = true;
                        writeBooks(books);
                        System.out.println("✅ Book issued successfully!");
                        return;
                    } else {
                        System.out.println("❌ Book already issued.");
                        return;
                    }
                }
            }
            System.out.println("❌ Book not found.");
        } catch (Exception e) {
            System.out.println("Error issuing book: " + e);
        }
    }

    public static void returnBook(int id) {
        try {
            ArrayList<Book> books = readBooks();
            for (Book b : books) {
                if (b.id == id) {
                    if (b.isIssued) {
                        b.isIssued = false;
                        writeBooks(books);
                        System.out.println("✅ Book returned successfully!");
                        return;
                    } else {
                        System.out.println("❌ Book was not issued.");
                        return;
                    }
                }
            }
            System.out.println("❌ Book not found.");
        } catch (Exception e) {
            System.out.println("Error returning book: " + e);
        }
    }

    // Helper methods
    @SuppressWarnings("unchecked")
    public static ArrayList<Book> readBooks() throws Exception {
        File f = new File(FILE_NAME);
        if (!f.exists() || f.length() == 0) {
            return new ArrayList<>();
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        ArrayList<Book> books = (ArrayList<Book>) ois.readObject();
        ois.close();
        return books;
    }

    public static void writeBooks(ArrayList<Book> books) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
        oos.writeObject(books);
        oos.close();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    addBook(new Book(id, title, author));
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    System.out.print("Enter Book ID to search: ");
                    searchBook(sc.nextInt());
                    break;
                case 4:
                    System.out.print("Enter Book ID to issue: ");
                    issueBook(sc.nextInt());
                    break;
                case 5:
                    System.out.print("Enter Book ID to return: ");
                    returnBook(sc.nextInt());
                    break;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
        sc.close();
    }
}
