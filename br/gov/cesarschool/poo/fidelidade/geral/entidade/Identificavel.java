package br.gov.cesarschool.poo.fidelidade.geral.entidade;

import java.io.Serializable;

public abstract class Identificavel implements Serializable {
    public abstract String obterChave();
}