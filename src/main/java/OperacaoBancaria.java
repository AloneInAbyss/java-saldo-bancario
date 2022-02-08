import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class OperacaoBancaria {
    private String operador;
    private TipoOperacao tipo;
    private float valor;
    private Date data;
    private ContaBancaria conta;

    public OperacaoBancaria(String operador, TipoOperacao tipo, float valor, String data, ContaBancaria conta) {
        this.operador = operador;
        this.tipo = tipo;
        this.valor = valor;
        this.conta = conta;

        Date dataFormatada = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            dataFormatada = formatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.data = dataFormatada;
    }
}

enum TipoOperacao {
    SAQUE, DEPOSITO
}