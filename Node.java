public class Node{

    private int value;
    private Node parent, left, right;
    private char color; //PODE VARIAR ENTRE B (BLACK) OU R (RED)

    public Node(int value){
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = 'r'; //Por conveniência, cor padrão para o nó é vermelho
    }

    public Node(int value, char color){ //Overload do construtor para possibilitar escolha de cor
        this(value);
        this.color = color;
    }

    public Node addChild(int value){ //Cria um novo nó e o insere na posição correta como filho do nó da classe
        Node new_node = new Node(value);
        if(value > this.value){
            this.right = new_node;
        }else{
            this.left = new_node;
        }
        new_node.setParent(this);
        return new_node;
    }

    public Node getUncle(){
        Node parent = this.parent;
        Node grandparent = parent.getParent();
        Node uncle = null;
        if(grandparent.getLeft() == parent){
            uncle = grandparent.getRight();
        }else{
            uncle = grandparent.getLeft();
        }
        return uncle;
    }

    public void recolorFamily(){
        Node parent = this.parent;
        if(parent == null) return;
        Node grandparent = parent.getParent();
        grandparent.setColor('r');
        if(grandparent.getLeft() != null) grandparent.getLeft().setColor('b');
        if(grandparent.getRight() != null) grandparent.getRight().setColor('b');
    }

    public void swapColor(Node n){ //Troca de cor com um outro nó n
        char aux_color = this.color;
        this.color = n.getColor();
        n.setColor(aux_color);
    }

    //GETTERS E SETTERS

    public char getColor(){
        return this.color;
    }

    public Node getParent(){
        return this.parent;
    }

    public Node getLeft(){
        return this.left;
    }

    public Node getRight(){
        return this.right;
    }

    public int getValue(){
        return this.value;
    }

    public void setColor(char color){
        this.color = color;
    }

    public void setParent(Node p){
        this.parent = p;
    }

    public void setLeft(Node p){
        this.left = p;
    }

    public void setRight(Node p){
        this.right = p;
    }

    public void setValue(int val){
        this.value = val;
    }
}
