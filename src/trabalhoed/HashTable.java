package trabalhoed;

import java.math.BigInteger;
import java.util.Random;

public class HashTable {

    private Registros[] table;
    private int length;
    private int m;
    private long colisao = 0;

    public HashTable(int length) {
        this.length = encontra_primo_menor(length * 2);
        this.m = encontra_primo_menor(this.length);
        this.table = new Registros[this.length];
        for (int i = 0; i < this.length; i++) {
            this.table[i] = null;
        }
    }

    public void insert(Registros l) {
        int h1 = (int) hash_H1(l);
        int h2 = hash_H2(l);
        int i = 0;
        int h = double_hash(h1, h2, i);
//        System.out.println("h1- " + h1 + " h2- " + h2 + "h- " + h + "titulo: " + l.getTitle());
        while (i < this.length) {

            if (this.table[h] == null) {
                this.table[h] = l;
                return;
            } else if (this.table[h].getTitle().equals(l.getTitle())) {
//                System.out.println("elemento repetido " + l.getTitle());
                return;
            }

            i += 1;
            this.colisao += 1;
            h = double_hash(h1, h2, i);
        }
    }

    public long getColisao() {
        return colisao;
    }

    private int hash_H1(Registros l) {
        long result = 0;
        String title = l.getTitle();
        double b = title.length() * 0.4;
        int a = (int) b;
        for (int i = 0; i < a; i++) {
            if (i + 1 == a) {
                if (title.charAt(i) % 2 == 1) {
                    result *= (title.charAt(i)+2);
                    result += 1;
                } else {
                    result *=title.charAt(i);
                }

            } else {
                result += title.charAt(i);
            }
        }
        return (int) (result) % this.length;
    }

    private int double_hash(int h1, int h2, int i) {
        return (h1 + i * h2) % this.length;
    }

    private int hash_H2(Registros l) {
        String title = l.getTitle();
        double b = title.length() * 0.6;
        int c = (int) b;
        long result = 0;
        for (; c < title.length(); c++) {
            if (c + 1 == title.length()) {
                result *= (int)title.charAt(c)*3.25;
            } else {
                result += title.charAt(c);
            }
        }
        return 3 + (int) (result) % m;
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
