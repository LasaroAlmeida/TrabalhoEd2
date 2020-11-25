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
            root.printNode(0, -1); //Chama função recursiva para imprimir nó iniciando pela raiz, 0 é o identificador do nó raiz e -1 uma flag de ter um pai nulo
        }
    }

    public void rootOverflow() { //executa a cisão quando ocorre na raiz
        ArrayList<Registros> keyList = root.getKeyList();
        int middlePoint = (keyList.size() - 1) / 2; //elemento que representa a mediana das chaves
        BNode left = new BNode(this.m); //nó que será a parte da esquerda do nó a ser partido
        BNode newRoot = new BNode(this.m); //nó que será a nova raiz da árvore
        BNode right = new BNode(this.m); //nó que será a parte da direita do nó a ser partido
        int i;
        for (i = 0; i < keyList.size(); i++) {
            if (i < middlePoint) { //chave que está antes da mediana
                left.insertKey(keyList.get(i)); //insere a chave no nó da esquerda
                if (!root.isLeaf()) { //caso a raiz não seja o único nó da árvore, os filhos devem ser passados para o novo nó
                    left.addChild(root.getChild(i));
                    this.comparacoes += 1;
                }
            }
            if (i == middlePoint) { //chave da mediana
                newRoot.insertKey(keyList.get(i)); //insere a mediana na nova raiz
                if (!root.isLeaf()) { //caso a raiz não seja o único nó da árvore, os filhos devem ser passados para o novo nó
                    left.addChild(root.getChild(i));
                    this.comparacoes += 1;
                }
            }
            if (i > middlePoint) { //chave que está depois da mediana
                right.insertKey(keyList.get(i)); //insere a chave no nó da direita
                if (!root.isLeaf()) {  //caso a raiz não seja o único nó da árvore, os filhos devem ser passados para o novo nó
                    right.addChild(root.getChild(i));
                    this.comparacoes += 1;
                }
            }
        }
        if (!root.isLeaf()) { //para um nó com n chaves, há n+1 filhos, então esse bloco deve ser executado mais uma vez para o último filho, caso ele exista
            right.addChild(root.getChild(i));
            this.comparacoes += 1;
            //nesse caso não há chave para inserir, apenas o filho
        }
        //conecta os nós da esquerda e da direita na nova raiz
        newRoot.addChild(left);
        newRoot.addChild(right);
        this.comparacoes += 2; //para cada inserção de nó filho, uma nova comparação é feita
        root = newRoot; //Muda a raiz da árvore
        this.movimentacoes+=1;

    }

    public BNode getParent(BNode n) { //n deve ser um nó que existe na árvore
        BNode aux = root;
        BNode parent = null;
        while (aux != n) { //ponteiro auxiliar busca o nó n passado como parâmetro
            parent = aux; //como a cada iteração o aux "descerá" para um nó filho, o parent receberá o valor de aux antes disso
            ArrayList<Registros> auxKeyList = aux.getKeyList();
            for (int i = 0; i < auxKeyList.size(); i++) { //percorre todas as chaves do nó para encontrar o filho certo
                ArrayList<Registros> NKeyList = n.getKeyList();
                if (compare(NKeyList.get(NKeyList.size() - 1).getId(), auxKeyList.get(i).getId())) { //se a última chave do nó N for menor que a percorrida, descer para o filho correspondente
                    aux = aux.getChild(i);
                    break;
                } else if (i == auxKeyList.size() - 1) { // caso nenhum candidato para filho tenha passado, o ponteiro segue para o último filho do nó
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
            if (i == middlePoint) { //chave é a mediana
                parentNode.insertKey(keyList.get(i)); //insere a chave no nó pai
                parentNode.removeChild(overNode);
                if (!overNode.isLeaf()) {
                    left.addChild(overNode.getChild(i));
                    this.comparacoes += 1;
                }
            }
            if (i > middlePoint) { //chave depois da mediana
                right.insertKey(keyList.get(i)); //insere a chave no nó da direita
                if (!overNode.isLeaf()) { //caso o nó partido não seja o folha, os filhos devem ser passados para o novo nó
                    right.addChild(overNode.getChild(i));
                    this.comparacoes += 1;
                }
            }
        }
        if (!overNode.isLeaf()) { //para um nó com n chaves, há n+1 filhos, então esse bloco deve ser executado mais uma vez para o último filho, caso ele exista
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

    private BNode auxSearch(BNode n, Registros val) {
        for (Registros i : n.getKeyList()) { //percorre a lista de chaves do nó
            if (i.getId().equals(val.getId())) { //caso encontre a chave, retornar o nó vigente
                this.comparacoes += 1;
                return n;
            }
        }
        if (n.isLeaf()) { //caso o nó seja folha e não tenha sido achada a chave, retornar nulo para que não há a chave na árvore
            return null;
        }
        ArrayList<Registros> keyList = n.getKeyList();
        int i;
        for (i = 0; i < keyList.size(); i++) { //busca o filho certo para continuar a busca recursiva na arvore
            if (compare(val.getId(), keyList.get(i).getId())) {
                return auxSearch(n.getChild(i), val);
            }

        }
        return auxSearch(n.getChild(i), val); //caso o código chegue aqui, a busca não continuou em outros filhos, logo deve continuar no último
    }

    public BNode search(Registros val) { //executa a busca de um elemento
        return auxSearch(root, val);
    }

    public void insert(Registros val) {
        if (root == null) { //para árvore vazia, criar raiz
            root = new BNode(this.m);
            root.insertKey(val);
        } else {
            boolean finished = false;
            BNode aux = root;
            BNode parentNode = null;
            while (!finished) { //loop busca o lugar certo para inserir a nova chave
                if (aux.isLeaf()) { //caso o nó aux seja folha, inserir a chave nele
                    aux.insertKey(val);
                    if (aux.isFull()) { //caso o aux fique lotado, chamar a devida função de overflow
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
                        if (compare(val.getId(), keyList.get(i).getId())) { //ir para o filho no primeiro caso de chave maior do que a que se tenta inserir
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

    public boolean compare(BigInteger one, BigInteger two) { //retornará verdadeiro caso o segundo parâmetro seja maior que o primeiro
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
