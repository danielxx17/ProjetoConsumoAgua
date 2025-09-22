package Db;

import model.ConsumoAgua;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static final String DB_URL = System.getenv("DATABASE_URL") != null ? 
        System.getenv("DATABASE_URL") : 
        "jdbc:postgresql://localhost:5432/consumo_agua";
    
    private static final String DB_USER = System.getenv("PGUSER") != null ? 
        System.getenv("PGUSER") : "postgres";
        
    private static final String DB_PASSWORD = System.getenv("PGPASSWORD") != null ? 
        System.getenv("PGPASSWORD") : "";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL não encontrado: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public static void criarTabelas() {
        String sqlTabelaConsumo = """
            CREATE TABLE IF NOT EXISTS consumo_agua (
                id SERIAL PRIMARY KEY,
                nome_uso VARCHAR(255) NOT NULL,
                consumo_litros DECIMAL(10,2) NOT NULL,
                usa_reuso BOOLEAN NOT NULL,
                categoria VARCHAR(100) NOT NULL,
                eficiencia DECIMAL(5,2) NOT NULL DEFAULT 0,
                data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sqlTabelaConsumo);
            System.out.println("Tabela 'consumo_agua' criada com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public static boolean salvarConsumo(ConsumoAgua consumo) {
        String sql = """
            INSERT INTO consumo_agua (nome_uso, consumo_litros, usa_reuso, categoria, eficiencia) 
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, consumo.getNomeUso());
            pstmt.setDouble(2, consumo.getConsumoLitros());
            pstmt.setBoolean(3, consumo.isUsaReuso());
            pstmt.setString(4, consumo.getCategoria());
            pstmt.setDouble(5, consumo.getEficiencia());
            
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar consumo: " + e.getMessage());
            return false;
        }
    }

    public static List<ConsumoAgua> buscarTodosConsumos() {
        List<ConsumoAgua> consumos = new ArrayList<>();
        String sql = "SELECT * FROM consumo_agua ORDER BY data_criacao DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                ConsumoAgua consumo = new ConsumoAgua(
                    rs.getString("nome_uso"),
                    rs.getDouble("consumo_litros"),
                    rs.getBoolean("usa_reuso"),
                    rs.getString("categoria"),
                    rs.getDouble("eficiencia")
                );
                consumos.add(consumo);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consumos: " + e.getMessage());
        }
        
        return consumos;
    }

    public static List<ConsumoAgua> buscarConsumosPorCategoria(String categoria) {
        List<ConsumoAgua> consumos = new ArrayList<>();
        String sql = "SELECT * FROM consumo_agua WHERE categoria = ? ORDER BY data_criacao DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categoria);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ConsumoAgua consumo = new ConsumoAgua(
                    rs.getString("nome_uso"),
                    rs.getDouble("consumo_litros"),
                    rs.getBoolean("usa_reuso"),
                    rs.getString("categoria"),
                    rs.getDouble("eficiencia")
                );
                consumos.add(consumo);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consumos por categoria: " + e.getMessage());
        }
        
        return consumos;
    }

    public static boolean atualizarConsumo(int id, ConsumoAgua consumo) {
        String sql = """
            UPDATE consumo_agua 
            SET nome_uso = ?, consumo_litros = ?, usa_reuso = ?, categoria = ?, 
                eficiencia = ?, data_atualizacao = CURRENT_TIMESTAMP 
            WHERE id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, consumo.getNomeUso());
            pstmt.setDouble(2, consumo.getConsumoLitros());
            pstmt.setBoolean(3, consumo.isUsaReuso());
            pstmt.setString(4, consumo.getCategoria());
            pstmt.setDouble(5, consumo.getEficiencia());
            pstmt.setInt(6, id);
            
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar consumo: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletarConsumo(int id) {
        String sql = "DELETE FROM consumo_agua WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao deletar consumo: " + e.getMessage());
            return false;
        }
    }

    public static double calcularConsumoTotal() {
        String sql = "SELECT SUM(consumo_litros) as total FROM consumo_agua";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao calcular consumo total: " + e.getMessage());
        }
        
        return 0.0;
    }

    public static double calcularEconomiaTotal() {
        String sql = """
            SELECT SUM(consumo_litros * (eficiencia / 100)) as economia_total 
            FROM consumo_agua
        """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("economia_total");
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao calcular economia total: " + e.getMessage());
        }
        
        return 0.0;
    }

    public static boolean testarConexao() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Erro ao testar conexão: " + e.getMessage());
            return false;
        }
    }
}