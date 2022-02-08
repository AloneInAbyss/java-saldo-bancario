import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperacaoBancaria {
    private String operador;
    private TipoOperacao tipo;
    private float valor;
    private String data;
    private ContaBancaria conta;

    public OperacaoBancaria(String operador, TipoOperacao tipo, float valor, String data, ContaBancaria conta) {
        this.operador = operador;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.conta = conta;
    }
}

enum TipoOperacao {
    SAQUE, DEPOSITO
}