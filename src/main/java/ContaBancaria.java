import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaBancaria {
    private String id;
    private String banco;
    private int agencia;
    private String conta;

    public ContaBancaria(String id, String banco, int agencia, String conta) {
        this.id = id;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
    }
}
