package OSProject;

public class User implements Runnable {
    private String name;
    private Book book;
    private Library library;

    public User(String name, Library library) {
        this.name = name;
        this.library = library;
    }

    public void requestBook(Book book) {
        this.book = book;
        library.requestBook(this, book);
    }

    @Override
    public void run() {
        try {
            // Simulate 15 days of usage (15 seconds for testing)
            Thread.sleep(15000);
            library.returnBook(this, book);
        } catch (InterruptedException e) {
            System.out.println(name + " was interrupted.");
        }
    }

    public String getName() {
        return name;
    }
}
