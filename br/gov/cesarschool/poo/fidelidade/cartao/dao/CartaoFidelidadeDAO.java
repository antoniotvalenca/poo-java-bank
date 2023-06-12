package br.gov.cesarschool.poo.fidelidade.cartao.dao;

import br.gov.cesarschool.poo.fidelidade.cartao.entidade.CartaoFidelidade;
import br.gov.cesarschool.poo.fidelidade.geral.dao.DAOGenerico;

public class CartaoFidelidadeDAO {
    private static final String DIR_BASE = "." + System.getProperty("file.separator") + "fidelidade" +
            System.getProperty("file.separator") + "cartao";

    private DAOGenerico daoEncapsulado;

    public CartaoFidelidadeDAO() {
        daoEncapsulado = new DAOGenerico(DIR_BASE);
    }

    public boolean incluir(CartaoFidelidade cartao) {
        return daoEncapsulado.incluir(cartao);
    }

    public boolean alterar(CartaoFidelidade cartao) {
        return daoEncapsulado.alterar(cartao);
    }

    public CartaoFidelidade buscar(String numero) {
        String numeroString = numero;
        return (CartaoFidelidade) daoEncapsulado.buscar(numeroString);
    }
}
