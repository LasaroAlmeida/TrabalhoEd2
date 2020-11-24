package trabalhoed;

public class Authors {
    private int id;///Identificador do autor
    private String name; ///Nome do autor
    private int number_books; ///Numero de livros
    
    public Authors(){///Construtor
       this.id=0;
       this.name="";
       this.number_books=0;
    }
    
    //GETTERS E SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_books() {
        return number_books;
    }

    public void setNumber_books() { ///Atualiza numero de livros
        this.number_books+=1;
    }
   
}
