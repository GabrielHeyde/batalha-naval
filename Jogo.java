//Gabriel Heyde Pintos
//Esta classe possui todos os métodos necessários para executar uma partida completa de Batalha Naval, sendo também a classe que contém o método Main para executar o programa

import java.util.Scanner;

public class Jogo {

    // Variáveis criadas com o intuito de contabilizar o número de objetos
    // remanescentes no tabuleiro do jogador e do BOT
    static int jogadorP = 4;
    static int jogadorD = 5;
    static int jogadorS = 6;
    static int objJogadores = jogadorP + jogadorD + jogadorS;
    static int botP = 4;
    static int botD = 5;
    static int botS = 6;
    static int objBot = botP + botD + botS;

    // Método Main para execução do programa
    public static void main(String[] args) {

        // Scanner criado para o input de determinadas informações, como o quadrante que
        // será atacado pelo usuário
        Scanner scanner = new Scanner(System.in);

        // Objetos criados para serem distribuídos nos tabuleiros e facilitarem o
        // preenchimento dos mesmos
        Objeto portaAvioes = new Objeto("Porta-Aviões", "P");
        Objeto destroyer = new Objeto("Fragata / Corveta / Contratorpedeiro", "D");
        Objeto submarino = new Objeto("Submarino", "S");
        Objeto água = new Objeto("Água", "~");

        // Criação dos tabuleiros que serão utilizados durante o jogo
        Objeto[][] tabuleiroJogador = new Objeto[5][5];
        Objeto[][] tabuleiroBot = new Objeto[5][5];
        Objeto[][] tabuleiroBotPrintado = new Objeto[5][5];

        // Chamada dos métodos que preenchem os tabuleiros que serão utilizados durante
        // a partida, sendo o método "preencheTabuleiro" responsável por distruibuir os
        // objetos nos tabuleiros e o método "preencherTabuleiroBot" reponsável por
        // criar o tabuleiro exibido para o jogador no começo da partida, onde todas as
        // posições são preenchidas com o símbolo de "~"
        preencheTabuleiro(portaAvioes, destroyer, submarino, água, tabuleiroJogador);
        preencheTabuleiro(portaAvioes, destroyer, submarino, água, tabuleiroBot);
        preenchetabuleiroBot(água, tabuleiroBotPrintado);

        // Mensagem inicial de saudação ao usuário e um guia básico de como jogar, além
        // do pedido do nome do jogaddor
        System.out.print(
                "Seja bem-vindo(a) ao jogo de Batalha Naval!\nPara jogar, você deve digitar o quadrante no qual deseja atacar o seu adversário, para isso, você deve digitar a coordenada composta pela linha(A ao E) e a coluna(1 ao 5) e pressionar a tecla Enter.\nGanha aquele que destruir todos os objetos do adversário primeiro!\nPara começarmos, digite o seu nome: ");
        String nome = scanner.next();
        System.out.println("");

        // Laço de repetição criado para executar as rodadas do jogo conforme o padrão
        // solicitado, neste laço são chamados os métodos necessários para poder jogar a
        // Batalha Naval. O laço de repetição é quebrado quando algum dos jogadores tem
        // todos os seus objetos atingidos pelo menos uma vez
        int rodadas = 1;
        while (objJogadores > 0 && objBot > 0) {
            System.out.println(rodadas + "ª Rodada - BATALHA NAVAL\nTABULEIRO DO SEU ADVERSÁRIO");
            imprimeTabuleiro(tabuleiroBotPrintado);
            System.out.println(
                    "\nTotal de Objetos Restantes: " + objBot + "(P: " + botP + "| D: " + botD + "| S: " + botS + ")");
            atirarJogador(scanner, tabuleiroBot, tabuleiroBotPrintado);
            if (objBot == 0) {
                break;
            }
            System.out.println("\n\nSEU TABULEIRO (" + nome + ")");
            imprimeTabuleiro(tabuleiroJogador);
            System.out.println("\nTotal de Objetos Restantes: " + objJogadores + "(P: " + jogadorP + "| D: " + jogadorD
                    + "| S: " + jogadorS + ")");
            atirarBot(tabuleiroJogador);
            System.out.println(
                    "\n==========================================================================================================================================================================================================================");
            rodadas++;
        }
        System.out.println("\n");
        // Condicionais que imprimem ao final da partida quem foi o campeão da Batalha
        // Naval
        if (objBot == 0) {
            System.out.println(
                    "PARABÉNS! Você superou o seu adversário e foi o(a) campeão/campeã do jogo de Batalha Naval!\nSinta-se à vontade para jogar novamente sempre que quiser. Muito obrigado! :D");
        } else if (objJogadores == 0) {
            System.out.println(
                    "Que pena, você foi superado(a) pelo seu adversário e foi o derrotado(a) no jogo de Batalha Naval!\nSinta-se à vontade para jogar novamente sempre que quiser. Muito obrigado! :D");
        }
    }

