public class Book {
    private String title;
    private String author;
    private int totalChapters;
    private String imagePath;
    private String pdfPath;

    public Book(String title, String author, int totalChapters, String imagePath, String pdfPath) {
        this.title = title;
        this.author = author;
        this.totalChapters = totalChapters;
        this.imagePath = imagePath;
        this.pdfPath = pdfPath;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalChapters() {
        return totalChapters;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPdfPath() {
        return pdfPath;
    }
}