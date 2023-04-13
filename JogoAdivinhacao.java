import java.util.Random;
import java.util.Scanner;

public class JogoAdivinhacao {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int min = 1, max = 100, palpite = 0, cont = 0, palpiteClimbing = 0, palpiteAnnealing = 0;
        int resposta = random.nextInt(max - min + 1) + min;

        telaInicial();

        while (palpite != resposta) {
            System.out.print("Digite seu palpite: ");
            palpite = scanner.nextInt();

            // Salvar o valor do palpite para testar os algoritmos
            if (cont == 0) {
                palpiteClimbing = palpite;
                palpiteAnnealing = palpite;
            }

            cont++; // Contador de palpites do usuario

            if (palpite < resposta) {
                System.out.println(
                        "vvvvvvvvvvvvvvvvvvvvvvvvv Seu palpite está a baixo do valor escondido. vvvvvvvvvvvvvvvvvvvvvvvvv");
            } else if (palpite > resposta) {
                System.out.println(
                        "^^^^^^^^^^^^^^^^^^^^^^^^^ Seu palpite está a cima do valor escondido. ^^^^^^^^^^^^^^^^^^^^^^^^^");
            } else {
                System.out.println("Parabéns, você encontrou o valor escondido!");
                break;
            }

            if (palpite != resposta) {
                System.out.println("Infelizmente não encontrou o valor escondido! Continue tentando!");
            }

        }

        System.out.println("A quantidade de tentativas do Jogador: " + cont);
        System.out.print("\n");
        System.out.println("************************************************************************");
        System.out.println("Hill Climbing Jogando:");
        cont = 0;
        while (resposta != palpiteClimbing) {
            System.out.println("Palpite Climbing:" + palpiteClimbing);
            int palpiteHillClimbing = hillClimbing(resposta, palpiteClimbing);
            palpiteClimbing = palpiteHillClimbing;
            cont++;

        }

        System.out.println("A quantidade de tentativas do HillClimbing: " + cont);

        System.out.println("\n************************F************************************************");
        cont = 0;
        while (palpiteAnnealing != resposta) {
            cont++;
            int palpiteSimulatedAnnealing = simulatedAnnealing(resposta, palpiteAnnealing);
            palpiteAnnealing = palpiteSimulatedAnnealing;
        }

        System.out.println("A quantidade de tentativas do SimulatedAnnealing: " + cont);
    }

    private static int hillClimbing(int resposta, int palpite) {
        int min = 1, max = 100;
        int hillClimbingPalpite = palpite, distancia = Math.abs(resposta - palpite), novoPalpite, novaDistancia;

        for (int i = 0; i < 10; i++) {
            novoPalpite = palpite;

            switch (i % 3) {
                case 0:
                    novoPalpite = palpite + (new Random().nextInt(10) + 1);
                    break;
                case 1:
                    novoPalpite = palpite - (new Random().nextInt(10) + 1);
                    break;
                case 2:
                    novoPalpite = (palpite / 10) * 10 + (new Random().nextInt(10));
                    break;
                default:
                    System.out.println("ERROR! Algo aconteceu de errado!");
            }

            if (novoPalpite < min || novoPalpite > max) {
                continue;
            }

            novaDistancia = Math.abs(resposta - novoPalpite);

            if (novaDistancia < distancia) {
                distancia = novaDistancia;
                hillClimbingPalpite = novoPalpite;
            }
        }

        return hillClimbingPalpite;
    }

    private static int simulatedAnnealing(int resposta, int palpite) {
        int min = 1, max = 100;
        int simulatedAnnealingPalpite = palpite, distancia = Math.abs(resposta - palpite), novaDistancia, novoPalpite;
        double probabilidade, temperatura = 10000, resfriamento = 0.003;

        while (temperatura > 1) {
            novoPalpite = palpite + (new Random().nextInt(20) - 10);

            if (novoPalpite < min || novoPalpite > max) {
                continue;
            }

            novaDistancia = Math.abs(resposta - novoPalpite);

            if (novaDistancia == 0) {
                return novoPalpite;
            }
            probabilidade = Math.exp(-novaDistancia / temperatura);

            if (probabilidade > Math.random()) {
                palpite = novoPalpite;

                if (novaDistancia < distancia) {
                    distancia = novaDistancia;
                    simulatedAnnealingPalpite = novoPalpite;
                }
            }
            temperatura *= 1 - resfriamento;
        }

        return simulatedAnnealingPalpite;
    }

    public static void telaInicial() {
        System.out.println("************************************************************************");
        System.out.println("-----------------        JOGO DE ADIVINHAÇÃO        -----------------");
        System.out.println("************************************************************************");
        System.out.println("-----------------: Informe um valor entre 1 a 100 :-----------------");
    }

}