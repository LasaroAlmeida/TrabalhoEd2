package trabalhoed;

import java.util.Random;

public class HashTable {

    Registros[] table;
    int length;

    public HashTable(int length) {
        this.length = encontra_primo_menor(length * 2);
        this.table = new Registros[this.length];
        for (int i = 0; i < this.length; i++) {
            this.table[i] = null;
        }
    }

    public void insert(Registros l) {
        int h1 = hash_H1(l);
        int h2 = hash_H2(l);
        int i = 0;
        int h = double_hash(h1, h2, i);
        System.out.println("posicao " + h);
        while (i < this.length) {

            if (this.table[h] == null) {
                this.table[h] = l;
                return;
            }
            else if(this.table[h].getTitle().equals(l.getTitle())) {
                return;
            }
            i += 1;
            h = double_hash(h1, h2, i);
        }
    }

    private int hash_H1(Registros l) {
        int result = 1;
        String title = l.getTitle();
        double b = title.length() * 0.30;
        int a = (int) b;
        for (int i = 0; i < a; i++) {
            result *= title.charAt(i);
        }
        return (result & 0x7FFFFFFF) % this.length;
    }

    private int double_hash(int h1, int h2, int i) {
        return (h1 + i * h2) % this.length;
    }

    private int hash_H2(Registros l) {
        String title = l.getTitle();
        int m = encontra_primo_menor(this.length);
        double b = title.length() * 0.7;
        int c = (int) b;
        int result = 1;
        for (; c < title.length(); c++) {
            result *= title.charAt(c);
        }
        return 3 + (result & 0x7FFFFFFF) % m;
    }

    public Registros getPosition(int i) {
        return this.table[i];
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
