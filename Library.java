package OSProject;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

public class Library {
    private Map<Book, Semaphore> bookStock;
    private Map<Book, Queue<User>> waitingQueue;
    private JTextArea outputArea;

    public Library(List<Book> books, JTextArea outputArea) {
        bookStock = new HashMap<>();
        waitingQueue = new HashMap<>();
        this.outputArea = outputArea;

        for (Book book : books) {
            bookStock.put(book, new Semaphore(book.getCopies())); // Initialize with available copies
            waitingQueue.put(book, new LinkedList<>());
        }
    }

    public void requestBook(User user, Book book) {
        if (bookStock.get(book).availablePermits() > 0) {
            allocateBook(user, book);
        } else {
            outputArea.append("Book \"" + book.getTitle() + "\" is out of stock. Adding " + user.getName() + " to the waiting list.\n");
            waitingQueue.get(book).add(user); // Add user to the queue if the book is unavailable
        }
    }

    private void allocateBook(User user, Book book) {
        try {
            bookStock.get(book).acquire();
            outputArea.append(user.getName() + " has borrowed \"" + book.getTitle() + "\".\n");
            new Thread(user).start(); // Simulate 15 days usage
        } catch (InterruptedException e) {
            outputArea.append("Failed to allocate book to " + user.getName() + ".\n");
        }
    }

    public void returnBook(User user, Book book) {
        outputArea.append(user.getName() + " returned \"" + book.getTitle() + "\".\n");
        bookStock.get(book).release();

        Queue<User> queue = waitingQueue.get(book);
        if (!queue.isEmpty()) {
            User nextUser = queue.poll();
            outputArea.append("Allocating returned book \"" + book.getTitle() + "\" to waiting user " + nextUser.getName() + ".\n");
            allocateBook(nextUser, book); // Allocate to next user in queue
        } else {
            outputArea.append("Book \"" + book.getTitle() + "\" now has " + bookStock.get(book).availablePermits() + " copies available.\n");
        }
    }
}
