package trabalhoed2_parte1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrabalhoEd2_parte1 {

    public static void main(String[] args) {
        System.out.println("Alteração do Dener KEKW");
        File input_file=new File(args[0]);
        try{
            Scanner leitor= new Scanner(input_file);
            String linha;
            while(leitor.hasNext()){
                linha=leitor.nextLine();
                System.out.println(linha);
            }
            
        }catch(FileNotFoundException e){
            System.out.println("Erro ao abrir arquivo");
        }
        System.out.println("trabalhoed2_parte1.TrabalhoEd2_parte1.main()");
    }
    
}
