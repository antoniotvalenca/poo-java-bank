package br.gov.cesarschool.poo.fidelidade.cartao.dao;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.CartaoFidelidade;
import java.util.ArrayList;
import java.util.List;

public class CartaoFidelidadeDAO {

    private List<CartaoFidelidade> cartoes;

    public CartaoFidelidadeDAO() {
        this.cartoes = new ArrayList<>();
    }

    public boolean incluir(CartaoFidelidade cartao) {
        if (buscar(cartao.getNumero()) != null) {
            return false;
        }
        cartoes.add(cartao);
        return true;
    }

    public boolean alterar(CartaoFidelidade cartao) {
        return false;
    }

    public CartaoFidelidade buscar(long numero) {
        for (CartaoFidelidade cartao : cartoes) {
            if (cartao.getNumero() == numero) {
                return cartao;
            }
        }
        return null;
    }
}