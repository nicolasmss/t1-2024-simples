package oloco;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;
import java.util.List;

public class LeituraYAML {
    public static void main(String[] args) {
        // Carrega o arquivo YAML como um InputStream
        InputStream inputStream = LeituraYAML.class.getResourceAsStream("config.yml");

        // Cria um objeto Yaml
        Yaml yaml = new Yaml();

        // Faz o parsing do arquivo YAML para um Map
        Map<String, Object> data = yaml.load(inputStream);

        // Acessa as configurações
        Map<String, Double> configuracoes = (Map<String, Double>) data.get("configuracoes");
        double inicial = ((Number) configuracoes.get("inicial")).doubleValue();
        double chegada0 = ((Number) configuracoes.get("chegada0")).doubleValue();
        double chegada1 = ((Number) configuracoes.get("chegada1")).doubleValue();        
        System.out.println("Configurações:");
        System.out.println("Inicial: " + inicial);
        System.out.println("Chegada0: " + chegada0);
        System.out.println("Chegada1: " + chegada1);

        // Acessa as filas
        List<Map<String, Object>> filas = (List<Map<String, Object>>) data.get("filas");
        System.out.println("\nFilas:");
        for (Map<String, Object> fila : filas) {
            String nome = (String) fila.get("nome");
            int tamFila = ((Number) fila.get("tamFila")).intValue();
            int tamServ = ((Number) fila.get("tamServ")).intValue();
            int atendimento0 = ((Number) fila.get("atendimento0")).intValue();
            int atendimento1 = ((Number) fila.get("atendimento1")).intValue();
            System.out.println("Nome: " + nome);
            System.out.println("Tamanho da Fila: " + tamFila);
            System.out.println("Tamanho do Servidor: " + tamServ);
            System.out.println("Atendimento0: " + atendimento0);
            System.out.println("Atendimento1: " + atendimento1);

            // Acessa as saídas da fila, se houver
            List<Map<String, Object>> saidas = (List<Map<String, Object>>) fila.get("saidas");
            if (saidas != null) {
                System.out.println("Saídas:");
                for (Map<String, Object> saida : saidas) {
                    String nomeSaida = (String) saida.get("nome");
                    double probabilidade = ((Number) saida.get("prob")).doubleValue();
                    System.out.println("- Nome: " + nomeSaida + ", Probabilidade: " + probabilidade);
                }
            }
            System.out.println();
        }
    }

    public void leitura(){
        // Carrega o arquivo YAML como um InputStream
        InputStream inputStream = LeituraYAML.class.getResourceAsStream("config.yml");

        // Cria um objeto Yaml
        Yaml yaml = new Yaml();

        // Faz o parsing do arquivo YAML para um Map
        Map<String, Object> data = yaml.load(inputStream);

        // Acessa as configurações
        Map<String, Double> configuracoes = (Map<String, Double>) data.get("configuracoes");
        double inicial = ((Number) configuracoes.get("inicial")).doubleValue();
        double chegada0 = ((Number) configuracoes.get("chegada0")).doubleValue();
        double chegada1 = ((Number) configuracoes.get("chegada1")).doubleValue();

        // Acessa as filas
        List<Map<String, Object>> filas = (List<Map<String, Object>>) data.get("filas");
        System.out.println("\nFilas:");
        for (Map<String, Object> fila : filas) {
            String nome = (String) fila.get("nome");
            int tamFila = ((Number) fila.get("tamFila")).intValue();
            int tamServ = ((Number) fila.get("tamServ")).intValue();
            int atendimento0 = ((Number) fila.get("atendimento0")).intValue();
            int atendimento1 = ((Number) fila.get("atendimento1")).intValue();

            // Acessa as saídas da fila, se houver
            List<Map<String, Object>> saidas = (List<Map<String, Object>>) fila.get("saidas");
            if (saidas != null) {
                System.out.println("Saídas:");
                for (Map<String, Object> saida : saidas) {
                    String nomeSaida = (String) saida.get("nome");
                    double probabilidade = ((Number) saida.get("prob")).doubleValue();
                }
            }
            System.out.println();
        }
    }
}
