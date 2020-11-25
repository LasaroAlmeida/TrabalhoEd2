package trabalhoed;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BNode {

    private ArrayList<Registros> keys;
    private ArrayList<BNode> children;
    private int m;

    public BNode(int m) {
        this.m = m;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public boolean isFull() {
        return keys.size() == m;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public BNode getChild(int i) {
        return children.get(i);
    }

    public ArrayList<Registros> getKeyList() {
        return keys;
    }

    public boolean compare(BigInteger one, BigInteger two) { //retornará verdadeiro caso o segundo parâmetro seja maior que o primeiro
        int aux = one.compareTo(two);
        return aux < 0;
    }

    public void addChild(BNode b) {
        for (int i = 0; i < this.children.size(); i++) {
            ArrayList<Registros> keys = children.get(i).getKeyList();
            if (compare(b.getKeyList().get(b.getKeyList().size() - 1).getId()  ,  keys.get(0).getId())) { //adicionará novo filho na posição onde a chave é maior que a última chave do nó filho
                children.add(i, b);
                return;
            }
        }
        children.add(b); //caso não tenha adicionado em nenhuma posição, adiciona-se por último na lista
    }

    public void removeChild(BNode n) {
        children.remove(n);
    }

    public void insertKey(Registros val) {
        int n = keys.size();
        for (int i = 0; i < n; i++) { //busca a posição certa dentro da lista de chaves
            if (keys.get(i).getId() == val.getId()) {
                System.out.println("Esse valor já foi inserido!");
                return;
            }
            if (compare(val.getId(),keys.get(i).getId())) { //busca inserir na posição anterior à primeira chave maior que val
                keys.add(i, val);
                return;
            }
        }
        keys.add(val); //caso não encontre um maior, inserir no final

    }

    public void printNode(int id, int parent) {
        if (!this.isLeaf()) { //caso haja filhos
            for (int i = 0; i <= keys.size(); i++) { //For que percorrerá todos os filhos
                children.get(i).printNode(id + 1 + i, id); //chamar recursivamente impressão dos filhos. Id enviado identificará o relacionamento pai-filho dos nós impressos
            }
        }
        for (int i = 0; i < keys.size(); i++) { //for que percorrerá todas as chaves
            System.out.print(keys.get(i).getId() + "\t"); //impressão do nó em questão. Iniciará pelas folhas e terminará na raiz
        }
        System.out.println("\n");
       // System.out.print("(id = " + id + ", parent =" + parent + ")\n");
    }

}
