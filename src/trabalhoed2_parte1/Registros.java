package trabalhoed2_parte1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Registros {
    
    private List<Integer> authors;
    private int contauthors;
    private List<Integer> bestsellersr;
    private List<Integer> categories;
    private int contategorias;
    private String edition;
    private BigInteger id;
    private String isbn10;
    private String isbn13;
    private double rating_avg;
    private int rating_count;
    private String title;
    public Registros()
    {
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
            System.out.println(authors.get(contauthors));
            contauthors++;
        if(str.charAt(auxx)==',')
            auxx=auxx+2;
        aux="";
        }
        return;
    }

    public List<Integer> getAuthors() {
        return authors;
    }

    public List<Integer> getBestsellersr() {
        return bestsellersr;
    }

    public void setBestsellersr(String str) {
        int auxx=str.length(),contt=0,guarda;
        System.out.println(auxx);
        String aux="";
        if(str=="")
        {
            bestsellersr.add(-1);
            return;
        }
        while(contt<auxx-1)
        {
        while(str.charAt(contt)!=',' && contt<auxx)
        {
            System.out.println(contt);
            aux=aux+str.charAt(contt);
            System.out.println(aux);
            contt++;
            if(contt>auxx-1)
                break;
        }
//        guarda=(Integer.parseInt(aux));
            System.out.println(aux);
//        bestsellersr.add(guarda);
        bestsellersr.add(Integer.parseInt(aux));
            System.out.println("Impresso");
        aux="";
        }
        
    }
    public void setcategories(String str)
    {
        String aux="";
        int auxx=1;
        if(str=="")
        {
            categories.add(-1);
            return;
        }
        while(str.charAt(auxx)!=']')
        {
            
        while(str.charAt(auxx)!=',' && str.charAt(auxx)!=']')
        {
            aux=aux+str.charAt(auxx);
            auxx++;
            System.out.println(aux);
        }
        categories.add(Integer.parseInt(aux));
            System.out.println(categories.get(contategorias));
            contategorias++;
        if(str.charAt(auxx)==',')
            auxx=auxx+2;
        aux="";
        }
        return;
    }
    
    public void setedition(String str)
    {
        this.edition=str;
    }

    public String getEdition() {
        return edition;
    }
    public void setId(String str)
    {
        id=new BigInteger(str);
        System.out.println(this.id);
    }
}
