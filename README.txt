txt apenas para informação valores alterados aqui não mudarão nada

para fazer uma fila simples usar Parte1Simples altera os valores abaixo no arquivo:
    static double inicial = 2.0;
    static int tamFila = 5;
    static double chegada0 = 2;
    static double chegada1 = 5;
    static int tamServ = 1;
    static double atendimento0 = 3;
    static double atendimento1 = 5;

para fazer uma fila em tandem usar Parte2Tandem e alterar os valores abaixo no arquivo:
    static double inicial = 1.5;
    static double chegada0 = 1;
    static double chegada1 = 4;

    // para cada fila que deve ser implementada deve se adicionar mais uma na list de filas
    public static void popular() {
        // filas.add(new Fila(tamFila, tamServ, atendimento0, atendimento1, name, saida)) *não alterar nome da fila1* fila1 sempre recebera as chegadas
        filas.add(new Fila(3, 2, 3, 4, "fila1", "fila2"));// não altere name da fila1
        filas.add(new Fila(5, 1, 2, 3, "fila2", "saida"));// saida é a saida final
    }