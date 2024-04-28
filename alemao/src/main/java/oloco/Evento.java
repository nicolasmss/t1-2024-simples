package oloco;

import java.util.Arrays;

public class Evento {
    String tipo; //saida ou chegada
    double tempo;
    double [] temposX;
    int tamX;
    int fila;
    String name;

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

    public Evento(int tamX) {
        temposX = new double[tamX+1];
    }

    public Evento(double tempo,String tipo, int tamX, String name) {
        if (tamX == -1) {
            tamX = 1000;
        }
        this.tempo = tempo;
        this.tipo = tipo;
        temposX = new double[tamX+1];
        for (int i = 0; i < temposX.length; i++) {
            temposX[i] = 0;
        }
        this.name = name;
    }

    public void setTempoX(int pos, double valor){
        temposX[pos] = valor;
    }

    public double getTempo() {
        return tempo;
    }

    @Override
    public String toString() {
        return "\nEvento [tipo=" + tipo + ", fila = " + fila + ", tempo = " + tempo + ", temposX=" + Arrays.toString(temposX) + "]";
    }

    public String print(){
        String lol =  "Tempo: " + tempo;
        for (int i = 0; i < temposX.length; i++) {
            if (temposX[i]>0) {
                lol += "\n" + i + "\t" + temposX[i];
            }
        }
        return lol;
    }

    public String print2(){
        String lol =  "Tempo: " + tempo + "\t" + name + "->" + tipo;
        for (int i = 0; i < temposX.length; i++) {
            if (temposX[i]>0) {
                lol += "\n" + i + "\t" + temposX[i];
            }
        }
        return lol;
    }
}
