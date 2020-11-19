public class RBTree{

    private Node root;

    public RBTree(int root_value){
        Node n = new Node(root_value, 'b');
        this.root = n;
    }

    public void printTree(){
        auxPrint(root); //Impressão em ordem
    }

    private void auxPrint(Node p){ //Recursivamente acessa 1º os valores mais à esquerda na árvore, e por último os da direita
        if(p == null) return;
        auxPrint(p.getLeft());
        String parent_value = p.getParent() != null ? p.getParent().getValue()+"" : "null"; //Variável armazena informação sobre o pai,
                                                                                        //caso ele exista
        System.out.println(p.getValue()+"("+parent_value+", "+p.getColor()+")");  //Além do valor próprio e do pai, imprime a cor
        auxPrint(p.getRight());
    }

    private Node auxSearch(Node n, int val){
        if(n == null) return null;
        int current_val = n.getValue();
        if(current_val == val){
            return n;
        }
        if(current_val > val){
            return auxSearch(n.getLeft(), val);
        }else{
            return auxSearch(n.getRight(), val);
        }
    }

    public void insert(int val){
        Node aux_node = root;
        boolean insertion_place_found;
        do{
            int current_val = aux_node.getValue();
            if(val == current_val){ //Essa árvore não terá valores duplicados
                System.out.println("Esse valor já existe na árvore!\n");
                return;
            }
            insertion_place_found = ((val>current_val) && (aux_node.getRight() == null)) || ((val<current_val) &&
                                            (aux_node.getLeft() == null));
            //Variável verifica se o próximo nó na busca seria nulo, logo, o lugar para inserir o novo nó foi encontrado
            if(!insertion_place_found){ //Caso não seja momento de inserir, descer mais na árvore
                if(val > current_val){
                    aux_node = aux_node.getRight();
                }else{
                    aux_node = aux_node.getLeft();
                }
            }
        }while(!insertion_place_found);
        Node new_node = aux_node.addChild(val);
        balanceTree(new_node);                
    }

    public void balanceTree(Node new_node){
        Node parent = new_node.getParent();
        if(parent == null){
            new_node.setColor('b');
            return;
        }
        if(parent.getColor() == 'r'){
            Node grandparent = parent.getParent();
            //Raiz sempre será preto, então grandparent nunca será nulo
            if(new_node.getUncle() == null || new_node.getColor() == 'b'){
                String rot_type = "";
                if(parent.getValue() > new_node.getValue() && parent.getValue() > grandparent.getValue()){
                    rot_type = "RL";
                }else if(parent.getValue() < new_node.getValue() && parent.getValue() < grandparent.getValue()){
                    rot_type = "LR";
                }else if (parent.getValue() > new_node.getValue() && parent.getValue() < grandparent.getValue()){
                    rot_type = "LL";
                }else{
                    rot_type = "RR";
                }
                fixRotation(new_node, rot_type);

            }else{
                new_node.recolorFamily();
                balanceTree(grandparent);
            }
        }
    }

    public void fixRotation(Node n, String rot_type){
        Node parent = n.getParent();
        Node grandparent = parent.getParent();
        if(rot_type.equals("LL")){
            rotateRight(grandparent);
            parent.swapColor(grandparent);
            balanceTree(parent);
        }else if(rot_type.equals("RR")){
            rotateLeft(grandparent);
            parent.swapColor(grandparent);
            balanceTree(parent);
        }else if(rot_type.equals("LR")){
            rotateLeft(parent);
            fixRotation(parent, "LL");
        }else{

            rotateRight(parent);
            fixRotation(parent, "RR");

        }

    }

    public void rotateLeft(Node n){
        Node right = n.getRight();
        Node parent = n.getParent();
        n.setRight(right.getLeft());
        right.setLeft(n);
        n.setParent(right);
        right.setParent(parent);
        if(parent != null){
            if(parent.getValue() > right.getValue())
                parent.setLeft(right);
            else
                parent.setRight(right);

        }else{
            root = right;
        }
    }

    public void rotateRight(Node n){
        Node left = n.getLeft();
        Node parent = n.getParent();
        n.setLeft(left.getRight());
        left.setRight(n);
        n.setParent(left);
        left.setParent(parent);
        if(parent != null){
            if(parent.getValue() > left.getValue())
                parent.setLeft(left);
            else
                parent.setRight(left);

        }else{
            root = left;
        }
    }

    public Node search(int val){
        return auxSearch(root, val);
    }
}
