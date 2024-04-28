package oloco;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CongruenteLinearA1 {
    private long x0;
    private long a;
    private long c;

    private long m;

    public CongruenteLinearA1(long x0, long a, long c, long m) {
        this.x0 = x0;
        this.a = a;
        this.c = c;
        this.m = m;
    }

    public double nextRandom() {
        x0 = (a * x0 + c) % m;
        return (double) x0 / m;
    }

    public List<Double> preDef() {
        List<Double> lista = new ArrayList<>();
        lista.add(0.3276);
        lista.add(0.8851);
        lista.add(0.1643);
        lista.add(0.5542);
        lista.add(0.6813);
        lista.add(0.7221);
        lista.add(0.9881);
        return lista;
    }

    public List<Double> gerarX(int x){
        long x0 = 12;
        long a = 123;
        long c = 456;
        long m = 1597531591;
        int count = x;

        List<Double> lista = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            double random = nextRandom();
            lista.add(random);
        }

        return lista;
    }

    public static void main(String[] args) {
        long x0 = 12;
        long a = 123;
        long c = 456;
        long m = 1597531591;
        int count = 1000;

        CongruenteLinearA1 congruenteLinear = new CongruenteLinearA1(x0, a, c, m);

        try {
            FileWriter fileWriter = new FileWriter("aleatorios.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int i = 0; i < count; i++) {
                double random = congruenteLinear.nextRandom();
                printWriter.println(random);
            }

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
