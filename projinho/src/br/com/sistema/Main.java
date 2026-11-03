package br.com.sistema;

import br.com.sistema.controller.CadastroController;
import br.com.sistema.model.UsuarioModel;
import br.com.sistema.view.CadastroView;
import javax.swing.SwingUtilities;
public class Main {
public static void main(String[] args) {
SwingUtilities.invokeLater(new Runnable() {
@Override
public void run() {
// Instanciação das três camadas
UsuarioModel model = new UsuarioModel();
CadastroView view = new CadastroView();
// Conectando tudo via Controller
new CadastroController(model, view);
// Torna a janela visível
view.setVisible(true);
}
});
}
}