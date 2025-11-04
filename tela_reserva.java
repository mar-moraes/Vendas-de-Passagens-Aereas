// O seu pacote

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Um JPanel que recria a tela "Minhas Reservas" usando Java Swing.
 * @author eumes
 */
public class tela_reserva extends javax.swing.JPanel { // Note que o nome da classe é o seu

    private JTable tabelaReservas;
    private DefaultTableModel modelTabela;

    /**
     * Creates new form tela_reserva
     */
    public tela_reserva() { // <<< ESSA É A ÚNICA LINHA QUE MUDAMOS
        // 1. Configuração principal do Painel
        setLayout(new BorderLayout(10, 10)); // Gaps entre as seções
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Margem em volta do painel

        // 2. Adiciona as seções
        add(criarPainelTopo(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);
    }

    /**
     * Cria o cabeçalho (Logo, Menu) e a seção de título e busca.
     */
    private JPanel criarPainelTopo() {
        // Painel principal do topo, que conterá duas linhas:
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS)); // Layout vertical

        // --- Linha 1: Header ---
        JPanel painelHeader = new JPanel(new BorderLayout());
        JLabel lblLogo = new JLabel("✈ Logo");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JPanel painelMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        painelMenu.add(new JLabel("Bem-vindo, Fulano"));
        painelMenu.add(new JButton("Página Inicial"));
        painelMenu.add(new JButton("Minhas Reservas"));

        painelHeader.add(lblLogo, BorderLayout.WEST);
        painelHeader.add(painelMenu, BorderLayout.EAST);

        // --- Linha 2: Título e Filtros ---
        JPanel painelFiltros = new JPanel(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel("Minhas Reservas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setBorder(new EmptyBorder(15, 0, 15, 0)); // Espaçamento
        painelFiltros.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBuscaContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JTextField txtBusca = new JTextField(35);
        txtBusca.setText("Buscar por destino, data, companhia ou status");
        
        JButton btnProximosVoos = new JButton("Próximos Voos");
        
        String[] opcoesCanceladas = {"Canceladas"};
        JComboBox<String> comboCanceladas = new JComboBox<>(opcoesCanceladas);

        painelBuscaContainer.add(txtBusca);
        painelBuscaContainer.add(btnProximosVoos);
        painelBuscaContainer.add(comboCanceladas);
        
        painelFiltros.add(painelBuscaContainer, BorderLayout.CENTER);

        // Adiciona as duas linhas ao painel do topo
        painelTopo.add(painelHeader);
        painelTopo.add(painelFiltros);

        return painelTopo;
    }

    /**
     * Cria a tabela de reservas (o conteúdo principal).
     */
    private JScrollPane criarPainelCentral() {
        String[] colunas = {"Código da Reserva", "Origem -> Destino", "Data e Hora do Voo", "Companhia Aérea", "Status", "Preço"};

        Object[][] dados = {
            {"#R1234", "<html>São Paulo<br>Recife</html>", "19/05/2024 às 15:30", "Gol", "Confirmada", 950.00},
            {"#R5678", "<html>Rio de Janeiro<br>Belo Horizonte</html>", "25/05/2024 às 09:45", "Latam", "Pendente", 720.00},
            {"#R9101", "<html>Brasília<br>Salvador</html>", "02/07/2024 às 20:10", "Azul", "Cancelada", 650.00},
            {"#R1121", "<html>Fortaleza<br>São Paulo</html>", "12/08/2024 às 07:25", "Gol", "Confirmada", 1200.00}
        };

        modelTabela = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabelaReservas = new JTable(modelTabela);

        tabelaReservas.setRowHeight(40); 
        tabelaReservas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabelaReservas.setGridColor(Color.LIGHT_GRAY);
        tabelaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tabelaReservas.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(Color.WHITE);

        tabelaReservas.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());
        tabelaReservas.getColumnModel().getColumn(5).setCellRenderer(new CurrencyRenderer());

        return new JScrollPane(tabelaReservas);
    }

    /**
     * Cria os botões de ação na parte inferior.
     */
    private JPanel criarPainelInferior() {
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        
        painelInferior.add(new JButton("🔍 Visualizar Detalhes"));
        painelInferior.add(new JButton("🔄 Alterar Reserva"));
        painelInferior.add(new JButton("❌ Cancelar Reserva"));
        painelInferior.add(new JButton("📄 Gerar Comprovante"));

        return painelInferior;
    }

    // --- CLASSES INTERNAS PARA CUSTOMIZAR A TABELA ---

    private static class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) value;
            
            if (!isSelected) {
                 c.setBackground(table.getBackground());
            }

            if ("Cancelada".equalsIgnoreCase(status)) {
                c.setForeground(Color.RED);
                c.setFont(new Font("SansSerif", Font.BOLD, 14));
            } else if ("Confirmada".equalsIgnoreCase(status)) {
                c.setForeground(new Color(0, 150, 0)); // Verde escuro
                c.setFont(new Font("SansSerif", Font.BOLD, 14));
            } else {
                c.setForeground(Color.BLACK);
                c.setFont(new Font("SansSerif", Font.PLAIN, 14));
            }
            return c;
        }
    }

    private static class CurrencyRenderer extends DefaultTableCellRenderer {
        private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        public CurrencyRenderer() {
            super();
            setHorizontalAlignment(JLabel.LEFT); 
        }

        @Override
        public void setValue(Object value) {
            if (value != null) {
                setText(FORMATO_MOEDA.format(value));
            } else {
                setText("");
            }
        }
    }

    // Você pode apagar o método main() se não precisar testar este painel isoladamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Teste do Painel de Reservas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            tela_reserva painel = new tela_reserva();
            frame.add(painel);
            
            frame.pack(); 
            frame.setMinimumSize(new Dimension(1000, 600)); 
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);
        });
    }

    // As variáveis que o NetBeans criaria (initComponents, etc.) não são necessárias
    // porque estamos criando tudo manualmente no construtor.
}