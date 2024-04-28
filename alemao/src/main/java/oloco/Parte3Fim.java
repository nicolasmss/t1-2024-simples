package oloco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;

public class Parte3Fim {

    static double inicial;
    static double chegada0;
    static double chegada1;

    static List<Fila> filas = new ArrayList<Fila>();

    public static void leitura(){
        InputStream inputStream = LeituraYAML.class.getResourceAsStream("config.yml");
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);

        Map<String, Double> configuracoes = (Map<String, Double>) data.get("configuracoes");
        inicial = ((Number) configuracoes.get("inicial")).doubleValue();
        chegada0 = ((Number) configuracoes.get("chegada0")).doubleValue();
        chegada1 = ((Number) configuracoes.get("chegada1")).doubleValue();

        List<Map<String, Object>> filasAux = (List<Map<String, Object>>) data.get("filas");
        for (Map<String, Object> fila : filasAux) {
            String nome = (String) fila.get("nome");
            int tamFila = ((Number) fila.get("tamFila")).intValue();
            int tamServ = ((Number) fila.get("tamServ")).intValue();
            double atendimento0 = ((Number) fila.get("atendimento0")).doubleValue();
            double atendimento1 = ((Number) fila.get("atendimento1")).doubleValue();

            List<Saida> s2 = new ArrayList<Saida>();
            List<Map<String, Object>> saidas = (List<Map<String, Object>>) fila.get("saidas");
            if (saidas != null) {
                for (Map<String, Object> saida : saidas) {
                    String nomeSaida = (String) saida.get("nome");
                    double probabilidade = ((Number) saida.get("prob")).doubleValue();
                    s2.add(new Saida(nomeSaida, probabilidade));
                }
            }
            filas.add(new Fila(tamFila, tamServ, atendimento0, atendimento1, nome, s2));
        }
    }

    public static void main(String[] args) {
        leitura();
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


        while (true) {
            if (aleatorios.size() < 1) {
                break;
            }

            for (int i = 0; i < filas.size(); i++) { //registra saidas
                Evento ev = filas.get(i).sairEvento2(aleatorios);
                if (aleatorios.size() < 1) {
                    break;
                }
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
            if (chegadaLivre(escalonador)) { //registra chegadas
                double formChegada = (chegada1 - chegada0) * aleatorios.remove(0) + chegada0
                        + tempo;
                escalonador.add(new Evento(formChegada, "chegada", fila1.tamFila, "fila1"));
            }

            if (aleatorios.size() < 1) {
                break;
            }

            /*
            // print do escalonador para cada estado usando debugger
            System.out.println("escalonador");
            for (Evento te : escalonador) {
                System.out.println(te.print2());
            }//*/

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

                    if (evento.tipo.equals("saida")) {

                    } else {
                        Fila filaChegada = buscarFila(evento.tipo);
                        Evento novoEvento = new Evento(evento.tempo, filaChegada.name, filaChegada.tamFila,
                                evento.tipo);

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
            }
            /* 
            // print das filas para cada momento usando debugger
            for (Fila f : filas) {
                if(f.feitos.size()>0){
                    System.out.println(100000 - aleatorios.size());
                    System.out.println(f.feitos.get(f.feitos.size()-1).print()+ "\tnome: " + f.name +  "\tfila: " + f.fila);
                    System.out.println("perdas: "+f.perdas);
                }
            }
            System.out.println();// */
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
            System.out.println(f.feitos.get(f.feitos.size()-1).print());
            System.out.println("perdas = " + f.perdas);
        }

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
            if (evento.tipo.equals("chegada")) {
                return false;
            }
        }
        return true;
    }

    public static Fila buscarFila(String name) {
        for (Fila fila : filas) {
            if (fila.name.equals(name)) {
                return fila;
            }
        }
        return null;
    }
}
