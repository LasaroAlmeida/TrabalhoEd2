package trabalhoed;

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
    private int contauthors;
    private List<Integer> bestsellersr;
    private List<Integer> categories;
    private int contategorias;
    private String title;
    private int contaseller=0;

    
    public String getTitle() {
        return this.title;
    }

     
    
    public double getRating_avg() {
        return rating_avg;
    }

    public String getIsbn13() {
        return isbn13;
    }
   
    public Registros() {
        this.edition="";
        contauthors=0;
        this.authors=new ArrayList<Integer>();
        this.bestsellersr=new ArrayList<Integer>();
        contategorias=0;
        this.categories=new ArrayList<Integer>();
        this.title = "";
    }

    public void setAuthors(String str)
    {
        
        if(str.equals("[]")){
            this.authors.add(-1);
            return;
        }
        
        str = str.replace("[", "");
        str = str.replace("]", "");
        String[] list_str = str.split(",");
        for(String s : list_str){
            if(!s.isEmpty())
                this.authors.add(Integer.parseInt(s.replace(" ", "")));
        }
    }
    
    public void setcategories(String str) {
       
        if(str.equals("[]")){
            this.categories.add(-1);
            return;
        }
        str = str.replace("[", "");
        str = str.replace("]", "");
        String[] list_str = str.split(",");
        for(String s : list_str){
            if(!s.isEmpty())
            this.categories.add(Integer.parseInt(s.replace(" ", "")));
        }
    }
   public void setBestsellersr(String str) {
       
        if(str.isEmpty()){
            this.bestsellersr.add(-1);
            return;
        }
        String[] list_str = str.split(",");
        for(String s : list_str){
            if(!s.isEmpty())
            this.bestsellersr.add(Integer.parseInt(s.replace(" ", "")));
        }

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
