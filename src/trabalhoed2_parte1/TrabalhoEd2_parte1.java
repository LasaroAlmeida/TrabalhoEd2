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
        List<Registros> a = new ArrayList<Registros>();
        Registros b = new Registros();
        int cont1 = 1, cont2 = 0, cont3 = 0, aux = 0;
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
                            b.setAuthors(junta);
                            break;
                        case 1:
                            System.out.println(junta);
                          //  b.setBestsellersr(junta);
                            break;
                        case 2:
                            System.out.println(junta);
                            b.setcategories(junta);
                            break;
                        case 3:
                            System.out.println(junta);
                            b.setEdition(junta);
                            break;
                        case 4:
                            b.setId(new BigInteger(junta));
                            break;
                        case 5:
                            b.setIsbn10(junta);
                            break;
                        case 6:
                            b.setIsbn13(junta);
                            break;
                        case 7:
                            if (!junta.equals(",") && !junta.equals("")) {
                                b.setRating_avg(Double.parseDouble(junta));
                            } else {
                                b.setRating_avg(-1.0);
                            }

                            break;
                        case 8:
                            if (!junta.equals(",") && !junta.equals("")) {
                                b.setRating_count(Integer.parseInt(junta));
                            }
                            break;
                        case 9:
                            b.setTitle(junta);
                            break;

                        default:
                            System.out.println("Posicao invalida");

                    }
                    cont2 += 1;
                }
                System.out.println(b.getId());
                a.add(b);
                System.out.println(a.get(cont3).getRating_avg());
                cont3++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir arquivo");
        }
//        );
    }

}