    // Método criado para controlar os disparos do BOT
    private static void atirarBot(Objeto[][] tabuleiro) {
        int p1 = (int) (Math.random() * 5);
        int p2 = (int) (Math.random() * 5);
        // Convertendo valores int em char para trazer o quadrante na tela do usuário
        char pletra = 'Z';
        if (p1 == 0) {
            pletra = 'A';
        } else if (p1 == 1) {
            pletra = 'B';
        } else if (p1 == 2) {
            pletra = 'C';
        } else if (p1 == 3) {
            pletra = 'D';
        } else if (p1 == 4) {
            pletra = 'E';
        }
        // Somando mais um ao quadrante para trazer a informação conforme o tabuleiro
        // apresentado para o usuário
        int pposicao = p2 + 1;
        // Condicional caso o BOT acerte uma posição preenchida com "água"
        if (tabuleiro[p1][p2].getSigla().equals("~")) {
            System.out.println("O adversário atirou na água! Posição atingida: [" + pletra + "," + pposicao + "]");
        } else if (tabuleiro[p1][p2].getSigla().equals("P") || tabuleiro[p1][p2].getSigla().equals("D")
                || tabuleiro[p1][p2].getSigla().equals("S")) {
            // Condicional caso o BOT atire em uma posição já acertada anteriormente
            if (tabuleiro[p1][p2].isAtingido() == true) {
                System.out.println(
                        "O adversário atirou em um objeto já atingido! Objeto Atingido: " + tabuleiro[p1][p2].getTipo()
                                + ". Posição atingida: [" + pletra + "," + pposicao + "]");
            }
            // Condicional caso o BOT destrua um objeto
            else {
                tabuleiro[p1][p2].setAtingido(true);
                if (tabuleiro[p1][p2].getSigla().equals("P")) {
                    jogadorP = jogadorP - 1;
                    objJogadores = objJogadores - 1;
                } else if (tabuleiro[p1][p2].getSigla().equals("D")) {
                    jogadorD = jogadorD - 1;
                    objJogadores = objJogadores - 1;
                } else if (tabuleiro[p1][p2].getSigla().equals("S")) {
                    jogadorS = jogadorS - 1;
                    objJogadores = objJogadores - 1;
                }
                System.out.println("Seu adversário destruiu um objeto! Objeto Atingido: " + tabuleiro[p1][p2].getTipo()
                        + ". Posição atingida: [" + pletra + "," + pposicao + "]");
            }
        }
    }

