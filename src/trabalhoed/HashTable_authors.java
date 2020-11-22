package trabalhoed;

public class HashTable_authors {
    private Authors [] table_authors;
    private int length;
    private int m;
    
    public HashTable_authors(int n) {
        this.length=encontra_primo_menor((n+n/2));
        this.table_authors = new Authors[this.length];
        this.m=encontra_primo_menor(this.length);
        for(int i=0;i < this.length; i++){
            this.table_authors[i] = null;
        }
    }
    
    public void insert(Authors author){
        int h1 = hash_h1(author.getId());
        int h2 = hash_h2(author.getId());
        int i=0;
        int  h = double_hash(h1, h2, i);
        while(i < this.length){
            if(this.table_authors[h]==null){
                author.setNumber_books();
                this.table_authors[h] = author;
                return;
            }
            else if(this.table_authors[h].getId() == author.getId()){
                author.setNumber_books();
                return;
            }
            i+=1;
            h=double_hash(h1, h2, i);
        }
    }
    
    private int hash_h1(int id){
        return (id*11)%this.length;
    }
    
    private int hash_h2(int id){
        return 1+(id*3)%this.m;
    }
    
    private int double_hash(int h1, int h2, int i){
        return (h1 + i*h2)%this.length;
    }
    
    public int getLength() {
        return length;
    }
    
    public Authors getPosition(int i){
        return (this.table_authors[i]!=null)? this.table_authors[i] : null;
    }
    
    private int encontra_primo_menor(int tam) {
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
