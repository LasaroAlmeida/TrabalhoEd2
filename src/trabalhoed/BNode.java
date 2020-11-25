package trabalhoed;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BNode {

    private ArrayList<Registros> keys; //lista nos nos da arvore B
    private ArrayList<BNode> children; //lista de filhos da lista de nos -
    private int m;

    public BNode(int m) { //cria a arvore com base na ordem
        this.m = m;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public boolean isFull() { //verifica se lista de nos esta cheia
        return keys.size() == m;
    }

    public boolean isLeaf() { //verifica se o no eh folha
        return children.isEmpty();
    }

    public BNode getChild(int i) { //retorna o filho de um indice da lista de do no
        return children.get(i);
    }

    public ArrayList<Registros> getKeyList() {
        return keys;
    }
    
    public boolean compare(BigInteger one, BigInteger two) {
        int aux = one.compareTo(two);
        return aux < 0;
    }
    
    public void addChild(BNode b) { //adiciona filho em um no
        for (int i = 0; i < this.children.size(); i++) {
            ArrayList<Registros> keys = children.get(i).getKeyList();
            if (compare(b.getKeyList().get(b.getKeyList().size() - 1).getId()  ,  keys.get(0).getId())) {
                children.add(i, b);
                return;
            }
        }
        children.add(b);
    }

    public void removeChild(BNode n) { //remove o no filho do no passado
        children.remove(n);
    }

    public void insertKey(Registros val) { //insere um id na lista do no
        int n = keys.size();
        for (int i = 0; i < n; i++) {
            if (keys.get(i).getId() == val.getId()) {
                System.out.println("Esse valor já foi inserido!");
                return;
            }
            if (compare(val.getId(),keys.get(i).getId())) {
                keys.add(i, val);
                return;
            }
        }
        keys.add(val);

    }

    public void printNode(int id, int parent) {
        if (!this.isLeaf()) { //For que percorrerá todos os filhos
            for (int i = 0; i <= keys.size(); i++) {
                children.get(i).printNode(id + 1 + i, id);
            }
        }
        for (int i = 0; i < keys.size(); i++) { //for que percorrerá todas as chaves
            System.out.print(keys.get(i).getId() + "\t");
        }
        System.out.println("\n");
       // System.out.print("(id = " + id + ", parent =" + parent + ")\n");
    }

}
