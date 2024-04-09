import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parte2Tandem {
    /*
     * Fila 1 - G/G/2/3, chegadas entre 1..4, atendimento entre 3..4
     * Fila 2 - G/G/1/5, atendimento entre 2..3
     * Note que a Fila 2 não possui chegadas de clientes do exterior da rede. A Fila
     * 2 recebe 100% dos clientes que passam pela Fila 1, ou seja, as Filas 1 e 2
     * estão em linha (em tandem) onde os clientes chegam do exterior na Fila 1 e
     * posteriormente vão para a Fila 2, indo embora do sistema após atendimento da
     * Fila 2.
     * 
     * Para a simulação, considere inicialmente as filas vazias e o primeiro cliente
     * chega no tempo 1,5. Realizem a simulação com 100.000 aleatórios, ou seja, ao
     * se utilizar o 100.000 aleatório, a simulação deve se encerrar e a
     * distribuição de probabilidades, bem como os tempos acumulados para os estados
     * de cada fila devem ser reportados. Além disso, indique o número de perda de
     * clientes (caso tenha havido perda) de cada fila e o tempo global da
     * simulação.
     */

    static double inicial = 1.5;
    static double chegada0 = 1;
    static double chegada1 = 4;

    static List<Fila> filas = new ArrayList<Fila>();

    public static void popular() {
        // filas.add(new Fila(tamFila, tamServ, atendimento0, atendimento1, name,
        // saida)) *não alterar nome da fila1*
        filas.add(new Fila(3, 2, 3, 4, "fila1", "fila2"));// não altere name da fila1
        filas.add(new Fila(5, 1, 2, 3, "fila2", "saida"));// saida é a saida final

    }

    public static void main(String[] args) {
        popular();
        double tempo = 0;
        CongruenteLinearA1 cl = new CongruenteLinearA1(12, 123, 456, 1597531591);
        List<Double> aleatorios = cl.gerarX(100000);
        List<Evento> escalonador = new ArrayList<>();

        Fila fila1 = buscarFila("fila1");

        Evento e = new Evento(inicial, "chegada", fila1.tamFila, "fila1");
        fila1.feitos.add(e);
        fila1.feitos.get(0).setTempoX(fila1.fila, fila1.feitos.get(0).tempo);
        fila1.fila++;
        fila1.feitos.get(0).fila = fila1.fila;
        tempo = inicial;

        System.out.println(fila1.feitos.get(0));

        while (true) {
            if (aleatorios.size() < 1) {
                break;
            }

            for (int i = 0; i < filas.size(); i++) {
                Evento ev = filas.get(i).sairEvento(aleatorios);
                if (ev != null) {
                    escalonador.add(ev);
                    if (aleatorios.size() < 1) {
                        break;
                    }
                }
            }

            if (aleatorios.size() < 1) {
                break;
            }
            if (chegadaLivre(escalonador)) {
                double formChegada = (chegada1 - chegada0) * aleatorios.remove(0) + chegada0
                        + tempo;
                escalonador.add(new Evento(formChegada, "chegada", fila1.tamFila, "fila1"));
            }

            if (aleatorios.size() < 1) {
                break;
            }

            int menor = menorTempoPos(escalonador);
            if (menor >= 0) {
                Evento evento = escalonador.remove(menor);
                if (evento.tipo.equals("chegada")) { // chegada fila1
                    Evento ultEvento = fila1.feitos.get(fila1.feitos.size() - 1);
                    copiaTempos(ultEvento, evento);
                    evento.temposX[ultEvento.fila] += evento.tempo - ultEvento.tempo;
                    tempo = evento.tempo;
                    if (fila1.fila < fila1.tamFila) {
                        fila1.fila++;
                    } else {
                        fila1.perdas++;
                    }
                    evento.fila = fila1.fila;
                    fila1.feitos.add(evento);
                }

                else { // saida fila qlqr
                    Fila f = buscarFila(evento.name);
                    Evento ultEvento = f.feitos.get(f.feitos.size() - 1);
                    copiaTempos(ultEvento, evento);
                    evento.temposX[ultEvento.fila] += evento.tempo - ultEvento.tempo;

                    f.serv--;
                    f.fila--;
                    evento.fila = f.fila;
                    f.feitos.add(evento);

                    if (f.saida.equals("saida")) {

                    } else {
                        Fila filaChegada = buscarFila(f.saida);
                        Evento novoEvento = new Evento(evento.tempo, filaChegada.name, filaChegada.tamFila,
                                filaChegada.saida);

                        if (filaChegada.fila < filaChegada.tamFila) {
                            filaChegada.fila++;
                        } else {
                            filaChegada.perdas++;
                        }
                        novoEvento.fila = filaChegada.fila;

                        if (filaChegada.feitos.size() == 0) {
                            novoEvento.temposX[0] = novoEvento.tempo;
                        } else {
                            Evento ultEvento2 = filaChegada.feitos.get(filaChegada.feitos.size() - 1);
                            copiaTempos(ultEvento2, novoEvento);
                            novoEvento.temposX[ultEvento2.fila] += novoEvento.tempo - ultEvento2.tempo;
                        }

                        filaChegada.feitos.add(novoEvento);
                    }

                }

                //System.out.println(evento);
                // evento.fila = fila;
                // tempo = evento.tempo;

                // feitos.add(evento);
                // System.out.println(evento);
            }
        }

        double maior = filas.get(0).getTempo();
        for (int i = 1; i < filas.size(); i++) {
            if (filas.get(i).getTempo() > maior) {
                maior = filas.get(i).getTempo(); 
            }
        }

        for (Fila f : filas) {
            if (maior > f.getTempo()) {
                Evento ev = f.feitos.get(f.feitos.size()-1);
                Evento novo = new Evento(f.tamFila);
                copiaTempos(ev, novo);
                novo.temposX[ev.fila] = ev.temposX[ev.fila] + (maior - ev.tempo);
                novo.tempo = maior;
                novo.tipo = "arrumar";
                f.feitos.add(novo);
            }
        }

        for (Fila f : filas) {
            System.out.println(f.feitos.get(f.feitos.size()-1));
            System.out.println("perdas = " + f.perdas);
        }

        // System.out.println(feitos.get(feitos.size() - 1));
        // System.out.println("perdas = " + perdas);
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
        ev2.temposX = Arrays.copyOf(ev1.temposX, ev1.temposX.length);
    }

    public static boolean chegadaLivre(List<Evento> escalonador) {
        for (Evento evento : escalonador) {
            if (evento.tipo == "chegada") {
                return false;
            }
        }
        return true;
    }

    public static Fila buscarFila(String name) {
        for (Fila fila : filas) {
            if (fila.name == name) {
                return fila;
            }
        }
        return null;
    }
}
