package trabalhoed;

import java.math.BigInteger;

public class RBTree {

    private Node root;
    private long comparacoes = 0;
    private long movimentacoes = 0;

    public RBTree(Registros root_value) {
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

    public boolean compare(BigInteger one, BigInteger two) { //retornará verdadeiro caso o segundo parâmetro seja maior que o primeiro
        int aux = one.compareTo(two);
        comparacoes += 1;
        return aux < 0;
    }

    private Node auxSearch(Node n, BigInteger val) {
        if (n == null) { //Caso o ponteiro n alcance nulo, o nó não foi encontrado e retornará nulo para a pilha da busca
            return null;
        }
        BigInteger current_val = n.getBook().getId();
        if (current_val == val) { //Caso o id seja igual, retornará o nó encontrado para a pilha da busca
            return n;
        }
        //Como o nó não foi encontrado ainda, função auxSearch é chamada de novo para o nó filho na devida direção
        if (compare(val, current_val)) {
            return auxSearch(n.getLeft(), val);
        } else {
            return auxSearch(n.getRight(), val);
        }
    }

    public void insert(Registros book) {
        Node aux_node = root;
        boolean insertion_place_found;
        do {
            BigInteger current_val = aux_node.getBook().getId();
            if (book.getId() == current_val) { //Essa árvore não terá valores duplicados
                System.out.println("Esse valor já existe na árvore!\n");
                return;
            }
            //Variável verifica se o próximo nó na busca seria nulo, logo, o lugar para inserir o novo nó teria sido encontrado
            insertion_place_found = ((compare(current_val, book.getId())) && (aux_node.getRight() == null)) || (compare(book.getId(), current_val)
                    && (aux_node.getLeft() == null));
            if (!insertion_place_found) { //Caso não seja momento de inserir, descer mais na árvore, na devida direção
                if (compare(current_val, book.getId())) {
                    aux_node = aux_node.getRight();
                } else {
                    aux_node = aux_node.getLeft();
                }
            }
        } while (!insertion_place_found); //repetirá a busca até que o ponteiro aux_node tenha o lugar de inserção do novo nó armazenado
        Node new_node = aux_node.addChild(book);
        this.comparacoes += 1;
        balanceTree(new_node); //verifica se a árvore precisa ser balanceada em torno da inserção de um novo nó
    }

    public void balanceTree(Node new_node) {
        Node parent = new_node.getParent();
        if (parent == null) { //Caso de nó raiz, apenas recolore para preto
            new_node.setColor('b');
            return;
        }
        if (parent.getColor() == 'r') { //Nó vermelho pai de nó vermelho, propriedades da árvore rubro negra estão sendo quebradas
            Node grandparent = parent.getParent(); //Lembrando que a raiz sempre será preta, então grandparent nunca será nulo nesse escopo visto que todo nó não raiz tem pai
            if (new_node.getUncle() == null || new_node.getUncle().getColor() == 'b') { //Tio do nó inserido é preto
                String rot_type = ""; //Variável controla qual tipo de rotação será feita
                if (compare(new_node.getBook().getId(), parent.getBook().getId()) && compare(grandparent.getBook().getId(), parent.getBook().getId())) {
                    rot_type = "RL"; //Right-Left, pai é maior que filho e avô
                } else if (compare(parent.getBook().getId(), new_node.getBook().getId()) && compare(parent.getBook().getId(), grandparent.getBook().getId())) {
                    rot_type = "LR"; //Left-Right, pai é menor que filho e avô
                } else if (compare(new_node.getBook().getId(), parent.getBook().getId()) && compare(parent.getBook().getId(), grandparent.getBook().getId())) {
                    rot_type = "LL"; //Left-left, filho é menor que pai, que é menor que o avô
                } else {
                    rot_type = "RR"; //Right-Right, filho é maior que pai, que é maior que o avô
                }
                fixRotation(new_node, rot_type);
            } else {
                new_node.recolorFamily(); //Para tio vermelho, a árvore pode ser ajustada recolorindo alguns nós
                balanceTree(grandparent); //Checa se a árvore não ficou 'desbalanceada' em torno do avô que mudou de cor
            }
        }
    }

    public void fixRotation(Node n, String rot_type) {
        Node parent = n.getParent();
        Node grandparent = parent.getParent();
        if (rot_type.equals("LL")) {
            rotateRight(grandparent); //rotação simples do avô para a direita
            parent.swapColor(grandparent); //pai e avô trocam de cor entre eles
            balanceTree(parent); //checa se a árvore precisa ser balanceada de novo
        } else if (rot_type.equals("RR")) {
            rotateLeft(grandparent); //rotação simples do avô para a esquerda
            parent.swapColor(grandparent); //pai e avô trocam de cor entre eles
            balanceTree(parent); //checa se a árvore precisa ser balanceada de novo
        } else if (rot_type.equals("LR")) {
            rotateLeft(parent); //Rotação dupla, após rotacionar o pai para a esquerda, entra em um caso Left-Left
            fixRotation(parent, "LL");
        } else {
            rotateRight(parent); //Rotação dupla, após rotacionar o pai para a direita, entra em um caso Right-Right
            fixRotation(parent, "RR");

        }
    }

    public void rotateLeft(Node n) {
        this.movimentacoes+=1;
        Node right = n.getRight();
        Node parent = n.getParent();
        n.setRight(right.getLeft()); //Sub-árvore filha do nó filho se torna filho do nó rotacionado
        if (right.getLeft() != null) { //Se essa subárvore não for nula, variável de parent deve ser consertada
            right.getLeft().setParent(n);
        }
        right.setLeft(n); //Nó filho do rotacionado passa a ser pai dele
        n.setParent(right); //Consequentemente, o nó rotacionado passa a ter o filho como pai
        right.setParent(parent); //Pai do nó rotacionado passa a ser pai do ex-filho
        if (parent != null) { //Caso o nó rotacionado não seja raiz
            if (compare(right.getBook().getId(), parent.getBook().getId())) { //Pai passa a ser filho do nó rotacioando
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
        } else {
            root = right; //Caso contrário, o ex-filho passa a ser a nova raiz da árvore
        }

    }


    public void rotateRight(Node n) {
        this.movimentacoes+=1;
        Node left = n.getLeft();
        Node parent = n.getParent();
        n.setLeft(left.getRight()); //Sub-árvore filha do nó filho se torna filho do nó rotacionado
        if (left.getRight() != null) { //Se essa subárvore não for nula, variável de parent deve ser consertada
            left.getRight().setParent(n);
        }
        left.setRight(n); //Nó filho do rotacionado passa a ser pai dele
        n.setParent(left); //Consequentemente, o nó rotacionado passa a ter o filho como pai
        left.setParent(parent); //Pai do nó rotacionado passa a ser pai do ex-filho
        if (parent != null) { //Caso o nó rotacionado não seja raiz
            if (compare(left.getBook().getId(), parent.getBook().getId())) { //Pai passa a ser filho do nó rotacioando
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
        } else {
            root = left; //Caso contrário, o ex-filho passa a ser a nova raiz da árvore
        }
    }

    public Node search(BigInteger val) {
        return auxSearch(root, val); //Chama função recursiva de busca
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
