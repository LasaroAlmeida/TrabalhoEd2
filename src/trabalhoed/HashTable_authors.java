package trabalhoed;

public class HashTable_authors {
    private Authors [] table_authors;  ///Vetor que faz o papel da hash de autores
    private int length; ///Tamanho do hash de autores
    private int m; ///Maior primo menor que o tamanho da tabela
    
    public HashTable_authors(int n) { ///Construtor. Recebe o numero de elementos que serao inseridos
        this.length=encontra_primo_menor((n+n/2)); ///Tamanho da hash recebe o maior numero primo menor que n recebido + a metade de n e faz do maior primo menor que este valor do tamanho da hash
        this.table_authors = new Authors[this.length]; ///faz do lenght o tamanho da hash de autores
        this.m=encontra_primo_menor(this.length); /// armazena o maior primo menor que o tamanho atual da hash
        for(int i=0;i < this.length; i++){ ///inicializa a hash autores com null
            this.table_authors[i] = null;
        }
    }
    
    public void insert(Authors author){ ///insere os autores na hash recebendo um objeto do tipo autor usando seu id para hasheado
        int h1 = hash_h1(author.getId()); ///chama a primeira funcao da sondagem dupla 
        int h2 = hash_h2(author.getId()); ///chama a segunda funcao da sondagem dupla
        int i=0; ///inicializa o i para a funcao de sondagem dupla
        int  h = double_hash(h1, h2, i); ///chama a funcao para a sondagem dupla
        while(i < this.length){ ///enquanto o i for menor que o tamanho da hash
            if(this.table_authors[h]==null){ ///verifica se a posicao hasheada ainda nao foi ocupada
                author.setNumber_books(); ///incrementa o numero de livros do autor, inicializado inicialmente como 0 em autores
                this.table_authors[h] = author; ///adiciona o autor na posicao hasheada
                return; 
            }
            else if(this.table_authors[h].getId() == author.getId()){ ///verifica se a posicao hasheada tem como autor o autor que agora queremos inserer 
                author.setNumber_books(); ///incrementa o numero de livros do autor já hasheado anteriormente
                return;
            }
            i+=1; ///incrementa i para a proxima iteracao da sondagem dupla
            h=double_hash(h1, h2, i);
        }
    }
    
    private int hash_h1(int id){ ///primeira funcao da sondagem linear
        return (id*11)%this.length; ///multiplica o id por 11 e retorna seu resto pelo tamanho da hash
    }
    
    private int hash_h2(int id){
        return 1+(id*3)%this.m; ///multiplica o id por 3, o incrementa em 1 e retorna seu resto pelo tamanho da hash
    }
    
    private int double_hash(int h1, int h2, int i){ ///funcao de sondagem dupla que recebe os parametros da funcao
        return (h1 + i*h2)%this.length;
    }
    
    public int getLength() { ///funcao que  retorna o tamanho da hash
        return length;
    }
    
    public Authors getPosition(int i){ ///funcao que retorna a posicao referente a um indice da hash
        return (this.table_authors[i]!=null)? this.table_authors[i] : null; ///retorna o objeto autor caso a posicao tenha sido hasheada ou null caso nao
    }
    
    private int encontra_primo_menor(int tam) { ///retorna o maior primo menor que o valor passado como parametro
        int nao_primo;
        for (int i = tam - 1; i >= 2; i--) {
            nao_primo = 0;
            for (int j = 2; j < i / 2; j++) {
                if (i % j == 0) {
                    nao_primo++;
                }
            }
            if (nao_primo == 0) {
                return i;
            }
        }
        return 0;
    }
}
