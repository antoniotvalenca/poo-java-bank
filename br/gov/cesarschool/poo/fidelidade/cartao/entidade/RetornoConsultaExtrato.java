package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RetornoConsultaExtrato {
    private LancamentoExtrato[] lancamentos;
    private String mensagemErro;
}
