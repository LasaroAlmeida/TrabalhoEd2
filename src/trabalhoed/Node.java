package trabalhoed;

import java.math.BigInteger;

public class Node {

    private Registros book;
    private Node parent, left, right;
    private char color; //PODE VARIAR ENTRE B (BLACK) OU R (RED)

    public Node(Registros book) {
        this.book = book;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = 'r'; //Por conveniência, cor padrão para o nó é vermelho
    }

    public Node(Registros book, char color) { //Overload do construtor para possibilitar escolha de cor
        this(book);
        this.color = color;
    }

    public boolean compare(BigInteger one, BigInteger two) { //retornará verdadeiro caso o segundo parâmetro seja maior que o primeiro
        int aux = one.compareTo(two);
        return aux < 0;
    }

    public Node addChild(Registros book) { //Cria um novo nó e o insere na posição correta como filho do nó da classe
        Node new_node = new Node(book);
        if (compare(this.book.getId(),book.getId())) {
            this.right = new_node;
        } else {
            this.left = new_node;
        }
        new_node.setParent(this);
        return new_node;
    }

    public Node getUncle() {
        Node parent = this.parent;
        Node grandparent = parent.getParent();
        Node uncle = null;
        if (grandparent.getLeft() == parent) { //retorna o nó filho do avô que é diferente do pai
            uncle = grandparent.getRight();
        } else {
            uncle = grandparent.getLeft();
        }
        return uncle;
    }

    public void recolorFamily() {
        Node parent = this.parent;
        if (parent == null) { //sem pai não há família para recolorir, contudo, não é um caso que deveria acontecer em uma árvore rb
            return;
        }
        Node grandparent = parent.getParent();
        grandparent.setColor('r'); //avô fica vermelho
        //pai e tio ficam pretos
        if (grandparent.getLeft() != null) {
            grandparent.getLeft().setColor('b');
        }
        if (grandparent.getRight() != null) {
            grandparent.getRight().setColor('b');
        }
    }

    public void swapColor(Node n) { //Troca de cor com um outro nó n
        char aux_color = this.color;
        this.color = n.getColor();
        n.setColor(aux_color);
    }

    //GETTERS E SETTERS
    public char getColor() {
        return this.color;
    }

    public Node getParent() {
        return this.parent;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public Registros getBook() {
        return book;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public void setParent(Node p) {
        this.parent = p;
    }

    public void setLeft(Node p) {
        this.left = p;
    }

    public void setRight(Node p) {
        this.right = p;
    }

    public void setBook(Registros book) {
        this.book = book;
    }

}
