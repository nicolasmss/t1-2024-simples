package oloco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fila {
    int tamFila;
    int tamServ;
    double atendimento0;
    double atendimento1;
    List<Evento> feitos = new ArrayList<Evento>();
    int fila = 0;
    int serv = 0;
    String name;
    String saida;
    List<Saida> saidas;
    int perdas = 0;

    public Fila(int tamFila, int tamServ, double atendimento0, double atendimento1, String name, String saida) {
        this.tamFila = tamFila;
        this.tamServ = tamServ;
        this.atendimento0 = atendimento0;
        this.atendimento1 = atendimento1;
        this.name = name;
        this.saida = saida;
        if (tamFila==-1) {
            this.tamFila = 1000; 
        }
    }

    public Fila(int tamFila, int tamServ, double atendimento0, double atendimento1, String name,List<Saida> saidas) {
        this.tamFila = tamFila;
        this.tamServ = tamServ;
        this.atendimento0 = atendimento0;
        this.atendimento1 = atendimento1;
        this.name = name;
        this.saidas = saidas;
        if (tamFila==-1) {
            this.tamFila = 1000; 
        }
    }

    public Evento sairEvento(List<Double> aleatorios) {
        if (fila >= 1 && serv < tamServ && serv < fila) {
            double formSaida = (atendimento1 - atendimento0) * aleatorios.remove(0) + atendimento0
                    + feitos.get(feitos.size() - 1).tempo;
            serv++;
            return (new Evento(formSaida, saida, tamFila, name));
        }else{
            return null;
        }
    }

    public Evento sairEvento2(List<Double> aleatorios) {
        if (fila >= 1 && serv < tamServ && serv < fila) {
            String saidaEv = "";
            if (saidas.size()>1) {
                double a = aleatorios.remove(0);
                double sum =  0;
                for (int i = 0; i < saidas.size(); i++) {
                    sum += saidas.get(i).porcentagem;
                    if (a <= sum) {
                        saidaEv = saidas.get(i).nome;
                        break;
                    }
                }
            }else{
                saidaEv = saidas.get(0).nome;
            }

            if (aleatorios.size() < 1) {
                return null;
            }

            double formSaida = (atendimento1 - atendimento0) * aleatorios.remove(0) + atendimento0
                    + feitos.get(feitos.size() - 1).tempo;
            serv++;
            return (new Evento(formSaida, saidaEv, tamFila, name));
        }else{
            return null;
        }
    }

    public void copiaTempos(Evento ev1, Evento ev2) {
        ev2.temposX = Arrays.copyOf(ev1.temposX, tamFila + 1);
    }

    public double getTempo(){
        return feitos.get(feitos.size()-1).tempo;
    }

    @Override
    public String toString() {
        return "Fila{" +
                "tamFila=" + tamFila +
                ", tamServ=" + tamServ +
                ", feitos=" + feitos +
                ", fila=" + fila +
                ", serv=" + serv +
                ", name='" + name + '\'' +
                '}';
    }
}
