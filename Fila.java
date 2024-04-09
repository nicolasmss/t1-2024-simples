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
    int perdas = 0;

    public Fila(int tamFila, int tamServ, double atendimento0, double atendimento1, String name, String saida) {
        this.tamFila = tamFila;
        this.tamServ = tamServ;
        this.atendimento0 = atendimento0;
        this.atendimento1 = atendimento1;
        this.name = name;
        this.saida = saida;
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
