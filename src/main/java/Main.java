import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\Arquivos\\Projetos\\Projetos\\Projetos IntelliJ\\calculadora-saldo-bancario\\src\\data\\operacoes.csv";

        try {
            FileReader fileReader = new FileReader(path);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }

        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }
}
