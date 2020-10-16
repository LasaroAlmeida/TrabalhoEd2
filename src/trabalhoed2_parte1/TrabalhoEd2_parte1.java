package trabalhoed2_parte1;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

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
                    switch (cont2) {
                        case 0:
                            b.setAuthors(junta);
                            break;
                        case 1:
                            //  b.setBestsellersr(junta);
                            break;
                        case 2:
                            b.setcategories(junta);
                            break;
                        case 3:
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
                a.add(b);
                b = new Registros();
                //System.out.println(linha);
                //System.out.println(a.get(cont3).getRating_avg());
                cont3++;
            }
            System.out.println("IMPRIMINDO REGISTRO ALEATORIO");
            List<Registros> list = randomRegistro(1000, a);

            for (Registros r : list) {
                System.out.println(r.getId());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir arquivo");
        }
//        );
    }

    public static List<Registros> randomRegistro(int N, List<Registros> old_list) {
        if (N > old_list.size()) {
            System.out.println("Erro! N não pode ser maior que o número total de registros");
            return null;
        }
        Random rand = new Random(System.currentTimeMillis());
        Set<Integer> aux_set = new HashSet();
        List<Registros> new_list = new ArrayList<>();
        int n;
        while (aux_set.size() < N) {
            n = rand.nextInt(old_list.size());
            aux_set.add(n);
        }
        for (int x : aux_set) {
            new_list.add(old_list.get(x));
        }
        return new_list;
    }
}
