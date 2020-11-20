package trabalhoed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class HashTable {

    Registros []table;
    int length;

    public HashTable(int length) {
        this.table= new Registros[length];
        this.length = length;
    }

    public void insert(Registros l) {
        int m = hash(l);
        this.table[m]=l;
    }

    private int hash(Registros l) {
        //int a = (int) (rand.nextInt(l.getTitle().length()) * 0.25);
        Random rand = new Random(this.length);
        int result = 1;
        String title = l.getTitle();
        double b=title.length()*0.25;
        int a=(int)b;
        for (int i = 0; i < a; i++) {
            result *= title.charAt(i);
        }
        return (result & 0x7FFFFFFF) % length ;
    }
    public Registros getPosition(int i){
        return this.table[i];
    }
}
