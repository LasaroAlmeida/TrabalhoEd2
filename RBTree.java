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
        aux_node.addChild(val);
    }

    public Node search(int val){
        return auxSearch(root, val);
    }
}