    // Método criado para controlar os disparos do jogador controlável
    private static void atirarJogador(Scanner scanner, Objeto[][] tabuleiroB, Objeto[][] tabuleiroBPrintado) {
        System.out.print("Digite a posição(Linha x Coluna) que você deseja atirar: ");
        String disparo = scanner.next();
        // Método utilizado para facilitar a leitura da condicional com apenas um tipo
        // de letra
        String maiuscula = disparo.toUpperCase();
        char[] posicoes = maiuscula.toCharArray();
        while (posicoes.length != 2
                || (posicoes[0] != 'A' && posicoes[0] != 'B' && posicoes[0] != 'C' && posicoes[0] != 'D'
                        && posicoes[0] != 'E')
                || (posicoes[1] != '1' && posicoes[1] != '2' && posicoes[1] != '3' && posicoes[1] != '4'
                        && posicoes[1] != '5')) {
            System.out.println("VALOR INVÁLIDO! Digite a a posição(Linha x Coluna) que você deseja atirar: ");
            disparo = scanner.next();
            maiuscula = disparo.toUpperCase();
            posicoes = maiuscula.toCharArray();
        }
        // Transformação do char em um valor int para encontrar o índice desejado pelo
        // usuário
        int p1 = 5;
        if (posicoes[0] == 'A') {
            p1 = 0;
        } else if (posicoes[0] == 'B') {
            p1 = 1;
        } else if (posicoes[0] == 'C') {
            p1 = 2;
        } else if (posicoes[0] == 'D') {
            p1 = 3;
        } else if (posicoes[0] == 'E') {
            p1 = 4;
        }

        int p2 = 5;
        if (posicoes[1] == '1') {
            p2 = 0;
        } else if (posicoes[1] == '2') {
            p2 = 1;
        } else if (posicoes[1] == '3') {
            p2 = 2;
        } else if (posicoes[1] == '4') {
            p2 = 3;
        } else if (posicoes[1] == '5') {
            p2 = 4;
        }

        // Condicional caso o jogador acerte uma posição preenchida com "água"
        if (tabuleiroB[p1][p2].getSigla().equals("~")) {
            System.out.println("Você atirou na água! Posição atingida: [" + posicoes[0] + "," + posicoes[1] + "]");
        } else {
            // Condicional caso o jogador atire em uma posição já acertada anteriormente
            if (tabuleiroB[p1][p2].isAtingido() == true) {
                System.out.println(
                        "Você atirou em um objeto já atingido! Objeto Atingido: " + tabuleiroB[p1][p2].getTipo()
                                + ". Posição atingida: [" + posicoes[0] + "," + posicoes[1] + "]");
            }
            // Condicional caso o jogador destrua um objeto
            else {
                tabuleiroB[p1][p2].setAtingido(true);
                tabuleiroBPrintado[p1][p2] = tabuleiroB[p1][p2];
                if (tabuleiroB[p1][p2].getSigla().equals("P")) {
                    botP = botP - 1;
                    objBot = objBot - 1;
                } else if (tabuleiroB[p1][p2].getSigla().equals("D")) {
                    botD = botD - 1;
                    objBot = objBot - 1;
                } else if (tabuleiroB[p1][p2].getSigla().equals("S")) {
                    botS = botS - 1;
                    objBot = objBot - 1;
                }
                System.out.println("Você destruiu um objeto! Objeto Atingido: " + tabuleiroB[p1][p2].getTipo()
                        + ". Posição atingida: [" + posicoes[0] + "," + posicoes[1] + "]");
            }
        }
    }

    // Método criado para preencher o tabuleiro do bot que será visível ao usuário
    // apenas com o símbolo de "~"
    private static void preenchetabuleiroBot(Objeto água, Objeto[][] tabuleiroBotPrintado) {
        for (int i = 0; i < tabuleiroBotPrintado.length; i++) {
            for (int j = 0; j < tabuleiroBotPrintado[i].length; j++) {
                tabuleiroBotPrintado[i][j] = água;
            }
        }
    }

