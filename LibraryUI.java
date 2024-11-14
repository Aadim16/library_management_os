package OSProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class LibraryUI extends JFrame {
    private JTextField nameField;
    private JComboBox<String> bookDropdown;
    private JTextArea outputArea;
    private Library library;
    private List<Book> books;

    public LibraryUI() {
        setTitle("Library Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the list of books
        books = Arrays.asList(
                new Book("Java Programming", 3),
                new Book("Data Structures", 2),
                new Book("Operating Systems", 1)
        );

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        library = new Library(books, outputArea);

        // Input panel for user name and book selection
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        nameField = new JTextField();

        // Initialize dropdown with available books
        bookDropdown = new JComboBox<>();
        updateBookDropdown();

        inputPanel.add(new JLabel("User Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Select Book:"));
        inputPanel.add(bookDropdown);

        // Request and Return buttons
        JButton requestButton = new JButton("Request Book");
        JButton returnButton = new JButton("Return Book");
        inputPanel.add(requestButton);
        inputPanel.add(returnButton);

        // Request book button action
        requestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = nameField.getText();
                String bookTitle = (String) bookDropdown.getSelectedItem();
                handleRequest(userName, bookTitle);
            }
        });

        // Return book button action
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = nameField.getText();
                String bookTitle = (String) bookDropdown.getSelectedItem();
                handleReturn(userName, bookTitle);
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    private void handleRequest(String userName, String bookTitle) {
        Book book = findBook(bookTitle);
        if (book != null) {
            User user = new User(userName, library);
            user.requestBook(book);
            updateBookDropdown(); // Update dropdown after request
        } else {
            outputArea.append("Book \"" + bookTitle + "\" does not exist in the library.\n");
        }
    }

    private void handleReturn(String userName, String bookTitle) {
        Book book = findBook(bookTitle);
        if (book != null) {
            library.returnBook(new User(userName, library), book);
            updateBookDropdown(); // Update dropdown after return
        } else {
            outputArea.append("Book \"" + bookTitle + "\" does not exist in the library.\n");
        }
    }

    // Update dropdown to show all books regardless of availability
    private void updateBookDropdown() {
        bookDropdown.removeAllItems();
        for (Book book : books) {
            bookDropdown.addItem(book.getTitle());
        }
    }

    private Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LibraryUI().setVisible(true);
            }
        });
    }
}
