import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\Arquivos\\Projetos\\Projetos\\Projetos IntelliJ\\calculadora-saldo-bancario\\src\\input\\operacoes.csv";
        String pathExtratos = "D:\\Arquivos\\Projetos\\Projetos\\Projetos IntelliJ\\calculadora-saldo-bancario\\src\\output";

        List<OperacaoBancaria> registros = lerConteudoDoArquivo(path);

        HashMap<String, List<OperacaoBancaria>> operacoesPorId = separarOperacoesPorConta(registros);

        ordenarPorData(operacoesPorId);

        HashMap<String, Float> saldos = gerarSaldos(operacoesPorId);

        gerarExtratos(operacoesPorId, saldos, pathExtratos);
    }

    public static List<OperacaoBancaria> lerConteudoDoArquivo(String path) {
        List<OperacaoBancaria> registros = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(path);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();

            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                ArrayList<String> info = new ArrayList<>();
                Collections.addAll(info, nextRecord);

                registros.add(converterArrayEmOperacaoBancaria(info));
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

    public static HashMap<String, List<OperacaoBancaria>> separarOperacoesPorConta(List<OperacaoBancaria> registros) {
        HashMap<String, List<OperacaoBancaria>> operacoesPorId = new HashMap<>();

        for (OperacaoBancaria operacao : registros) {
            String id = operacao.getConta().getId();
            ArrayList<OperacaoBancaria> arrayList;

            if (!operacoesPorId.containsKey(id)) {
                arrayList = new ArrayList<>();
            } else {
                arrayList = (ArrayList<OperacaoBancaria>) operacoesPorId.get(id);
            }

            arrayList.add(operacao);
            operacoesPorId.put(id, arrayList);
        }

        return operacoesPorId;
    }

    public static void ordenarPorData(HashMap<String, List<OperacaoBancaria>> operacoesPorId) {
        for (List<OperacaoBancaria> lista : operacoesPorId.values()) {
            lista.sort((o1, o2) -> {
                if (o1.getData() == null || o2.getData() == null)
                    return 0;
                return o1.getData().compareTo(o2.getData());
            });
        }
    }

    public static HashMap<String, Float> gerarSaldos(HashMap<String, List<OperacaoBancaria>> operacoesPorId) {
        HashMap<String, Float> saldos = new HashMap<>();

        for (List<OperacaoBancaria> lista : operacoesPorId.values()) {
            float saldo = 0;
            for (OperacaoBancaria registro : lista) {
                if (registro.getTipo() == TipoOperacao.DEPOSITO) {
                    saldo += registro.getValor();
                } else if (registro.getTipo() == TipoOperacao.SAQUE) {
                    saldo -= registro.getValor();
                }
            }

            saldos.put(lista.get(0).getConta().getId(), saldo);
        }

        return saldos;
    }

    public static void gerarExtratos(HashMap<String, List<OperacaoBancaria>> operacoesPorId, HashMap<String, Float> saldos, String path) {
        new File(path).mkdir();
        System.out.println("Arquivos salvos em: " + path + "\n");

        for (List<OperacaoBancaria> lista : operacoesPorId.values()) {
            String filename = lista.get(0).getConta().getId();
            try {
                File extrato = new File(path + "\\" + filename + ".txt");
                BufferedWriter br = new BufferedWriter(new FileWriter(extrato, false));

                for (OperacaoBancaria registro : lista) {
                    System.out.print(registro.getData() + "\t");
                    System.out.print(registro.getConta().getId() + "\t");
                    System.out.print(registro.getConta().getBanco() + "\t");
                    System.out.print(registro.getConta().getAgencia() + "\t");
                    System.out.print(registro.getConta().getConta() + "\t");
                    System.out.print(registro.getOperador() + "\t");
                    System.out.print(registro.getTipo() + "\t");
                    System.out.print(registro.getValor() + "\n");

                    br.write(registro.getData() + "\t");
                    br.write(registro.getConta().getId() + "\t");
                    br.write(registro.getConta().getBanco() + "\t");
                    br.write(registro.getConta().getAgencia() + "\t");
                    br.write(registro.getConta().getConta() + "\t");
                    br.write(registro.getOperador() + "\t");
                    br.write(registro.getTipo() + "\t");
                    br.write(registro.getValor() + "\n");
                }
                System.out.println("Saldo: R$" + saldos.get(lista.get(0).getConta().getId()));
                System.out.println();

                br.write("Saldo: R$" + saldos.get(lista.get(0).getConta().getId()));
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
