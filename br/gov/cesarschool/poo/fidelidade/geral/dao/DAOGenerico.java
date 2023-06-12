package br.gov.cesarschool.poo.fidelidade.geral.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import br.gov.cesarschool.poo.fidelidade.geral.entidade.Identificavel;

public class DAOGenerico<T extends Identificavel> {
    private String diretorioBase;

    public DAOGenerico(String diretorioBase) {
        this.diretorioBase = diretorioBase;
    }

    private File getArquivo(String chave) {
        String caminhoArquivo = diretorioBase + File.separator + chave;
        return new File(caminhoArquivo);
    }

    private File getArquivo(T ident) {
        String chave = ident.obterChave();
        return getArquivo(chave);
    }

    private void incluirAux(T ident, File arq) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arq))) {
            out.writeObject(ident);
            System.out.println("Objeto incluído com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao incluir o objeto.");
            e.printStackTrace();
        }
    }

    public boolean incluir(T ident) {
        File arq = getArquivo(ident);
        if (arq.exists()) {
            System.out.println("Objeto já existe.");
            return false;
        }
        incluirAux(ident, arq);
        return true;
    }

    public boolean alterar(T ident) {
        File arq = getArquivo(ident);
        if (!arq.exists()) {
            System.out.println("Objeto não encontrado.");
            return false;
        }
        incluirAux(ident, arq);
        return true;
    }

    public T buscar(String chave) {
        File arq = getArquivo(chave);
        if (!arq.exists()) {
            System.out.println("Objeto não encontrado.");
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arq))) {
            T ident = (T) in.readObject();
            return ident;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao buscar o objeto.");
            e.printStackTrace();
            return null;
        }
    }

    public Identificavel[] buscarTodos() {
        File diretorio = new File(diretorioBase);
        if (!diretorio.exists() || !diretorio.isDirectory()) {
            return new Identificavel[0];
        }

        List<Identificavel> identificaveis = new ArrayList<>();
        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
                        T ident = (T) in.readObject();
                        identificaveis.add(ident);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException("Erro ao ler o objeto do arquivo.", e);
                    }
                }
            }
        }

        return identificaveis.toArray(new Identificavel[0]);
    }
}
