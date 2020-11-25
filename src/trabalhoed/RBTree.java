package trabalhoed;

import java.math.BigInteger;

public class RBTree {

    private Node root;
    private long comparacoes = 0;
    private long movimentacoes = 0;

    public RBTree(Registros root_value) { //inicializa a arvore com seu no raiz
        Node n = new Node(root_value, 'b');
        this.root = n;
    }

    public void printTree() {
        auxPrint(root); //Impressão em ordem
    }

    private void auxPrint(Node p) { //Recursivamente acessa 1º os valores mais à esquerda na árvore, e por último os da direita
        if (p == null) {
            return;
        }
        auxPrint(p.getLeft());
        String parent_value = p.getParent() != null ? p.getParent().getBook().getId() + "" : "null"; //Variável armazena informação sobre o pai,
        //caso ele exista
        System.out.println(p.getBook().getId() + "(" + parent_value + ", " + p.getColor() + ")");  //Além do valor próprio e do pai, imprime a cor
        auxPrint(p.getRight());
    }

    public boolean compare(BigInteger one, BigInteger two) {
        int aux = one.compareTo(two);
        comparacoes += 1;
        return aux < 0;
    }

    private Node auxSearch(Node n, BigInteger val) {
        if (n == null) {
            return null;
        }
        BigInteger current_val = n.getBook().getId();
        if (current_val == val) {
            return n;
        }
        if (compare(val, current_val)) {
            return auxSearch(n.getLeft(), val);
        } else {
            return auxSearch(n.getRight(), val);
        }
    }

    public void insert(Registros book) { //insere elemento na arvore 
        Node aux_node = root;
        boolean insertion_place_found;
        do { //percorre a arvore ate que encontre a posicao em que o elemento sera inserido
            BigInteger current_val = aux_node.getBook().getId();
            if (book.getId() == current_val) { //Essa árvore não terá valores duplicados
                System.out.println("Esse valor já existe na árvore!\n");
                return;
            }
            insertion_place_found = ((compare(current_val, book.getId())) && (aux_node.getRight() == null)) || (compare(book.getId(), current_val)
                    && (aux_node.getLeft() == null)); 
            //Variável verifica se o próximo nó na busca seria nulo, logo, o lugar para inserir o novo nó foi encontrado
            if (!insertion_place_found) { //Caso não seja momento de inserir, descer mais na árvore
                if (compare(current_val, book.getId())) {
                    aux_node = aux_node.getRight();
                } else {
                    aux_node = aux_node.getLeft();
                }
            }
        } while (!insertion_place_found); //enquanto o local de insercao nao for encontrado
        Node new_node = aux_node.addChild(book);  //insere elemento como filho e pega sua posicao
        this.comparacoes += 1;
        balanceTree(new_node);
    }

    public void balanceTree(Node new_node) { //balanceia a arvore fazendo as rotacoes necessarias
        Node parent = new_node.getParent(); //guarda o pai do no
        if (parent == null) { //se no passado for a raiz
            new_node.setColor('b'); //muda sua cor para preto
            return;
        }
        if (parent.getColor() == 'r') { //se pai do no passado for vermelho
            Node grandparent = parent.getParent(); //guarda o avo do no passado
            //Raiz sempre será preto, então grandparent nunca será nulo
            if (new_node.getUncle() == null || new_node.getUncle().getColor() == 'b') { //verifica se o tio eh null ou se eh preto
                String rot_type = ""; //string que guardara os caracteres referentes a a rotacao necessaria
                if (compare(new_node.getBook().getId(), parent.getBook().getId()) && compare(grandparent.getBook().getId(), parent.getBook().getId())) { //analisa qual tipo de rotacao sera feita
                    rot_type = "RL";
                } else if (compare(parent.getBook().getId(), new_node.getBook().getId()) && compare(parent.getBook().getId(), grandparent.getBook().getId())) {
                    rot_type = "LR";
                } else if (compare(new_node.getBook().getId(), parent.getBook().getId()) && compare(parent.getBook().getId(), grandparent.getBook().getId())) {
                    rot_type = "LL";
                } else {
                    rot_type = "RR";
                }
                fixRotation(new_node, rot_type); //chama a funcao que executa as rotacoes
            } else {
                new_node.recolorFamily(); //chama a funcao para recolorir os nos
                balanceTree(grandparent); //chama a funcao de balanceamento para o avo
            }
        }
    }

    public void fixRotation(Node n, String rot_type) { //funcao que revebe um no e uma string e executa as rotacoes com informacoes desse no
        Node parent = n.getParent(); //armazena seu pai
        Node grandparent = parent.getParent(); //armazena seu avo
        if (rot_type.equals("LL")) { //rotacao simples a direita
            rotateRight(grandparent);
            parent.swapColor(grandparent); //troca as cores do pai e do avo
            balanceTree(parent); //chama a funcao de balanceamento novamente
        } else if (rot_type.equals("RR")) { //rotacao simples a esquerda
            rotateLeft(grandparent);
            parent.swapColor(grandparent); //troca as cores do pai e do avo
            balanceTree(parent); //chama a funcao de balanceamento novamente
        } else if (rot_type.equals("LR")) { //rotacao dupla a esquerda
            rotateLeft(parent);
            fixRotation(parent, "LL");
        } else { //rotacao dupla a direita
            rotateRight(parent);
            fixRotation(parent, "RR");

        }
    }

    public void rotateLeft(Node n) { //executa rotacao a esquerda
        this.movimentacoes+=1;
        Node right = n.getRight();
        Node parent = n.getParent();
        n.setRight(right.getLeft());
        if (right.getLeft() != null) {
            right.getLeft().setParent(n);
        }
        right.setLeft(n);
        n.setParent(right);
        right.setParent(parent);
        if (parent != null) {
            if (compare(right.getBook().getId(), parent.getBook().getId())) {
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
        } else {
            root = right;
        }

    }

    public void rotateRight(Node n) { //executa rotacao a direita
        this.movimentacoes+=1;
        Node left = n.getLeft();
        Node parent = n.getParent();
        n.setLeft(left.getRight());
        if (left.getRight() != null) {
            left.getRight().setParent(n);
        }
        left.setRight(n);
        n.setParent(left);
        left.setParent(parent);
        if (parent != null) {
            if (compare(left.getBook().getId(), parent.getBook().getId())) {
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
        } else {
            root = left;
        }
    }

    public Node search(BigInteger val) {
        return auxSearch(root, val);
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public void setComparacoes() {
        this.comparacoes = 0;
    }

    public long getMovimentacoes() {
        return movimentacoes;
    }
    
}
