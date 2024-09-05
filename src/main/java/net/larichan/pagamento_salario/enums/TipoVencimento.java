package net.larichan.pagamento_salario.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoVencimento {
    CREDITO("Crédito"),
    DEBITO("Débito");

    private final String descricao;
}
