package br.com.sistema.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CadastroView extends JFrame {
	private final JTextField txtNome = new JTextField(15);
	private final JButton btnCadastrar = new JButton("Cadastrar");
	private final JLabel lblStatus = new JLabel(" ");
	private final DefaultTableModel modeloTabela = new DefaultTableModel();

	public CadastroView() {
		setTitle("Sistema de Cadastro - Java Swing + MySQL");
		setSize(480, 380);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		JPanel painelEntrada = new JPanel(new FlowLayout());
		painelEntrada.add(new JLabel("Nome:"));
		painelEntrada.add(txtNome);
		painelEntrada.add(btnCadastrar);

		modeloTabela.addColumn("Nº");
		modeloTabela.addColumn("Nome Cadastrado");
		JTable tabela = new JTable(modeloTabela);

		add(painelEntrada, BorderLayout.NORTH);
		add(new JScrollPane(tabela), BorderLayout.CENTER);
		add(lblStatus, BorderLayout.SOUTH);
	}

	public String getNomeDigitado() {
		return txtNome.getText();
	}

	public void limparCampo() {
		txtNome.setText("");
		txtNome.requestFocus();
	}

	public void adicionarLinha(int id, String nome) {
		modeloTabela.addRow(new Object[]{id, nome});
	}

	public void exibirMensagem(String msg, Color cor) {
		lblStatus.setForeground(cor);
		lblStatus.setText(msg);
	}

	public void addCadastrarListener(ActionListener listener) {
		btnCadastrar.addActionListener(listener);
	}
}