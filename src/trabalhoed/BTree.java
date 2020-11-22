package trabalhoed;

import java.util.ArrayList;
import java.util.List;

public class BTree{

    private int m; //Grau dos nós da árvore
    private BNode root;

    public BTree(int m){
        this.m = m;
        this.root = null;
    }


    public void printTree(){
        if(root != null){
            root.printNode(0, -1);
        }
    }

    public void rootOverflow(){
        ArrayList<Integer> keyList = root.getKeyList();
        int middlePoint = (keyList.size()-1)/2;
        BNode left = new BNode(this.m);
        BNode newRoot = new BNode(this.m);
        BNode right = new BNode(this.m);
        int i;
        for(i = 0; i < keyList.size(); i++){
            if(i < middlePoint){
                left.insertKey(keyList.get(i));
                if(!root.isLeaf()) left.addChild(root.getChild(i));
            }
            if(i == middlePoint){
                newRoot.insertKey(keyList.get(i));
                if(!root.isLeaf()) left.addChild(root.getChild(i));
            }
            if(i > middlePoint){
                right.insertKey(keyList.get(i));
                if(!root.isLeaf()) right.addChild(root.getChild(i));
            }
        }
        if(!root.isLeaf()) right.addChild(root.getChild(i));
        newRoot.addChild(left);
        newRoot.addChild(right);
        root = newRoot;
    }

    public BNode getParent(BNode n){
        BNode aux = root;
        BNode parent = null;
        while(aux != n){
            parent = aux;
            ArrayList<Integer> auxKeyList = aux.getKeyList();
            for(int i = 0; i < auxKeyList.size(); i++){
                ArrayList<Integer> NKeyList = n.getKeyList();
                if(auxKeyList.get(i) > NKeyList.get(NKeyList.size()-1)){
                    aux = aux.getChild(i);
                    break;
                }else if(i == auxKeyList.size()-1){
                    aux = aux.getChild(i+1);
                }
            }
        }
        return parent;

    }

    public void normalOverflow(BNode overNode, BNode parentNode){
        ArrayList<Integer> keyList = overNode.getKeyList();
        int middlePoint = (keyList.size()-1)/2;
        BNode left = new BNode(this.m);
        BNode right = new BNode(this.m);
        int i;
        for(i = 0; i < keyList.size(); i++){
            if(i < middlePoint){
                left.insertKey(keyList.get(i));
                if(!overNode.isLeaf()) left.addChild(overNode.getChild(i));
            }
            if(i == middlePoint){
                parentNode.insertKey(keyList.get(i));
                parentNode.removeChild(overNode);
                if(!overNode.isLeaf()) left.addChild(overNode.getChild(i));
            }
            if(i > middlePoint){
                right.insertKey(keyList.get(i));
                if(!overNode.isLeaf()) right.addChild(overNode.getChild(i));
            }
        }
        if(!overNode.isLeaf()) right.addChild(overNode.getChild(i));
        parentNode.addChild(left);
        parentNode.addChild(right);
        if(parentNode.isFull()){
            if(parentNode == root){
                System.out.println("overflow chegou na raiz oloco meu");
                rootOverflow();
            }else{
                normalOverflow(parentNode, getParent(parentNode));
            }
        }
    }

    private BNode auxSearch(BNode n, int val){
        for(int i : n.getKeyList()){
            if(i == val) return n;
        }
        if(n.isLeaf()) return null;
        ArrayList<Integer> keyList = n.getKeyList();
        int i;
        for(i = 0; i < keyList.size(); i++){
            if(keyList.get(i) > val){
                return auxSearch(n.getChild(i), val);
            }

        }
        return auxSearch(n.getChild(i), val);
    }

    public BNode search(int val){
        return auxSearch(root, val);
    }

    public void insert(int val){
        if(root == null){
            root = new BNode(this.m);
            root.insertKey(val);
        }else{
            boolean finished = false;
            BNode aux = root;
            BNode parentNode = null;
            while(!finished){
                if(aux.isLeaf()){
                    aux.insertKey(val);
                    if(aux.isFull()){
                        if(aux == root){
                            rootOverflow();
                        }else{
                            normalOverflow(aux, parentNode);
                        }
                    }
                    finished = true;
                }else{
                    List<Integer> keyList = aux.getKeyList();
                    for(int i = 0; i < keyList.size(); i++){
                        if(keyList.get(i) > val){
                            parentNode = aux;
                            aux = aux.getChild(i);
                            break;
                        }else if(i == keyList.size() - 1){ //Não houve key maior que a nova no nó, inserir no último filho
                            parentNode = aux;
                            aux = aux.getChild(i+1);
                        }
                    }
                }
            }
        }
    }


}
