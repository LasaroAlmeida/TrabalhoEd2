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

public class TrabalhoEd2 {

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
        File authors = new File(args[2]); ///Abre o txt de autores
        String linha = "", junta; //Variáveis String. Linha receberá cada linha do arquivo lida via nextLine()
        //Junta armazenará temporariamente o valor de cada campo separado que será
        //adicionado a um objeto da classe Registros
        String auxiliar = ""; //String usada para fazer a leitura da entrada dos parâmetros inteiros
        String estoqueCSV = ""; //String irá concatenar múltiplas linhas quando um mesmo registro ocupar
        // mais de uma mesma linha no arquivo csv
        List<Registros> a = new ArrayList<>();
        Registros b = new Registros();

        try {
            output_file = new FileWriter("saida.txt"); ///Cria o arquivo de saida
            file = new PrintWriter(output_file); ///Cria o arquivo para a escrita
        } catch (IOException ex) {
            Logger.getLogger(TrabalhoEd2.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nao foi possivel abrir o arquivo");
        }

        int c = 0; //Variável que irá armazenar a linha atual do arquivo que está sendo lido
        try {
            Scanner leitor_one = new Scanner(input_file_2);
            while (leitor_one.hasNext()) {
                auxiliar = leitor_one.nextLine(); ///Le linha do arquivo
                if (c == 0) { ///Se for a primeira linha
                    number_tests = Integer.parseInt(auxiliar);  ///Guarda o numero de testes a serem lidos
                } else if (c == number_tests + 1) { ///Depois que ja foram lidos N numeros de testes, le numero de livros a serem adicionados na hash
                    N = Integer.parseInt(auxiliar);
                } else if (c <= number_tests) {
                    length.add(Integer.parseInt(auxiliar)); ///N. Numero de Valores a serem inseridos nas arvores e nos algoritmos de ordenacao
                } else {
                    length_forTree.add(Integer.parseInt(auxiliar)); ///Tamanhos de d, minimo de chaves em cada no, da arvore B
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
                    a.add(b);
                    b = new Registros();
                }
            }

            int selection = 1;
            ///Menu para escolha da secao
            while (selection != 0) {
                selection = menu();
                switch (selection) {
                    case 1:
                        analisedos_Algoritmosde_Ordenação(length, a);///Secao 1
                        break;
                    case 2:
                        autores_mais_Frequentes(a, authors);///Secao 2
                        break;
                    case 3:
                        Busca_em_Estruturas_Balanceadas(a); ///Secao 3
                        break;
                    case 0:///Encerra o programa ///O programa so ira gerar o txt corretamente se o 0(zero) for escolhido para encerrar o programa
                        break;
                    default:
                        System.out.println("Opcao Invalida");
                }

            }
            output_file.close();///Fecha o arquivo de saida

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Secao 1////////////////////////////////////////////////////////////////////
    public static void print_out(List<Integer> length) {///Grava os dados no txt
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

    public static void analisedos_Algoritmosde_Ordenação(List<Integer> length, List<Registros> a) {///Secao 1

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
            time[i] += ((final_time - start_time) / 1000000.0);///Transforma o tempo em milisegundos

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
        print_out(length);///escreve saida
    }

    ///Secao 2//////////////////////////////////////////////////////////////////
    public static void quickSort_id(List<Authors> new_list, int first, int last) {

        int m = (int) ((first + last) / 2); ///O elemento do meio da lista é escolhido com pivo
        Authors pivo = new_list.get(m);
        int i = first, j = last;
        do {       ///Op serve para escolher se os autores serao ordenados em ordem crecente de id ou de ordem decrescente de numero de livros
            while ((op) ? (new_list.get(i).getId() < pivo.getId()) : new_list.get(i).getNumber_books() > pivo.getNumber_books()) {
                i += 1;
            }
            while ((op) ? (new_list.get(j).getId() > pivo.getId()) : new_list.get(j).getNumber_books() < pivo.getNumber_books()) {
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
        selecao = teclado.nextInt();///Le a opcao escolhida
        return selecao;
    }

    public static void read_authors(List<Authors> authors, File ex_file) {///Funcao que le o txt de autores
        String aux = "";
        String word;
        Authors a = new Authors();
        try {
            Scanner in = new Scanner(ex_file);
            aux = in.nextLine();///le a linha primeira linha e a ignora. Nela esta a informacao do que representa cada uma das duas colunas
            while (in.hasNext()) {
                aux = in.nextLine();///le a linha
                String[] line = aux.split(",");///separa a linha nas virgulas
                int j = 0;
                for (String i : line) {
                    word = i.replace("\"", ""); ///Tira as aspas das palavras
                    switch (j) {
                        case 0:
                            a.setId(Integer.parseInt(word));///Insere o id no objeto autores
                        case 1:
                            if (word.equals("")) { ///Se o nome for vazio
                                a.setName("Nome nao informado");
                            } else {
                                a.setName(word);///Insere o nome no objeto
                            }
                    }
                    j++;
                }
                authors.add(a);///adiciona o objeto a lista de objetos
                a = new Authors(); ///Cria o novo objeto
            }
        } catch (Exception e) {
        }
    }

    public static List<Registros> randomRegistro_livros(int N, List<Registros> old_list) { ///Escolhe os livros de forma aleatoria e de forma nao repetida de titulo
        if (N > old_list.size()) { ///Se o numero de livros a serem escolhidos for maior que o numero total de livros
            System.out.println("Erro! N não pode ser maior que o número total de registros");
            return null;
        }
        Random rand = new Random(System.currentTimeMillis());
        Set<String> aux_set = new HashSet<>(); //Estrutura de Set para garantir que o mesmo registro não
        //será selecionado mais de uma vez
        List<Integer> aux = new ArrayList<>();///Lista de interios para guardar as posicoes de cada livro
        List<Registros> new_list = new ArrayList<>();
        int n;
        while (aux_set.size() < N) { //apenas terminar quando o HashSet estiver cheio, isto é, quando houver
            //N registros DIFERENTES
            n = rand.nextInt(old_list.size());
            int old_size = aux_set.size(); ///pega o tamanho da lista de livros
            aux_set.add(old_list.get(n).getTitle());
            if (aux_set.size() > old_size) { ///verifica se o tamanho é maior. Significa que o elemento foi adicionado a lista e nao esta repetido
                aux.add(n);
            }
        }
        for (int x : aux) { //Adiciona os livros referentes a cada posicao numa lista
            new_list.add(old_list.get(x));
        }
        return new_list;///retorna lista de livros
    }

    public static void autores_mais_Frequentes(List<Registros> all_books, File txt_authors) {///Secao 2.
        List<Authors> list_authors = new ArrayList<>();
        read_authors(list_authors, txt_authors);
        op = true;///Faz o quickSort_id ordenar por id
        quickSort_id(list_authors, 0, list_authors.size() - 1);///ordena os autores por id
        System.out.println("N- " + N); ///Mostra o numero de livros a serem inseridos na tabela hash
        HashTable_livros table_livros = new HashTable_livros(N); ///Cria uma nova tabela hash
        List<Registros> aleatoria = new ArrayList<>();  ///Cria uma nova lista
        aleatoria = randomRegistro_livros(N, all_books); ///Preenche a lista com N livros aleatorios e diferentes
        if (aleatoria == null) {///Se o N é maior que o numero de livros
            return;
        }
        for (int i = 0; i < N; i++) { ///Insere os livros na tabela hash
            table_livros.insert(aleatoria.get(i));
        }
        int M;
        HashTable_authors authors = new HashTable_authors(N); ///Cria a tabela hash de autores
        List<Integer> ids_author = new ArrayList<>(); ///Lista com os ids dos autores
        for (int i = 0; i < table_livros.getLength(); i++) { ///Percorre a hash de livros
            if (table_livros.getPosition(i) != null) {///Se a posicao for diferente de null ou seja, conter um livro
                ids_author = table_livros.getPosition(i).getAuthors(); ///Recebe a lista de autores do livro
                for (int id : ids_author) { ///Percorre a lista de ids de autore
                    if (id >= list_authors.size()) { ///se o id for maior que o tamanho da lista de autores, ou seja, maior que maior que o maior id de autor
                        break;///para
                    } else {
                        Authors aux = list_authors.get(id - 1); ///Pega o autor referente aquele id. Esta na posicao -1 da lista referente a seu id
                        authors.insert(aux); ///    Adiciona o autor encontrado na hash de autores
                    }
                }
            }
        }
        list_authors.clear(); ///limpa a lista de autores
        for (int i = 0; i < authors.getLength(); i++) { ///Percorre a hash de autores
            Authors p = authors.getPosition(i); ///pega o autor referente a posicao i
            if (p != null) {
                list_authors.add(p);///adiciona o autor a lista de autores da hash
            }
        }
        Scanner teclado = new Scanner(System.in);
        M = 0;
        do {
            System.out.print("Numero de autores mais frequentes: ");
            M = teclado.nextInt();///Le o M numero de autores mais frequentes
            if (M > list_authors.size()) { ///se o M for mairo que o numero de autores
                System.out.println("ERRO !!! --- Numero maior que a quantidade de autores");
            }
        } while (M > list_authors.size()); ///Enquanto o M nao for valido

        op = false; ///faz o quickSort_id ordenar por numero de livros e de forma decrescente
        quickSort_id(list_authors, 0, list_authors.size() - 1); ///Chama o algoritmo de ordenacao
        file.println("Secao 2: =================================================================================");
        file.println("|N. Livros:|" + "|     Id    |" + "|  Autor:     ");
        System.out.println("|N. Livros:|" + "|     Id    |" + "|  Autor:     ");
        for (int i = 0; i < M; i++) { ///Anota todos os M autores mais frequentes na saida e mostra na tela

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
    
     ///Secao 3//////////////////////////////////////////////////////////////////
    public static void print_output1(String tipo, int t) { ///Funcao de impressao de cabeçalho
        file.println(tipo);
        file.println("Numero de Elementos: " + t + "**********************");
        file.println("|   Tempo(ms)   |" + "|  Comparacoes  |" + "| Movimentacoes |");
        System.out.println(tipo);
        System.out.println("Numero de Elementos: " + t + "**********************");
        System.out.println("|   Tempo(ms)   |" + "|  Comparacoes  |" + "| Movimentacoes |");
    }

    public static void print_output2(String tipo) { ///Funcao de impressao de dados
        int i;
        if ("Insercao:".equals(tipo)) { ///Se a operacao é uma insercao
            i = 0;
        } else { ///Se a operacao é a Busca
            i = 1;
        }
        ///Grava na saida e mostra as medias das estatisticas encontradas
        file.printf("|%14.4f |", time[i] / 5.0);
        file.printf("|%14s |", comparacoes[i] / 5);
        file.printf("|%14s |%n", movimentacoes[i] / 5);
        System.out.printf("|%14.4f |", time[i] / 5.0);
        System.out.printf("|%14s |", comparacoes[i] / 5);
        System.out.printf("|%14s |%n", movimentacoes[i] / 5);
    }

    public static void Busca_em_Estruturas_Balanceadas(List<Registros> livros) { ///Secao 3
        time = new double[2];
        comparacoes = new long[2];
        movimentacoes = new long[2];
        file.println("Secao 3: =================================================================================");
        int i = 0;
        List<Registros> aleatoria = new ArrayList<Registros>();

        RBTree read; ///Cria a arvore vermelho e preto
        double start_time;
        double final_time;
        int cont = 0;

        file.println("&&&&&&&& Arvore Vermelho-Preto &&&&&&&&");
        System.out.println("&&&&&&&& Arvore Vermelho-Preto &&&&&&&&");
        while (i < number_tests) { ///Faz o numero de testes recebido no inicio
            int t = length.get(i); //t recebe o numero de elementos para o teste i
            if (cont == 0) {///se for a primeiria iteracao do teste
                print_output1("Insercao:", t); ///Escreve o cabelho e o tipo de operacao
            }
            aleatoria = randomRegistro(t, livros); ///Cria uma lista aleatoria de livros
            if (aleatoria == null) { ///Se o numero de elementos do teste for mairo que total de elementos. Pare
                break;
            }
            read = new RBTree(aleatoria.get(0)); ///Adicona o primeiro elemento da lista à arvore RB e faz dele sua raiz
            start_time = System.nanoTime();///Inicia contagem do tempo
            for (int g = 1; g < aleatoria.size(); g++) { ///percorre a lista de livros
                read.insert(aleatoria.get(g)); ///adiciona o livro à arvore
            }
            final_time = System.nanoTime(); ///Finliza a contagem do tempo
            time[0] += (final_time - start_time) / 1000000.0;  ///Calcula o tempo gasto, transforma em milisegundos e guarda no vetor de tempos
            comparacoes[0] += read.getComparacoes();  ///Soma o numero de comparacoes da insercao
            movimentacoes[0] += read.getMovimentacoes(); ///Soma  o numero  de movimentacoes da insercao

            ///Buscas
            read.setComparacoes();///Zera o numero de comparacoes
            start_time = System.nanoTime(); ///Inicia contagem do tempo
            for (int g = 1; g < aleatoria.size(); g++) { ///percorre a lista de livros
                read.search(aleatoria.get(g).getId()); ///Pesqquisa cada um dos livros na arvore
            }
            final_time = System.nanoTime(); ///Finaliza contagem do tempo
            time[1] += (final_time - start_time) / 1000000.0;  ///Calcula o tempo gasto, transforma em milisegundos e guarda no vetor de tempos
            comparacoes[1] += read.getComparacoes();///Soma o numero de comparacoes da insercao
            cont++; ///atualiza o contador de iteracoes
            if (cont == 5) { ///Se foram feitas 5 iteracoes. Grava medias das estatisticas na saida e mostra na tela
                print_output2("Insercao:"); ///Escreve as esatisticas. Passa a posicao 0, referente aos dados da arvore vermelho e preto
                print_output1("Busca:", t); ///Escreve o cabeçalho para a busca e passa o numero de elementos da lista
                print_output2("Busca:");
                file.print("**********************\n");
                file.println();
                System.out.println("");
                i += 1; ///atualiza contador de testes. Ele tambem serve para escolher o tamanho de cada teste
                cont = 0; ///Zera o contador de iteracoes
                ///zera os vetor de estatisticas
                time = new double[2];
                comparacoes = new long[2];
                movimentacoes = new long[2];
            }
            aleatoria.clear(); ////Limpa a lista de elementos aleatorios
        }
        System.out.println("");///Pula linha
        file.println("#############");

        //Arvore B
        i = 0;
        cont = 0;
        BTree bt;///Cria a arvore B
        file.println("\n\n&&&&&&&& Arvore B &&&&&&&&");
        System.out.println("\n\n&&&&&&&& Arvore B &&&&&&&&");
        int j = 0;
        while (j < length_forTree.size()) { ///Percorre a lista de tamahos para nos da arvore

            int d = length_forTree.get(j); ///recebe o valor minimo de chaves em cada no da arvore
            file.println("D= " + d);
            System.out.println("D= " + d);

            while (i < number_tests) {  ///Pecorre a lista com o tamanho de cada teste
                int t = length.get(i); ///recebe o tamanho do teste j
                if (cont == 0) { ///Se for a primeira iteracao, escreve o cabeçalho
                    print_output1("Insercao:", t);
                }
                aleatoria = randomRegistro(t, livros); ///Cria a lista aleatoria de livros com o tamanho t
                if (aleatoria == null) {///Se o tamanho t for mairo que o numero total de elementos. Pare
                    break;
                }
                bt = new BTree(d); ///Cria a arvore com o valor minimo de d chaves por no
                start_time = System.nanoTime();///Inicia marcacao do tempo
                for (int g = 0; g < aleatoria.size(); g++) { ///Percorrre a lista de livros aleatoria
                    bt.insert(aleatoria.get(g)); ///Insere o livro na arvore B
                }
                final_time = System.nanoTime(); ///Finaliza a marcacao do tempo
                time[0] += (final_time - start_time) / 1000000.0; ///Calcula o tempo da execao e o transforma em milisegundos. Soma aos tempos anteriores
                comparacoes[0] += bt.getComparacoes(); ///Soma numero de comparacoces nas estatisticas
                movimentacoes[0] += bt.getMovimentacoes(); ///Soma numero de movimentacoes nas estatisticas

                ///Buscas
                bt.setComparacoes();///Zera numero de comparacoes
                start_time = System.nanoTime(); ///Inicia contagem do tempo
                for (int g = 0; g < aleatoria.size(); g++) { ///Percorre a lista aleatoria de livros
                    bt.search(aleatoria.get(g)); ///Adiciona o livro a arvore
                }
                final_time = System.nanoTime(); ///Finaliza contagem de tempo
                time[1] += (final_time - start_time) / 1000000.0; ///Atualiza estatistica do tempo. Passa o tempo para milisegundos
                comparacoes[1] += bt.getComparacoes(); ///Atualiza estatistica de comparaceos
                cont += 1; ///Incrementa contador de iteracoes
                if (cont == 5) { ///Se foram feitas 5 iteracoes
                    print_output2("Insercao:");///Escreve saida para a insercao
                    print_output1("Busca:", t);///Escreve cabeçalho para a busca
                    print_output2("Busca:");///escreve a saida para a busca
                    System.out.println("");
                    file.println("**********************\n");
                    i += 1; 
                    cont = 0;
                    ///Zera vetores de estatisticas
                    time = new double[2];
                    comparacoes = new long[2];
                    movimentacoes = new long[2];
                }
                aleatoria.clear();
            }
            i = 0;
            j++;
        }
        file.println("==========================================================================================\n\n");
    }
}
///FIM .. .. ..O CODIGO É COMO A VIDA. TEM SEU FIM.