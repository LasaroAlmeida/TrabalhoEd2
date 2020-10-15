package trabalhoed2_parte1;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Registros {
    
    private List<Integer> authors;
    private String edition;
    private BigInteger id;
    private String isbn10;
    private String isbn13;
    private double rating_avg;
    private int rating_count;

    public double getRating_avg() {
        return rating_avg;
    }

    public String getIsbn13() {
        return isbn13;
    }
    private String title;
    public Registros()
    {
        this.edition="";
        this.authors=new ArrayList<Integer>();
    }

    public void setAuthors(List<Integer> authors) {
        this.authors = authors;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public void setRating_avg(double rating_avg) {
        this.rating_avg = rating_avg;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public void setTitle(String title) {
        this.title = title;
    }
   

    public String getEdition() {
        return edition;
    }

 
}