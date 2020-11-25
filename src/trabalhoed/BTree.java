package trabalhoed;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BTree {

    private int m; //Grau dos nós da árvore
    private BNode root;
    private long comparacoes;
    private long movimentacoes;

    public BTree(int m) { //construtor arvore B
        this.m = (m*2)+1; //calcula o grau com base no numero passado como parametro
        this.root = null;
        this.comparacoes = 0;
        this.movimentacoes=0;
    }

    public void printTree() {
        if (root != null) {
            root.printNode(0, -1);
        }
    }

    public void rootOverflow() { //executa a cisao quando ocorre na raiz
        ArrayList<Registros> keyList = root.getKeyList();
        int middlePoint = (keyList.size() - 1) / 2; //pega o valor central
        BNode left = new BNode(this.m); //cria 3 nos para armazenar os elementos oriundos da cisao
        BNode newRoot = new BNode(this.m);
        BNode right = new BNode(this.m);
        int i;
        for (i = 0; i < keyList.size(); i++) { //adiciona os elementos certos nos 3 nos criados
            if (i < middlePoint) {
                left.insertKey(keyList.get(i));
                if (!root.isLeaf()) {
                    left.addChild(root.getChild(i));
                    this.comparacoes += 1;
                }
            }
            if (i == middlePoint) {
                newRoot.insertKey(keyList.get(i));
                if (!root.isLeaf()) {
                    left.addChild(root.getChild(i));
                    this.comparacoes += 1;
                }
            }
            if (i > middlePoint) {
                right.insertKey(keyList.get(i));
                if (!root.isLeaf()) {
                    right.addChild(root.getChild(i));
                    this.comparacoes += 1;
                }
            }
        }
        if (!root.isLeaf()) {
            right.addChild(root.getChild(i));
            this.comparacoes += 1;
        }
        newRoot.addChild(left); //seta o no direito e esquerdo da nova raiz
        newRoot.addChild(right);
        this.comparacoes += 2;
        root = newRoot; //muda a raiz da arvore
        this.movimentacoes+=1;
        
    }

    public BNode getParent(BNode n) { //retorna o pai de um no
        BNode aux = root;
        BNode parent = null;
        while (aux != n) {
            parent = aux; //percorre a arvore B sempre guardando o ultimo no pelo qual passou antes de descer
            ArrayList<Registros> auxKeyList = aux.getKeyList();
            for (int i = 0; i < auxKeyList.size(); i++) {
                ArrayList<Registros> NKeyList = n.getKeyList();
                if (compare(NKeyList.get(NKeyList.size() - 1).getId(), auxKeyList.get(i).getId())) {
                    aux = aux.getChild(i);
                    break;
                } else if (i == auxKeyList.size() - 1) {
                    aux = aux.getChild(i + 1);
                }
            }
        }
        return parent;

    }

    public void normalOverflow(BNode overNode, BNode parentNode) { //cisao de um no que nao eh a raiz
        ArrayList<Registros> keyList = overNode.getKeyList();
        int middlePoint = (keyList.size() - 1) / 2;
        BNode left = new BNode(this.m); //cria os 2 novos nos criados pela cisao
        BNode right = new BNode(this.m);
        int i;
        for (i = 0; i < keyList.size(); i++) { //adiciona os elementos certos nos 2 nos criados e o central no pai do no cisionado
            if (i < middlePoint) {
                left.insertKey(keyList.get(i));
                if (!overNode.isLeaf()) {
                    left.addChild(overNode.getChild(i));
                    comparacoes += 1;
                }
            }
            if (i == middlePoint) {
                parentNode.insertKey(keyList.get(i));
                parentNode.removeChild(overNode);
                if (!overNode.isLeaf()) {
                    left.addChild(overNode.getChild(i));
                    this.comparacoes += 1;
                }
            }
            if (i > middlePoint) {
                right.insertKey(keyList.get(i));
                if (!overNode.isLeaf()) {
                    right.addChild(overNode.getChild(i));
                    this.comparacoes += 1;
                }
            }
        }
        if (!overNode.isLeaf()) {
            right.addChild(overNode.getChild(i));
            this.comparacoes += 1;
        }
        parentNode.addChild(left);  //seta o no direito e esquerdo do no central agora pertencente a a chave pai
        parentNode.addChild(right);
        this.comparacoes += 2;
        if (parentNode.isFull()) { //verifica se ha necessidade de cisao no pai
            if (parentNode == root) { //verifica se o pai eh raiz
                rootOverflow(); //cisao do no pai, um no raiz
            } else {
                normalOverflow(parentNode, getParent(parentNode)); //cisao do no pai, um no nao raiz
            }
        }
        this.movimentacoes+=1;
    }

    private BNode auxSearch(BNode n, Registros val) { //auxilia na busca de elementos na arvore
        for (Registros i : n.getKeyList()) {
            if (i.getId().equals(val.getId())) {
                this.comparacoes += 1;
                return n;
            }
        }
        if (n.isLeaf()) {
            return null;
        }
        ArrayList<Registros> keyList = n.getKeyList();
        int i;
        for (i = 0; i < keyList.size(); i++) {
            if (compare(val.getId(), keyList.get(i).getId())) {
                return auxSearch(n.getChild(i), val);
            }

        }
        return auxSearch(n.getChild(i), val);
    }

    public BNode search(Registros val) { //executa a busca de um elemento
        return auxSearch(root, val);
    }

    public void insert(Registros val) { //insere elemento na arvore B
        if (root == null) { //verifica se raiz nao eh null, se for a arvore ainda nao foi criada e o elemento passado sera o primeiro
            root = new BNode(this.m);
            root.insertKey(val);
        } else {
            boolean finished = false;
            BNode aux = root;
            BNode parentNode = null;
            while (!finished) {
                if (aux.isLeaf()) { //percorre a arvore ate que um no folha seha encontrado
                    aux.insertKey(val); //insere elemento na lista do no
                    if (aux.isFull()) { //verifica se sera necessario fazer a cisao do no e se ela eh no nó raiz ou nao
                        if (aux == root) {
                            rootOverflow();
                            this.comparacoes += 1;
                        } else {
                            normalOverflow(aux, parentNode);
                            this.comparacoes += 1;
                        }
                    }
                    finished = true; //elemento inserido, o while para na proxima iteracao
                } else { //percorre a arvore B a procura do local de insercao
                    List<Registros> keyList = aux.getKeyList(); 
                    for (int i = 0; i < keyList.size(); i++) {
                        if (compare(val.getId(), keyList.get(i).getId())) {
                            parentNode = aux;
                            aux = aux.getChild(i);
                            break;
                        } else if (i == keyList.size() - 1) { //Não houve key maior que a nova no nó, inserir no último filho
                            parentNode = aux;
                            aux = aux.getChild(i + 1);
                        }
                    }
                }
            }
        }
    }

    public boolean compare(BigInteger one, BigInteger two) {
        int aux = one.compareTo(two);
        comparacoes += 1;
        return aux < 0;
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public void setComparacoes() {
        this.comparacoes =0;
    }

    public long getMovimentacoes() {
        return movimentacoes;
    }
}
