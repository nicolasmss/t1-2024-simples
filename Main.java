import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    /*
     * G/G/1/5, chegadas entre 2...5, atendimento entre 3...5
     * G/G/2/5, chegadas entre 2...5, atendimento entre 3...5
     * Para ambas simulações, considere inicialmente a fila vazia e o primeiro
     * cliente chega no tempo 2,0.
     * Realize a simulação com 100.000 aleatórios, ou seja, ao se utilizar o 100.000
     * aleatório, sua simulação
     * deve se encerrar e a distribuição de probabilidades, bem como os tempos
     * acumulados para os estados da fila
     * devem ser reportados. Além disso, indique o número de perda de clientes (caso
     * tenha havido perda) e o tempo
     * global da simulação.
     */

    // G/G/1/3 | 1..2 | 3..6 | inicial = 2.0

    static double inicial = 2;

    static int tamFila = 5;
    static double chegada0 = 2;
    static double chegada1 = 5;

    static int tamServ = 2;
    static double atendimento0 = 3;
    static double atendimento1 = 5;

    public static void main(String[] args) {
        double tempo = 0;
        int perdas = 0;
        CongruenteLinearA1 cl = new CongruenteLinearA1(12, 123, 456, 1597531591);
        //List<Double> aleatorios = cl.preDef();
        List<Double> aleatorios = cl.gerarX(100000);

        List<Evento> feitos = new ArrayList<>();
        List<Evento> escalonador = new ArrayList<>();

        int fila = 0;
        int serv = 0;
        feitos.add(new Evento(inicial, "chegada", tamFila));
        feitos.get(0).setTempoX(fila, feitos.get(0).tempo);
        fila++;
        feitos.get(0).fila = fila;
        tempo = inicial;

        System.out.println(feitos.get(0));

        while (true) {
            if (aleatorios.size() < 1) {
                break;
            }
            if (fila >= 1 && serv < tamServ && serv < fila) {
                double formSaida = (atendimento1 - atendimento0) * aleatorios.remove(0) + atendimento0
                        + feitos.get(feitos.size() - 1).tempo;
                escalonador.add(new Evento(formSaida, "saida", tamFila));
                serv++;
            }

            if (aleatorios.size() < 1) {
                break;
            }
            if (chegadaLivre(escalonador)) {
                double formChegada = (chegada1 - chegada0) * aleatorios.remove(0) + chegada0
                        + tempo;
                escalonador.add(new Evento(formChegada, "chegada", tamFila));
            }

            if (aleatorios.size() < 1) {
                break;
            }

            int menor = menorTempoPos(escalonador);
            if (menor >= 0) {
                boolean seguir = true;
                Evento evento = escalonador.remove(menor);
                Evento ultEvento = feitos.get(feitos.size() - 1);
                copiaTempos(ultEvento, evento);
                evento.temposX[ultEvento.fila] += evento.tempo - ultEvento.tempo;
                if (evento.tipo.equals("chegada")) {
                    tempo = evento.tempo;
                    if (fila < tamFila) {
                        fila++;
                    } else {
                        perdas++;
                        seguir = false;
                    }
                } else {
                    serv--;
                    fila--;
                }
                evento.fila = fila;
                // tempo = evento.tempo;

                feitos.add(evento);
                //System.out.println(evento);
            }
        }
        System.out.println(feitos.get(feitos.size()-1));
        System.out.println("perdas = " + perdas);
    }

    public static int menorTempoPos(List<Evento> lista) {
        if (lista.size() == 0) {
            return -1;
        }
        double tempoMenor = lista.get(0).tempo;
        int posMenorTempo = 0;
        for (int i = 1; i < lista.size(); i++) {
            if (tempoMenor > lista.get(i).tempo) {
                tempoMenor = lista.get(i).tempo;
                posMenorTempo = i;
            }
        }
        return posMenorTempo;
    }

    public static void copiaTempos(Evento ev1, Evento ev2) {
        ev2.temposX = Arrays.copyOf(ev1.temposX, tamFila + 1);
    }

    public static boolean chegadaLivre(List<Evento> escalonador) {
        for (Evento evento : escalonador) {
            if (evento.tipo == "chegada") {
                return false;
            }
        }
        return true;
    }
}
