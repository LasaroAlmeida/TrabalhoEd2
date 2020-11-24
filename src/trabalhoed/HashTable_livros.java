package trabalhoed;

public class HashTable_livros {

    private Registros[] table; ///Hash de livros
    private int length;///Tamanho da hash
    private int m; ///Numero primo menor que o tamanho da hash

    public HashTable_livros(int length) { ///Construtor. Recebe o numero de elementos que serao inseridos
        this.length = encontra_primo_menor(length * 2); ///Encontra o maior primo menor que length *2 e usa esse numero como tamanho da tabela
        this.m = encontra_primo_menor(this.length); ///Encontra o maior primo menor que o numero primo do tamanho da tabela
        this.table = new Registros[this.length]; ///Crio um vetor de tamanho this.length para ser usado como hash
        for (int i = 0; i < this.length; i++) { ///Armazeno null em todas as posicoes da tabela
            this.table[i] = null;
        }
    }

    public void insert(Registros l) { ///Funcao de Insercao
        int h1 = (int) hash_H1(l); ///chama a funcao hash 1
        int h2 = hash_H2(l); ///chama a funcao hash 2
        int i = 0; 
        int h = double_hash(h1, h2, i); ///chama a funcao double hash. A hash de sondagem dupla
        while (i < this.length) { ///i for menor que o tamanho da tabela

            if (this.table[h] == null) { ///Se a posicao estiver vazia
                this.table[h] = l; ///adiciona o livro na hash
                return;
            } else if (this.table[h].getTitle().equals(l.getTitle())) { ///Se o elemento ja existe na hash retorna, e nao faz mais nada
                return;
            }
            ///Se ocorreu colisao, atualiza o i e chama a funcao de sondagem dupla novamente, agora com o novo i
            i += 1;
            h = double_hash(h1, h2, i);
        }
    }

    private int hash_H1(Registros l) { ///Funcao Hash h1
        long result = 0;
        String title = l.getTitle(); ///Pega o titulo do livro
        double b = title.length() * 0.4; ///Pega um valor referente a 40% do tamanho do titulo
        int a = (int) b; ///Transforma o numero referente a 40% do tamanho do titulo em inteiro
        for (int i = 0; i < a; i++) { ///percorre os caracteres referentes a 40% do titulo
            if (i + 1 == a) { ///se for o ultimo caractere
                if (title.charAt(i) % 2 == 1) { ///verifica se o ultimo caractere é impar e se for
                    result *= (title.charAt(i)+2); ///incrementa o ultimo caractere em 2 e o multiplica pela soma ja obtida
                    result += 1; ///incrementa o resultado da multiplicação em 1
                } else { ///se o ultimo elemento nao for impar
                    result *=title.charAt(i); ///multiplica a soma ate entao adquirida pelo ultimo caractere dos 40% 
                }

            } else { ///se nao for o ultimo caractere
                result += title.charAt(i); ///soma os caracteres
            }
        }
        return (int) (result) % this.length; ///retorna o resto do resultado pelo tamanho da hash 
    }

    private int double_hash(int h1, int h2, int i) { ///Calcula a hash dupla com os dados recebidos
        return (h1 + i * h2) % this.length;
    }

    private int hash_H2(Registros l) {
        String title = l.getTitle(); ///Pega o titulo do livro
        double b = title.length() * 0.6; ///Armazena um numero referente a 60% dos elementos do titulo
        int c = (int) b; ///transforma o numero referente a 60% dos elementos do titulo em inteiro
        long result = 0;
        for (; c < title.length(); c++) { ///percorre os caracteres referentes aos ultimos 40% do titulo (a partir do inteiro referente a 60% do titulo)
            if (c + 1 == title.length()) { ///vefica se eh o ultimo caractere do titulo
                result *= (int)title.charAt(c)*3.25; ///multiplica a soma ate entao obtida por 3.25 e pelo ultimo caractere do titulo
            } else { ///se nao for o ultimo caractere do titulo
                result += title.charAt(c); ///faz a soma do caractere
            }
        }
        return 3 + (int) (result) % m; ///Retorna o resto +3 da divisao de result por m 
    }

    public Registros getPosition(int i) { ///pega uma posicao da hash e retorna o livro contido nela
        return (this.table[i]==null) ? null : this.table[i];
    }

    public int getLength() { ///retorna o tamanho da hash
        return length;
    }

    private int encontra_primo_menor(int tam) { ///encontra o maior numero primo menor que tam
        int nao_primo;
        for (int i = tam - 1; i >= 2; i--) {
            nao_primo = 0;
            for (int j = 2; j < i / 2; j++) {
                if (i % j == 0) {
                    nao_primo++;
                }
            }
            if (nao_primo == 0) {
                return i;
            }
        }
        return 0;
    }
    
}
