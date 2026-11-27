    package br.com.sistema.model;

    import java.security.GeneralSecurityException;
    import java.security.SecureRandom;
    import java.sql.Statement;
    import java.util.Base64;
    import javax.crypto.SecretKeyFactory;
    import javax.crypto.spec.PBEKeySpec;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class UsuarioModel {

        public int adicionarUsuario(String nome, char[] senha)
                throws SQLException, GeneralSecurityException {
            String senhaHash = gerarHash(senha);
            String sql = "INSERT INTO usuarios (nome, senha_hash) VALUES (?, ?)";
            try (Connection conn = ConexaoBanco.getConexao();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nome);
                stmt.setString(2, senhaHash);
                stmt.executeUpdate();

                try (ResultSet chaves = stmt.getGeneratedKeys()) {
                    if (chaves.next()) {
                        return chaves.getInt(1);
                    }
                }

                throw new SQLException("O banco n„o retornou o ID do cadastro.");
            }
        }

        private String gerarHash(char[] senha) throws GeneralSecurityException {
            int iteracoes = 600_000;
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            PBEKeySpec parametros = new PBEKeySpec(senha, salt, iteracoes, 256);
            try {
                byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(parametros).getEncoded();
                Base64.Encoder base64 = Base64.getEncoder();
                return iteracoes + ":" + base64.encodeToString(salt) + ":" + base64.encodeToString(hash);
            } finally {
                parametros.clearPassword();
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
                System.err.println("Erro ao contar usu√°rios: " + e.getMessage());
        }
            return 0;
        }
    }