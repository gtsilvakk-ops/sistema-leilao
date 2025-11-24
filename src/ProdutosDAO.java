import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    public void cadastrarProduto(ProdutosDTO produto) {
        try {
            // 1. Conectar ao banco
            conn = new conectaDAO().connectDB();
            
            // 2. Criar o comando SQL
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            
            // 3. Preparar o comando
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            // 4. Executar
            prep.executeUpdate();
            
            // 5. Mensagem de sucesso
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            
            // 6. Fechar conexão
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        try {
            // 1. Conectar ao banco
            conn = new conectaDAO().connectDB();
            
            // 2. Criar comando SQL
            String sql = "SELECT * FROM produtos";
            
            // 3. Executar
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            // 4. Processar resultados
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
            // 5. Fechar conexão
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return listagem;
    }
    
    // === NOVO MÉTODO 1: VENDER PRODUTO ===
    public void venderProduto(int id) {
        try {
            // 1. Conectar ao banco
            conn = new conectaDAO().connectDB();
            
            // 2. SQL para atualizar status
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            
            // 3. Preparar statement
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            
            // 4. Executar
            int linhasAfetadas = prep.executeUpdate();
            
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            // 5. Fechar conexão
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // === NOVO MÉTODO 2: LISTAR PRODUTOS VENDIDOS ===
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        try {
            // 1. Conectar ao banco
            conn = new conectaDAO().connectDB();
            
            // 2. SQL para buscar apenas vendidos
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            
            // 3. Executar query
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            // 4. Processar resultados
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
            // 5. Fechar conexão
            resultset.close();
            prep.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return listagem;
    }
}