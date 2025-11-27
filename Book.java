package model;

import java.time.LocalDate;

public class Book {
    private String id;               // unique identifier
    private String title;
    private String author;
    private String genre;

    private boolean borrowed;
    private String borrowedBy;       // username of borrower
    private LocalDate borrowDate;    // optional
    private LocalDate dueDate;       // due date for return

    private boolean reserved;
    private String reservedBy;       // username of reserver
    private String coverImage;       // path to cover image

    // Basic constructor
    public Book(String id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.borrowed = false;
        this.borrowedBy = "";
        this.reserved = false;
        this.reservedBy = "";
        this.borrowDate = null;
        this.dueDate = null;
        this.coverImage = "";
    }

    // Full constructor for DAO loading
    public Book(String id, String title, String author, String genre,
                boolean borrowed, String borrowedBy, LocalDate borrowDate,
                LocalDate dueDate, boolean reserved, String reservedBy, String coverImage) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.borrowed = borrowed;
        this.borrowedBy = borrowedBy == null ? "" : borrowedBy;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.reserved = reserved;
        this.reservedBy = reservedBy == null ? "" : reservedBy;
        this.coverImage = coverImage == null ? "" : coverImage;
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }

    public boolean isBorrowed() { return borrowed; }
    public String getBorrowedBy() { return borrowedBy; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }

    public boolean isReserved() { return reserved; }
    public String getReservedBy() { return reservedBy; }
    public String getCoverImage() { return coverImage; }

    // --- Setters ---
    public void setBorrowed(boolean borrowed) { this.borrowed = borrowed; }
    public void setBorrowedBy(String borrowedBy) { this.borrowedBy = borrowedBy; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setReserved(boolean reserved) { this.reserved = reserved; }
    public void setReservedBy(String reservedBy) { this.reservedBy = reservedBy; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
}
