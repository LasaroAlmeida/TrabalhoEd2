package trabalhoed;

public class Authors {
    private int id;///Identificador do autor
    private String name; ///Nome do autor
    
    public Authors(){///Construtor
       this.id=0;
       this.name="";
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
   
}
