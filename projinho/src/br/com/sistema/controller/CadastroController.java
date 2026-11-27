package br.com.sistema.controller;

import br.com.sistema.model.UsuarioModel;
import br.com.sistema.view.CadastroView;
import java.awt.Color;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Arrays;

public class CadastroController {
    public CadastroController(UsuarioModel model, CadastroView view) {
        view.addCadastrarListener(e -> {
            String nome = view.getNomeDigitado().trim();

            if (nome.isEmpty()) {
                view.exibirMensagem("Digite um nome.", Color.RED);
                return;
            }

            char[] senha = view.getSenhaDigitada();
            try {
                if (senha.length == 0) {
                    view.exibirMensagem("Digite uma senha.", Color.RED);
                    return;
                }

                int id = model.adicionarUsuario(nome, senha);
                view.adicionarLinha(id, nome);
                view.limparCampo();
                view.exibirMensagem("Cadastro concluído.", new Color(0, 128, 0));
            } catch (SQLException ex) {
                System.err.println("Erro ao cadastrar: " + ex.getMessage());
                view.exibirMensagem("Falha ao salvar. Confira o MySQL e o banco.", Color.RED);
            } catch (GeneralSecurityException ex) {
                System.err.println("Erro ao proteger a senha: " + ex.getMessage());
                view.exibirMensagem("Falha ao proteger a senha.", Color.RED);
            } finally {
                Arrays.fill(senha, '\0');
            }
        });
    }
}