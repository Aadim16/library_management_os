package OSProject;

public class Book {
    private String title;
    private int totalCopies; // Total number of copies for this book

    public Book(String title, int totalCopies) {
        this.title = title;
        this.totalCopies = totalCopies;
    }

    public String getTitle() {
        return title;
    }

    public int getCopies() {
        return totalCopies; // Return the total number of copies for Semaphore initialization
    }

    @Override
    public String toString() {
        return title + " (" + totalCopies + " total copies)";
    }
}
