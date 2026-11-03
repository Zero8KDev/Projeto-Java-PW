    package br.com.sistema.model;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class UsuarioModel {

        public void adicionarUsuario(String nome) {
            String sql = "INSERT INTO usuarios (nome) VALUES (?)";
            try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome.trim());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao inserir: " + e.getMessage());
        }
        }

        public List<String> getUsuarios() {
            List<String> lista = new ArrayList<>();
            String sql = "SELECT nome FROM usuarios ORDER BY id ASC";
            try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("nome"));
            }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar: " + e.getMessage());
        }
            return lista;
        }

        public int getTotalUsuarios() {
            String sql = "SELECT COUNT(*) FROM usuarios";
            try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
            }
            } catch (SQLException e) {
                System.err.println("Erro ao contar usuários: " + e.getMessage());
        }
            return 0;
        }
    }