    // Método criado para preencher os tabuleiros tanto do Bot quanto do Jogador que
    // serão utilizados durante a partida
    private static void preencheTabuleiro(Objeto portaAvioes, Objeto destroyer, Objeto submarino, Objeto água,
            Objeto[][] tabuleiro) {
        // preenchimento do tabuleiro com os Porta Aviões
        int contador = 0;
        while (contador < 4) {
            int posicao1 = (int) (Math.random() * 5);
            int posicao2 = (int) (Math.random() * 5);
            if (tabuleiro[posicao1][posicao2] == null) {
                tabuleiro[posicao1][posicao2] = new Objeto(portaAvioes.getTipo(), portaAvioes.getSigla());
                contador++;
            } else {
                while (tabuleiro[posicao1][posicao2] != null) {
                    posicao1 = (int) (Math.random() * 5);
                    posicao2 = (int) (Math.random() * 5);
                }
                tabuleiro[posicao1][posicao2] = new Objeto(portaAvioes.getTipo(), portaAvioes.getSigla());
                contador++;
            }
        }

        // preenchimento do tabuleiro com os objetos "Destroyer"
        contador = 0;
        while (contador < 5) {
            int posicao1 = (int) (Math.random() * 5);
            int posicao2 = (int) (Math.random() * 5);
            if (tabuleiro[posicao1][posicao2] == null) {
                if (contador == 0 || contador == 1) {
                    tabuleiro[posicao1][posicao2] = new Objeto("Fragata", destroyer.getSigla());
                    contador++;
                } else if (contador == 2 || contador == 3) {
                    tabuleiro[posicao1][posicao2] = new Objeto("Corveta", destroyer.getSigla());
                    contador++;
                } else if (contador == 4) {
                    tabuleiro[posicao1][posicao2] = new Objeto("Contratorpedeiro", destroyer.getSigla());
                    contador++;
                }
            } else {
                while (tabuleiro[posicao1][posicao2] != null) {
                    posicao1 = (int) (Math.random() * 5);
                    posicao2 = (int) (Math.random() * 5);
                }
                if (contador == 0 || contador == 1) {
                    tabuleiro[posicao1][posicao2] = new Objeto("Fragata", destroyer.getSigla());
                    contador++;
                } else if (contador == 2 || contador == 3) {
                    tabuleiro[posicao1][posicao2] = new Objeto("Corveta", destroyer.getSigla());
                    contador++;
                } else if (contador == 4) {
                    tabuleiro[posicao1][posicao2] = new Objeto("Contratorpedeiro", destroyer.getSigla());
                    contador++;
                }
            }
        }

        // preenchimento do tabuleiro com os Submarinos
        contador = 0;
        while (contador < 6) {
            int posicao1 = (int) (Math.random() * 5);
            int posicao2 = (int) (Math.random() * 5);
            if (tabuleiro[posicao1][posicao2] == null) {
                tabuleiro[posicao1][posicao2] = new Objeto(submarino.getTipo(), submarino.getSigla());
                contador++;
            } else {
                while (tabuleiro[posicao1][posicao2] != null) {
                    posicao1 = (int) (Math.random() * 5);
                    posicao2 = (int) (Math.random() * 5);
                }
                tabuleiro[posicao1][posicao2] = new Objeto(submarino.getTipo(), submarino.getSigla());
                contador++;
            }
        }

        // preenchimento do restante do tabuleiro com "água"
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == null) {
                    tabuleiro[i][j] = água;
                }

            }

        }
    }

    // Método criado para imprimir os tabuleiros na tela
    private static void imprimeTabuleiro(Objeto[][] tabuleiro) {
        System.out.println("    1   2   3   4   5   ");
        for (int i = 0; i < tabuleiro.length; i++) {
            if (i > 0) {
                System.out.println("");
            }
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (j == 0) {
                    if (i == 0)
                        System.out.print("A");
                    if (i == 1)
                        System.out.print("B");
                    if (i == 2)
                        System.out.print("C");
                    if (i == 3)
                        System.out.print("D");
                    if (i == 4)
                        System.out.print("E");
                }
                System.out.print(" | " + tabuleiro[i][j].getSigla());
                if (j == tabuleiro.length - 1) {
                    System.out.print(" | ");
                }
            }
        }
    }
}
