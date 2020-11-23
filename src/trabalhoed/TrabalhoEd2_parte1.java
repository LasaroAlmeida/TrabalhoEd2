package trabalhoed;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrabalhoEd2_parte1 {

    public static long[] comparacoes;
    public static long[] movimentacoes;
    public static double[] time;
    public static int index;
    public static boolean option;
    public static int number_tests = -1;
    public static int N;
    public static boolean op;
    public static FileWriter output_file;
    public static PrintWriter file;
    public static List<Integer> length = new ArrayList<>();
    public static List<Integer> length_forTree = new ArrayList<>();

    public static void main(String[] args) {

        File input_file = new File(args[0]);
        File input_file_2 = new File(args[1]);
        File authors = new File(args[2]); ///Abri o txt de autores
        String linha = "", junta; //Variáveis String. Linha receberá cada linha do arquivo lida via nextLine()
        //Junta armazenará temporariamente o valor de cada campo separado que será
        //adicionado a um objeto da classe Registros
        String auxiliar = ""; //String usada para fazer a leitura da entrada dos parâmetros inteiros
        String estoqueCSV = ""; //String irá concatenar múltiplas linhas quando um mesmo registro ocupar
        // mais de uma mesma linha no arquivo csv
        List<Registros> a = new ArrayList<>();
        Registros b = new Registros();

        try {
            output_file = new FileWriter("saida.txt");
            file = new PrintWriter(output_file);
        } catch (IOException ex) {
            Logger.getLogger(TrabalhoEd2_parte1.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nao foi possivel abrir o arquivo");
        }

        int c = 0; //Variável que irá armazenar a linha atual do arquivo que está sendo lido
        try {
            Scanner leitor_one = new Scanner(input_file_2);
            while (leitor_one.hasNext()) {
                auxiliar = leitor_one.nextLine();
                if (c == 0) {
                    number_tests = Integer.parseInt(auxiliar);
                } else if (c == number_tests + 1) {
                    N = Integer.parseInt(auxiliar);
                } else if (c <= number_tests) {
                    length.add(Integer.parseInt(auxiliar));
                } else {
                    length_forTree.add(Integer.parseInt(auxiliar));
                }
                c += 1;
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }

        c = 0;
        try {
            Scanner leitor = new Scanner(input_file);
            while (leitor.hasNext()) {
                linha = leitor.nextLine();
                linha = linha.replaceAll("(?x)[\"]{2}(?=[^{,}])", "'"); //Expressão regular onde, aspas
                //duplas consecutivas que não não precedem uma vírgula são
                //substituídas por um ', para facilitar a separação dos campos                                          
                estoqueCSV += linha;
                int num = 0;
                for (int i = 0; i < estoqueCSV.length(); i++) { //Loop que conta a quantidade de aspas
                    //duplas dentro das linhas concatenadas e armazena em uma
                    //variável num. Isso será útil para saber quando a leitura
                    //do registro terminou
                    if (estoqueCSV.charAt(i) == '"') {
                        num++;
                    }
                }
                if (num >= 20) { //Com 20 aspas duplas, 10 campos foram lidos, portanto, é hora de fazer o
                    //registro
                    String[] result = estoqueCSV.split("(?x)   "
                            + ",          "
                            + // Dividir String na virgula
                            "(?=        "
                            + // Sempre que a frente dela tiver:
                            "  (?:      "
                            + //{
                            "    [^\"]* "
                            + // 0 ou mais caracteres que nao sejam "
                            "    \"     "
                            + // 1 "
                            "    [^\"]* "
                            + // 0 ou mais caracteres que nao sejam "
                            "    \"     "
                            + // 1 "
                            "  )*       "
                            + // } repetição disso quantas vezes forem necessarias
                            "  [^\"]*   "
                            + // Novamente, 0 ou mais caracteres que nao sejam "
                            "  $        "
                            + // Ate o final da string
                            ")          "
                    );

                    int cont2 = 0; //Variável que armazena qual dos campos está sendo adicionado
                    for (String s : result) {
                        junta = s.replace("\"", ""); //Campos serão adicionados sem as aspas duplas

                        switch (cont2) {
                            case 0:

                                b.setAuthors(junta);
                                break;
                            case 1:
                                b.setBestsellersr(junta);
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
                                if (!junta.equals(",") && !junta.equals("")) { //Controle de valor não-
                                    //numérico fora da classe
                                    b.setRating_avg(Double.parseDouble(junta));
                                } else {
                                    b.setRating_avg(-1.0);
                                }
                                break;
                            case 8:
                                if (!junta.equals(",") && !junta.equals("")) { //Controle de valor não-
                                    //numérico fora da classe
                                    b.setRating_count(Integer.parseInt(junta));
                                } else {
                                    b.setRating_count(-1);
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
                    //Agora que o registro foi armazenado no objeto, variáveis registram que uma
                    //nova linha será lida
                    estoqueCSV = "";
                    c++;
                    if (c == 20000) {
                        break;
                    }
                    a.add(b);
                    b = new Registros();
                }
            }
            int selection = 1;
            while (selection != 0) {
                selection = menu();
                switch (selection) {
                    case 1:
                        analisedos_Algoritmosde_Ordenação(length, a);
                        break;
                    case 2:
                        autores_mais_Frequentes(a, authors);
                        break;
                    case 3:
                        Busca_em_Estruturas_Balanceadas(a);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcao Invalida");
                }

            }
            output_file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Secao 1////////////////////////////////////////////////////////////////////
    public static void print_out(List<Integer> length) {
        file.println("Secao 1: =================================================================================");
        System.out.println("***Os numeros de comparacoes, movimentacoes e tempo sao resultados da media de 5 testes***");
        file.println("***Os numeros de comparacoes, movimentacoes e tempo sao resultados da media de 5 testes***");
        for (int i = 0; i < number_tests; i++) {
            System.out.println("Numero de elementos: " + length.get(i));
            System.out.println("|   Algoritmo   |" + "|  Comparacoes  |" + "| Movimentacoes |" + "|        Tempo(ms) |");
            System.out.printf("%-15s", "    QuickSort:");
            System.out.printf("%16s", comparacoes[i]);
            System.out.printf("%18s", movimentacoes[i]);
            System.out.printf("%20.4f%n", time[i]);
            System.out.printf("%-15s", "    MergeSort:");
            System.out.printf("%16s", comparacoes[i + number_tests]);
            System.out.printf("%18s", movimentacoes[i + number_tests]);
            System.out.printf("%20.4f%n", time[i + number_tests]);
            System.out.println();

            file.println("Numero de elementos: " + length.get(i));
            file.println("|   Algoritmo   |" + "|  Comparacoes  |" + "| Movimentacoes |" + "|        Tempo(ms) |");
            file.printf("%-15s", "    QuickSort:");
            file.printf("%16s", comparacoes[i]);
            file.printf("%18s", movimentacoes[i]);
            file.printf("%20.4f%n", time[i]);
            file.printf("%-15s", "    MergeSort:");
            file.printf("%16s", comparacoes[i + number_tests]);
            file.printf("%18s", movimentacoes[i + number_tests]);
            file.printf("%20.4f%n", time[i + number_tests]);
            file.println();
        }
        file.println("==========================================================================================\n\n");
    }

    public static List<Registros> randomRegistro(int N, List<Registros> old_list) {
        if (N > old_list.size()) {
            System.out.println("Erro! N não pode ser maior que o número total de registros");
            return null;
        }
        Random rand = new Random(System.currentTimeMillis());
        Set<Integer> aux_set = new HashSet(); //Estrutura de Set para garantir que o mesmo registro não
        //será selecionado mais de uma vez
        List<Registros> new_list = new ArrayList<>();
        int n;
        while (aux_set.size() < N) { //apenas terminar quando o HashSet estiver cheio, isto é, quando houver
            //N registros DIFERENTES
            n = rand.nextInt(old_list.size());
            aux_set.add(n);
        }
        for (int x : aux_set) { //converte set para uma lista para retorno
            new_list.add(old_list.get(x));
        }
        return new_list;
    }

    public static boolean compare(String one, String two) {
        int aux = one.compareTo(two);
        if (option) {
            comparacoes[index] += 1; //adiciona comparação na primeira metade do array
        } else {
            comparacoes[number_tests + index] += 1; //adicionar comparação na segunda metade do array
        }
        return aux < 0;

    }

    public static void quickSort(List<Registros> new_list, int first, int last) {

        int m = (int) ((first + last) / 2); ///O elemento do meio da lista é escolhido com pivo
        Registros pivo = new_list.get(m);
        int i = first, j = last;
        do {
            while (compare(new_list.get(i).getTitle(), pivo.getTitle())) {///enquanto title da posicao i for menor que o title da posicao pivo
                i += 1;
            }
            while (compare(pivo.getTitle(), new_list.get(j).getTitle())) {///enquanto title da posicao i for maior que o title da posicao pivo
                j -= 1;
            }
            if (i <= j) {
                Collections.swap(new_list, i, j);///Faz a troca da posiccao i com a posicao j da lista
                movimentacoes[index] += 1;
                i++;
                j--;
            }
        } while (i <= j);///parte recursiva
        if (first < j) {
            quickSort(new_list, first, j);
        }
        if (i < last) {
            quickSort(new_list, i, last);
        }
    }

    public static void MergeSort(List<Registros> a, int p, int r) {
        int q;
        if (p < r - 1) {
            q = (p + r) / 2;
            MergeSort(a, p, q);
            MergeSort(a, q, r);
            marge(a, p, q, r);
        }
    }

    public static void marge(List<Registros> a, int p, int q, int r) {
        int i, j, k;
        List<Registros> aux = new ArrayList<Registros>(r);
        List<Registros> aux2 = new ArrayList<Registros>(r);
        aux2 = a;
        i = p;
        j = q;
        k = 0;
        while (i < q && j < r) //Compara valores da esquerda e direita e adiciona os menores 
        //valores entre os 2 em uma lista auxiliar
        {
            if (compare(a.get(i).getTitle(), a.get(j).getTitle())) {
                aux.add(a.get(i));
                movimentacoes[number_tests + index] += 1;
                i++;
            } else {
                aux.add(a.get(j));
                movimentacoes[number_tests + index] += 1;
                j++;
            }
            k++;
        }
        while (i < q) //Preenche valores restantes da esquerda
        {
            aux.add(a.get(i));
            i++;
            k++;
        }
        while (j < r) //Preenche valores restantes da direita
        {
            aux.add(a.get(j));
            j++;
            k++;
        }
        for (i = p; i < r; i++) { //Coloca valores da lista auxiliar na principal
            a.set(i, aux.get(i - p));
            movimentacoes[number_tests + index] += 1;
        }
    }

    public static void analisedos_Algoritmosde_Ordenação(List<Integer> length, List<Registros> a) {

        comparacoes = new long[number_tests * 2];
        movimentacoes = new long[number_tests * 2];
        time = new double[number_tests * 2];
        int i = 0;
        List<Registros> aleatoria = new ArrayList<Registros>();
        double start_time;
        double final_time;
        int cont = 0;
        while (i < number_tests) {

            aleatoria = randomRegistro(length.get(i), a);
            List<Registros> aleatoria_aux = aleatoria;
            index = i;
            option = true; // Variável option define qual algoritmo de ordenação será contabilizado 
            // para contar comparações. Cada algorimo ocupa metade de cada array, que                                
            // tem um tamanho de 2*number_tests
            start_time = System.nanoTime();
            quickSort(aleatoria, 0, aleatoria.size() - 1);
            final_time = System.nanoTime();
            time[i] += ((final_time - start_time) / 1000000.0);

            option = false;
            start_time = System.nanoTime();
            MergeSort(aleatoria_aux, 0, aleatoria_aux.size());
            final_time = System.nanoTime();
            time[i + number_tests] += ((final_time - start_time) / 1000000.0);

            aleatoria.clear();
            aleatoria_aux.clear();
            cont++;
            if (cont == 5) { //Calcular média de todas as medidas
                time[i] = time[i] / 5.0;
                comparacoes[i] /= 5;
                movimentacoes[i] /= 5;
                time[i + number_tests] /= 5.0;
                comparacoes[i + number_tests] /= 5;
                movimentacoes[i + number_tests] /= 5;
                i += 1;
                cont = 0;
            }
        }
        print_out(length);
    }

    ///Secao 2//////////////////////////////////////////////////////////////////
    public static void quickSort_id(List<Authors> new_list, int first, int last) {

        int m = (int) ((first + last) / 2); ///O elemento do meio da lista é escolhido com pivo
        Authors pivo = new_list.get(m);
        int i = first, j = last;
        do {
            while ((op) ? (new_list.get(i).getId() < pivo.getId()) : new_list.get(i).getNumber_books() > pivo.getNumber_books()) {///enquanto id da posicao i for menor que o id da posicao pivo
                i += 1;
            }
            while ((op) ? (new_list.get(j).getId() > pivo.getId()) : new_list.get(j).getNumber_books() < pivo.getNumber_books()) {///enquanto id da posicao j for maior que o id da posicao pivo
                j -= 1;
            }
            if (i <= j) {
                Collections.swap(new_list, i, j);///Faz a troca da posiccao i com a posicao j da lista
                i++;
                j--;
            }
        } while (i <= j);///parte recursiva
        if (first < j) {
            quickSort_id(new_list, first, j);
        }
        if (i < last) {
            quickSort_id(new_list, i, last);
        }
    }

    public static int menu() {///Menu de opcoes
        int selecao;
        System.out.println("MENU");
        System.out.println("----");
        System.out.println("[1] Analise dos Algoritmos de Ordenacao(Secao 1)");
        System.out.println("[2] Implementação dos Autores mais Frequentes(Secao 2)");
        System.out.println("[3] Busca em Estruturas Balanceadas(Secao 3)");
        System.out.println("[0] Sair");
        Scanner teclado = new Scanner(System.in);
        selecao = teclado.nextInt();
        return selecao;
    }

    public static void read_authors(List<Authors> authors, File ex_file) {
        String aux = "";
        String word;
        Authors a = new Authors();
        try {
            Scanner in = new Scanner(ex_file);
            aux = in.nextLine();
            while (in.hasNext()) {
                aux = in.nextLine();
                String[] line = aux.split(",");
                int j = 0;
                for (String i : line) {
                    word = i.replace("\"", "");
                    switch (j) {
                        case 0:
                            a.setId(Integer.parseInt(word));
                        case 1:
                            if (word.equals("")) {
                                a.setName("Nome nao informado");
                            } else {
                                a.setName(word);
                            }
                    }
                    j++;
                }
                authors.add(a);
                a = new Authors();
            }
        } catch (Exception e) {
        }
    }

    public static List<Registros> randomRegistro_livros(int N, List<Registros> old_list) {
        if (N > old_list.size()) {
            System.out.println("Erro! N não pode ser maior que o número total de registros");
            return null;
        }
        Random rand = new Random(System.currentTimeMillis());
        Set<String> aux_set = new HashSet<>(); //Estrutura de Set para garantir que o mesmo registro não
        //será selecionado mais de uma vez
        List<Integer> aux = new ArrayList<>();
        List<Registros> new_list = new ArrayList<>();
        int n;
        while (aux_set.size() < N) { //apenas terminar quando o HashSet estiver cheio, isto é, quando houver
            //N registros DIFERENTES
            n = rand.nextInt(old_list.size());
            int old_size = aux_set.size();
            aux_set.add(old_list.get(n).getTitle());
            if (aux_set.size() > old_size) {
                aux.add(n);
            }
        }
        for (int x : aux) { //converte set para uma lista para retorno
            new_list.add(old_list.get(x));
        }
        return new_list;
    }

    public static void autores_mais_Frequentes(List<Registros> all_books, File txt_authors) {
        List<Authors> list_authors = new ArrayList<>();
        read_authors(list_authors, txt_authors);
        op = true;
        quickSort_id(list_authors, 0, list_authors.size() - 1);
        System.out.println("N- " + N);
        HashTable_livros table_livros = new HashTable_livros(N);
        List<Registros> aleatoria = new ArrayList<>();
        aleatoria = randomRegistro_livros(N, all_books);
        if (aleatoria == null) {
            return;
        }
        for (int i = 0; i < N; i++) {
            table_livros.insert(aleatoria.get(i));
        }
        int M;
        HashTable_authors authors = new HashTable_authors(N);
        List<Integer> ids_author = new ArrayList<>();
        for (int i = 0; i < table_livros.getLength(); i++) {
            if (table_livros.getPosition(i) != null) {
                ids_author = table_livros.getPosition(i).getAuthors();
                for (int id : ids_author) {
                    if (id >= list_authors.size()) {
                        break;
                    } else {
                        Authors aux = list_authors.get(id - 1);
                        authors.insert(aux);
                    }
                }
            }
        }
        list_authors.clear();
        for (int i = 0; i < authors.getLength(); i++) {
            Authors p = authors.getPosition(i);
            if (p != null) {
                list_authors.add(p);
            }
        }
        Scanner teclado = new Scanner(System.in);
        M = 0;
        do {
            System.out.print("Numero de autores mais frequentes: ");
            M = teclado.nextInt();
            if (M > list_authors.size()) {
                System.out.println("ERRO !!! --- Numero maior que a quantidade de autores");
            }
        } while (M > list_authors.size());
        op = false;
        quickSort_id(list_authors, 0, list_authors.size() - 1);
        file.println("Secao 2: =================================================================================");
        file.println("|N. Livros:|" + "|     Id    |" + "|  Autor:     ");
        System.out.println("|N. Livros:|" + "|     Id    |" + "|  Autor:     ");
        for (int i = 0; i < M; i++) {

            Authors p = list_authors.get(i);
            System.out.printf("|%8s  |", p.getNumber_books());
            System.out.printf("|%9s  |", p.getId());
            System.out.println("|  " + p.getName());
            file.printf("|%8s  |", p.getNumber_books());
            file.printf("|%9s  |", p.getId());
            file.println("|  " + p.getName());
        }
        file.println("==========================================================================================\n\n");
    }

    public static void Busca_em_Estruturas_Balanceadas(List<Registros> livros) {
        time = new double[4];
        comparacoes = new long[4];
        movimentacoes = new long[4];
        file.println("Secao 2: =================================================================================");
        int i = 0;
        List<Registros> aleatoria = new ArrayList<Registros>();

        RBTree read;
        double start_time;
        double final_time;
        int cont = 0;

        System.out.println(number_tests);
        file.print("Arvore Vermelho-Preto");
        System.out.println("Arvore Vermelho-Preto");
        while (i < number_tests) {
            int t = length.get(i);
            if (cont == 0) {
                file.println("Numero de Elementos: " + t);
                file.println("|   Tempo(ms)   |" + "|  Comparacoes  |" + "| Movimentacoes |");
                file.println("Insercao:");
                System.out.println("Numero de Elementos: " + t);
                System.out.println("Insercao:");
                System.out.println("|   Tempo(ms)   |" + "|  Comparacoes  |" + "| Movimentacoes |");
            }
            aleatoria = randomRegistro(t, livros);
            if (aleatoria == null) {
                break;
            }
            read = new RBTree(aleatoria.get(0));
            start_time = System.nanoTime();
            for (int g = 1; g < aleatoria.size(); g++) {
                System.out.println(aleatoria.get(g).getTitle());
                read.insert(aleatoria.get(g));
            }
            final_time = System.nanoTime();
            time[0] += (final_time - start_time) / 1000000.0;
            comparacoes[0] += read.getComparacoes();
            //movimentacoes[0]+=;
            cont++;
            if (cont==5) {
                file.printf("|%14.4f |", time[0] / 5.0);
                file.printf("|%14s |", comparacoes[0] / 5);
                file.printf("|%14s |%n", movimentacoes[0] / 5);
                System.out.printf("|%14.4f |", time[0] / 5.0);
                System.out.printf("|%14s |", comparacoes[0] / 5);
                System.out.printf("|%14s |%n", movimentacoes[0] / 5);
                i += 1;
                cont=0;
            }
            aleatoria.clear();
        }

        //Arvore B
//        i = 0;
//        cont = 0;
//        BTree bt;
//        file.println("Arvore B");
//        System.out.println("Arvore B");
//        int j = 0;
//        while (j < length_forTree.size()) {
//            
//            int d = length_forTree.get(i);
//            
//            while (i < number_tests) {
//                int t = length.get(i);
//                if (cont == 0) {
//                    file.println("D= " + d);
//                    file.println("Numero de Elementos: " + t);
//                    file.println("|   Tempo(ms)   |" + "|  Comparacoes  |" + "| Movimentacoes |");
//                    file.println("Insercao:");
//                    System.out.println("Numero de Elementos: " + t);
//                    System.out.println("Insercao:");
//                    System.out.println("|   Tempo(ms)   |" + "|  Comparacoes  |" + "| Movimentacoes |");
//                }
//                aleatoria = randomRegistro(t, livros);
//                if (aleatoria == null) {
//                    break;
//                }
//                bt = new BTree(d);
//                start_time = System.nanoTime();
//                for (int g = 0; g < aleatoria.size(); g++) {
//                    bt.insert(aleatoria.get(g));
//                }
//                final_time = System.nanoTime();
//                time[2] += (final_time - start_time) / 1000000.0;
//                comparacoes[2] += bt.getComparacoes();
//                //movimentacoes[2]+=;
//                cont += 1;
//                if (cont == 5) {
//                    file.printf("|%14.4f |", time[2] / 5.0);
//                    file.printf("|%14s |", comparacoes[2] / 5);
//                    file.printf("|%14s |%n", movimentacoes[2] / 5);
//                    System.out.printf("|%14.4f |", time[2] / 5.0);
//                    System.out.printf("|%14s |", comparacoes[2] / 5);
//                    System.out.printf("|%14s |%n", movimentacoes[2] / 5);
//                    i += 1;
//                }
//                aleatoria.clear();
//            }
//            j++;
//        }
        file.println("==========================================================================================\n\n");
    }
}
