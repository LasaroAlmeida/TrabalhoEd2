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
    private int contauthors;
    private List<Integer> bestsellersr;
    private List<Integer> categories;
    private int contategorias;
    private String title;
    private int contaseller=0;

     
    
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
    }

    public void setAuthors(String str)
    {
        String aux="";
        int auxx=1;
        if(str=="")
        {
            authors.add(-1);
            return;
        }
        while(str.charAt(auxx)!=']')
        {
            
        while(str.charAt(auxx)!=',' && str.charAt(auxx)!=']')
        {
            aux=aux+str.charAt(auxx);
            auxx++;
        }
        authors.add(Integer.parseInt(aux));
            contauthors++;
        if(str.charAt(auxx)==',')
            auxx=auxx+2;
        aux="";
        }
    }
    
    public void setcategories(String str) {
        String aux = "";
        int auxx = 1;
        if (str == "") {
            categories.add(-1);
            return;
        }
        while (str.charAt(auxx) != ']') {

            while (str.charAt(auxx) != ',' && str.charAt(auxx) != ']') {
                aux = aux + str.charAt(auxx);
                auxx++;
            }
            categories.add(Integer.parseInt(aux));
            contategorias++;
            if (str.charAt(auxx) == ',') {
                auxx = auxx + 2;
            }
            aux = "";
        }
    }
   public void setBestsellersr(String str) {
        int tam=str.length();
        int contador=0;
        String auxstr="";
        if(str=="")
        {
            bestsellersr.add(-1);
            return;
        }
        while(contador<tam-1)
        {
            while(str.charAt(contador)!=',' && contador<tam)
        {
            auxstr=auxstr+str.charAt(contador);
            contador++;
            if(contador>=tam)
                break;
        }
//            System.out.println(auxstr);
            bestsellersr.add(Integer.parseInt(auxstr));
//            System.out.println(bestsellersr.get(contaseller));
            contaseller++;
            contador++;
            auxstr="";
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
