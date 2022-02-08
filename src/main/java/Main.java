import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\Arquivos\\Projetos\\Projetos\\Projetos IntelliJ\\calculadora-saldo-bancario\\src\\data\\operacoes.csv";
        List<OperacaoBancaria> registros = lerConteudoDoArquivo(path);
    }

    public static List<OperacaoBancaria> lerConteudoDoArquivo(String path) {
        List<OperacaoBancaria> registros = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(path);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                ArrayList<String> info = new ArrayList<>();
                for (String cell : nextRecord) {
                    info.add(cell);
                    System.out.print(cell + "\t");
                }

                registros.add(converterArrayEmOperacaoBancaria(info));
                System.out.println();
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

        return registros;
    }

    public static OperacaoBancaria converterArrayEmOperacaoBancaria(ArrayList<String> dados) {
        String id = dados.get(1);
        String banco = dados.get(2);
        int agencia = Integer.parseInt(dados.get(3));
        String conta = dados.get(4);
        ContaBancaria contaBancaria = new ContaBancaria(id, banco, agencia, conta);

        String operador = dados.get(5);
        TipoOperacao tipo = TipoOperacao.valueOf(dados.get(6));
        float valor = Float.parseFloat(dados.get(7));
        String data = dados.get(0);

        return new OperacaoBancaria(operador, tipo, valor, data, contaBancaria);
    }
}
