import java.util.Arrays;

public class Evento {
    String tipo; //saida ou chegada
    double tempo;
    double [] temposX;
    int tamX;
    int fila;

    public Evento(double tempo,String tipo, int tamX) {
        if (tamX == -1) {
            tamX = 1000;
        }
        this.tempo = tempo;
        this.tipo = tipo;
        temposX = new double[tamX+1];
        for (int i = 0; i < temposX.length; i++) {
            temposX[i] = 0;
        }
    }

    public void setTempoX(int pos, double valor){
        temposX[pos] = valor;
    }

    public double getTempo() {
        return tempo;
    }

    @Override
    public String toString() {
        return "\nEvento [tipo=" + tipo + "fila = " + fila + ", tempo = " + tempo + ", temposX=" + Arrays.toString(temposX) + "]";
    }
}
