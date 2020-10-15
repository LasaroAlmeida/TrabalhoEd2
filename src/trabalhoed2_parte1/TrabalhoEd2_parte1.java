package trabalhoed2_parte1;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrabalhoEd2_parte1 {

    public static void main(String[] args) {
        System.out.println("Alteração do Dener KEKW");
        File input_file = new File(args[0]);
        String separa = "", junta, linha;
        List<Registros> a= new ArrayList<Registros>();
        Registros b=new Registros();
        List<Integer> n=new ArrayList<Integer>();
        int cont1 = 1, cont2 = 0, cont3 = 0, aux = 0,auxs=0;
        try {
            Scanner leitor = new Scanner(input_file);
            while (leitor.hasNext()) {
                linha = leitor.nextLine();
                aux = linha.length();
                cont1 = 1;
                cont2 = 0;
                separa = linha;
                for (int i = 0; i < 10 && cont1 < aux; i++) {
                    junta = "";
                    while (separa.charAt(cont1) == '"' || separa.charAt(cont1) == ',') {
                        if (separa.charAt(cont1) == '"' && separa.charAt(cont1 + 1) == '"') {
                            cont1++;
                            break;
                        }
                        cont1++;
                    }
                    while (separa.charAt(cont1) != '"') {
                        junta = junta + separa.charAt(cont1);
                        cont1++;
                    }
                    // System.out.println(junta);
                    switch (cont2) {
                        case 0:
                            //if(cont1<aux-1)
                            b.setAuthors(junta);
                            break;
                        case 1:
                            System.out.println(junta);
                            b.setBestsellersr(junta);
                            break;
                        case 2:
                            System.out.println(junta);
                            b.setcategories(junta);
                            break;
                        case 3:
                            System.out.println(junta);
                            b.setedition(junta);
                            break;
                        case 4:
                            System.out.println(junta);
//                            BigInteger decima=new BigInteger(junta);
//                            System.out.println(decima);
//                            System.out.println("Sua");
                            b.setId(junta);
                        default:
                        System.out.println("trabalhoed2_parte1.TrabalhoEd2_parte1.main()");
                        //System.out.println(cont2);
                    }
                    // System.out.println(cont2);
                    cont2 += 1;
                }
                a.add(b);
//                n=a.get(cont3).getAuthors();
//                for(int i=0;i<n.size();i++)
//                {
//                    System.out.println(n.get(i));
//                }
                cont3++;
                b=new Registros();
                //              System.out.println(linha);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir arquivo");
        }
//        System.out.println("trabalhoed2_parte1.TrabalhoEd2_parte1.main()");
    }

}
