import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File operacoes = new File("D:\\Arquivos\\Projetos\\Projetos\\Projetos IntelliJ\\calculadora-saldo-bancario\\src\\data\\operacoes.csv");
            Scanner scanner = new Scanner(operacoes);

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: " + e);
        }
    }
}
