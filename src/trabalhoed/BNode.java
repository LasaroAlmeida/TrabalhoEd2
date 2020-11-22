package trabalhoed;

import java.util.ArrayList;
import java.util.List;

public class BNode{

    private ArrayList<Integer> keys;
    private ArrayList<BNode> children;
    private int m;

    public BNode(int m){
        this.m = m;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public boolean isFull(){
        return keys.size() == m;
    }

    public boolean isLeaf(){
        return children.isEmpty();
    }

    public BNode getChild(int i){
        return children.get(i);
    }

    public ArrayList<Integer> getKeyList(){
        return keys;
    }

    public void addChild(BNode b){
        for(int i = 0; i < this.children.size(); i++){
            ArrayList<Integer> keys = children.get(i).getKeyList();
            if(keys.get(0) > b.getKeyList().get(b.getKeyList().size()-1)){            
                children.add(i, b);
                return;
            }
        }
        children.add(b);
    }

    public void removeChild(BNode n){
        children.remove(n);
    }

    public void insertKey(int val){
        int n = keys.size();
        for(int i = 0; i < n; i++){
            if(keys.get(i) == val){
                System.out.println("Esse valor já foi inserido!");
                return;
            }
            if(keys.get(i) > val){
                keys.add(i, val);
                return;
            }
        }
        keys.add(val);

    }


    public void printNode(int id, int parent){
        if(!this.isLeaf()){ //For que percorrerá todos os filhos
            for(int i = 0; i <= keys.size(); i++){
                children.get(i).printNode(id+1+i, id);
            }
        }
        for(int i = 0; i < keys.size(); i++){ //for que percorrerá todas as chaves
            System.out.print(keys.get(i) + "\t");
        }
        System.out.print("(id = "+id+", parent ="+parent+")\n");
    }


}
