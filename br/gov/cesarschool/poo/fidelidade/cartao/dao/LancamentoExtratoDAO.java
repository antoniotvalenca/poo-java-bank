package br.gov.cesarschool.poo.fidelidade.cartao.dao;

import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtrato;
import br.gov.cesarschool.poo.fidelidade.geral.dao.DAOGenerico;

public class LancamentoExtratoDAO {
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String DIR_BASE = "." + FILE_SEP + "fidelidade" + FILE_SEP + "lancamento" + FILE_SEP;

    private DAOGenerico<LancamentoExtrato> daoEncapsulado;

    public LancamentoExtratoDAO() {
        daoEncapsulado = new DAOGenerico<>(DIR_BASE);
    }

    public boolean incluir(LancamentoExtrato extrato) {
        return daoEncapsulado.incluir(extrato);
    }

    public LancamentoExtrato[] buscarTodos() {
        Object[] objetos = daoEncapsulado.buscarTodos();
        LancamentoExtrato[] extratos = new LancamentoExtrato[objetos.length];
        for (int i = 0; i < objetos.length; i++) {
            extratos[i] = (LancamentoExtrato) objetos[i];
        }
        return extratos;
    }
}
