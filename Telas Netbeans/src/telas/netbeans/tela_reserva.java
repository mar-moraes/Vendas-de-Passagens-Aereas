

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Um JPanel que recria a tela "Minhas Reservas" usando Java Swing.
 * --- CLASSE ATUALIZADA ---
 * @author eumes
 */
public class tela_reserva extends javax.swing.JPanel {

    private JTable tabelaReservas;
    private DefaultTableModel modelTabela;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField txtBusca;
    private JComboBox<String> comboStatus;
    private final String placeholder = "Buscar por destino, data, companhia ou status";

    // --- NOVO CAMPO: Lista de Reservas ---
    // Esta lista guarda os objetos Reserva COMPLETOS.
    // A tabela é preenchida a partir dela.
    private List<Reserva> listaDeReservas;
    // ------------------------------------

    /**
     * Creates new form tela_reserva
     */
    public tela_reserva() { 
        // 1. Carrega os dados mock ANTES de criar os componentes
        carregarDadosMock();
        
        // 2. Configuração principal do Painel
        setLayout(new BorderLayout(10, 10)); // Gaps entre as seções
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Margem em volta do painel

        // 3. Adiciona as seções
        add(criarPainelTopo(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);
    }

    /**
     * --- NOVO MÉTODO ---
     * Inicializa a listaDeReservas com dados mock, incluindo os detalhes.
     */
    private void carregarDadosMock() {
        listaDeReservas = new ArrayList<>();
        
        // Reserva 1: Confirmada (Gol)
        listaDeReservas.add(new Reserva(
            "#R1234", "São Paulo", "Recife", "19/05/2024 às 15:30", "Gol", "Confirmada", 950.00,
            "G3 1500", "Aeroporto de Guarulhos (GRU)", "Aeroporto Int. de Recife (REC)", "19/05/2024 às 18:40",
            "2", "B12", "Fulano de Tal", "CPF 123.456.789-00", "22A", "10/04/2024", "Cartão Visa **** 1234"
        ));

        // Reserva 2: Pendente (Latam)
        listaDeReservas.add(new Reserva(
            "#R5678", "Rio de Janeiro", "Belo Horizonte", "25/05/2024 às 09:45", "Latam", "Pendente", 720.00,
            "LA 3201", "Aeroporto Santos Dumont (SDU)", "Aeroporto Int. de Confins (CNF)", "25/05/2024 às 10:50",
            "1", "A04", "Fulano de Tal", "CPF 123.456.789-00", "10C", "20/05/2024", "Pagamento Pendente"
        ));
        
        // Reserva 3: Cancelada (Azul)
        listaDeReservas.add(new Reserva(
            "#R9101", "Brasília", "Salvador", "02/07/2024 às 20:10", "Azul", "Cancelada", 650.00,
            "AD 4010", "Aeroporto Int. de Brasília (BSB)", "Aeroporto Int. de Salvador (SSA)", "02/07/2024 às 22:00",
            "1", "C05", "Fulano de Tal", "CPF 123.456.789-00", "5F", "01/06/2024", "Boleto Bancário"
        ));
        
        // Reserva 4: Confirmada (Gol)
        listaDeReservas.add(new Reserva(
            "#R1121", "Fortaleza", "São Paulo", "12/08/2024 às 07:25", "Gol", "Confirmada", 1200.00,
            "G3 1720", "Aeroporto Int. de Fortaleza (FOR)", "Aeroporto de Congonhas (CGH)", "12/08/2024 às 10:45",
            "1", "B02", "Ciclano da Silva", "CPF 987.654.321-00", "18B", "15/07/2024", "Cartão Master **** 5678"
        ));
    }


    /**
     * Cria o cabeçalho (Logo, Menu) e a seção de título e busca.
     * (Sem alterações neste método)
     */
    private JPanel criarPainelTopo() {
        // Painel principal do topo
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));

        // --- Linha 1: Header ---
        JPanel painelHeader = new JPanel(new BorderLayout());
        JLabel lblLogo = new JLabel("✈ Logo");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JPanel painelMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        painelMenu.add(new JLabel("Bem-vindo, Fulano"));
        
        JButton btnPaginaInicial = new JButton("Página Inicial");
        btnPaginaInicial.setBorderPainted(false);
        btnPaginaInicial.setContentAreaFilled(false);
        btnPaginaInicial.setFocusPainted(false);
        btnPaginaInicial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelMenu.add(btnPaginaInicial);

        painelHeader.add(lblLogo, BorderLayout.WEST);
        painelHeader.add(painelMenu, BorderLayout.EAST);

        // --- Linha 2: Título e Filtros ---
        JPanel painelFiltros = new JPanel(new BorderLayout(10, 10));
        
        JLabel lblTitulo = new JLabel("Minhas Reservas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setBorder(new EmptyBorder(15, 0, 15, 0));
        painelFiltros.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBuscaContainer = new JPanel(new BorderLayout(10, 5));
        
        // Inicializa o campo da classe
        txtBusca = new JTextField(); 
        txtBusca.setText(placeholder);
        txtBusca.setForeground(Color.GRAY); 
        txtBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtBusca.getText().equals(placeholder)) {
                    txtBusca.setText("");
                    txtBusca.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBusca.getText().isEmpty()) {
                    txtBusca.setText(placeholder);
                    txtBusca.setForeground(Color.GRAY);
                }
            }
        });
        
        JButton btnProximosVoos = new JButton("Próximos Voos");
        
        String[] opcoesStatus = {"Todos", "Confirmada", "Pendente", "Cancelada"};
        // Inicializa o campo da classe
        comboStatus = new JComboBox<>(opcoesStatus);

        // --- LÓGICA DE FILTRO ---
        comboStatus.addActionListener(e -> atualizarFiltros());
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { atualizarFiltros(); }
            @Override public void removeUpdate(DocumentEvent e) { atualizarFiltros(); }
            @Override public void changedUpdate(DocumentEvent e) { atualizarFiltros(); }
        });

        JPanel painelAcoesFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelAcoesFiltro.add(btnProximosVoos);
        painelAcoesFiltro.add(comboStatus); 
        
        painelBuscaContainer.add(txtBusca, BorderLayout.CENTER); 
        painelBuscaContainer.add(painelAcoesFiltro, BorderLayout.EAST);
        
        painelFiltros.add(painelBuscaContainer, BorderLayout.CENTER);

        // Adiciona as duas linhas ao painel do topo
        painelTopo.add(painelHeader);
        painelTopo.add(painelFiltros);

        return painelTopo;
    }

    /**
     * Cria a tabela de reservas (o conteúdo principal).
     * --- MÉTODO ATUALIZADO ---
     */
    private JScrollPane criarPainelCentral() {
        String[] colunas = {"Código da Reserva", "Origem -> Destino", "Data e Hora do Voo", "Companhia Aérea", "Status", "Preço"};

        // --- DADOS VÊM DA LISTA DE RESERVAS AGORA ---
        // Cria um array 2D vazio
        Object[][] dados = new Object[listaDeReservas.size()][colunas.length];
        // Preenche o array 2D com os dados básicos da lista de objetos
        for (int i = 0; i < listaDeReservas.size(); i++) {
            Reserva r = listaDeReservas.get(i);
            dados[i][0] = r.getCodigo();
            dados[i][1] = r.getOrigemDestinoHtml(); // Usa o helper para o HTML
            dados[i][2] = r.getDataHoraPartida();
            dados[i][3] = r.getCompanhiaAerea();
            dados[i][4] = r.getStatus();
            dados[i][5] = r.getPreco(); // O Renderer cuida da formatação
        }
        // ---------------------------------------------

        modelTabela = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabelaReservas = new JTable(modelTabela);
        
        sorter = new TableRowSorter<>(modelTabela);
        tabelaReservas.setRowSorter(sorter);

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
     * Atualiza o filtro da tabela com base no campo de busca E no JComboBox de status.
     * (Sem alterações neste método)
     */
    private void atualizarFiltros() {
        if (sorter == null) {
            return;
        }

        List<RowFilter<DefaultTableModel, Object>> filtros = new ArrayList<>();

        // 1. Pega o filtro de Status
        String statusSelecionado = (String) comboStatus.getSelectedItem();
        if (statusSelecionado != null && !"Todos".equals(statusSelecionado)) {
            filtros.add(RowFilter.regexFilter(statusSelecionado, 4));
        }

        // 2. Pega o filtro de Texto (Busca)
        String textoBusca = txtBusca.getText();
        if (textoBusca != null && !textoBusca.isEmpty() && !textoBusca.equals(placeholder)) {
            filtros.add(RowFilter.regexFilter("(?i)" + textoBusca));
        }

        // 3. Combina os filtros
        if (filtros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        }
    }



    /**
     * Cria os botões de ação na parte inferior.
     * --- MÉTODO ATUALIZADO ---
     * (Com a lógica para receber o sinal de exclusão do dialog)
     */
    private JPanel criarPainelInferior() {
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        
        // --- BOTÃO VISUALIZAR DETALHES ---
        JButton btnDetalhes = new JButton("🔍 Visualizar Detalhes");
        configurarBotaoDiscreto(btnDetalhes);
        btnDetalhes.addActionListener(e -> {
            int selectedRow = tabelaReservas.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                    tela_reserva.this, 
                    "Por favor, selecione uma reserva para visualizar.",
                    "Nenhuma Reserva Selecionada",
                    JOptionPane.WARNING_MESSAGE
                );
                return; // Para a execução
            }

            // 1. Converte o índice da VIEW para o índice do MODEL (em caso de filtro)
            int modelRow = tabelaReservas.convertRowIndexToModel(selectedRow);
            
            // 2. Pega o objeto Reserva completo da nossa lista
            Reserva reservaSelecionada = listaDeReservas.get(modelRow); //

            // 3. Encontra o Frame "pai" desta tela
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(tela_reserva.this);
            
            // 4. Cria e exibe o JDialog
            tela_detalhes_reserva dialog = new tela_detalhes_reserva(owner, reservaSelecionada);
            dialog.setVisible(true); // O código pausa aqui até o dialog ser fechado
            
            // --- INÍCIO DA CORREÇÃO ---
            // 5. Verifica se o diálogo foi fechado com um pedido de exclusão
            if (dialog.foiExclusaoSolicitada()) { //
                // A tela de detalhes confirmou a exclusão.
                // modelRow ainda está em escopo e correto.
                
                // Remove da lista de dados E do modelo da tabela
                listaDeReservas.remove(modelRow); //
                modelTabela.removeRow(modelRow); //
            }
            // --- FIM DA CORREÇÃO ---
            
        });
        painelInferior.add(btnDetalhes);
        // ----------------------------------------------------
        
        
        // --- O BOTÃO "ALTERAR RESERVA" FOI REMOVIDO DESTE PAINEL ---

        
        // --- BOTÃO REMOVER RESERVA (da tela principal) ---
        JButton btnCancelar = new JButton("❌ Remover Reserva");
        configurarBotaoDiscreto(btnCancelar); 
        
        btnCancelar.addActionListener(e -> {
            int selectedRow = tabelaReservas.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                    tela_reserva.this, 
                    "Por favor, selecione uma reserva para cancelar.",
                    "Nenhuma Reserva Selecionada",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                int confirm = JOptionPane.showConfirmDialog(
                    tela_reserva.this,
                    "Tem certeza que deseja cancelar a reserva selecionada?",
                    "Confirmar Cancelamento",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    int modelRow = tabelaReservas.convertRowIndexToModel(selectedRow);
                    
                    // Remove da lista de dados E do modelo da tabela
                    listaDeReservas.remove(modelRow); //
                    modelTabela.removeRow(modelRow); //
                }
            }
        });
        painelInferior.add(btnCancelar);
        // -------------------------------------------------

        // O BOTÃO "GERAR COMPROVANTE" já tinha sido removido

        return painelInferior;
    }

    /**
     * Aplica um estilo discreto (borda fina) e um efeito de hover
     * (fundo azul claro) a um botão.
     * (Sem alterações neste método)
     */
    private void configurarBotaoDiscreto(JButton botao) {
        Color corHover = new Color(220, 240, 255); 
        Color corFundoNormal = getBackground(); 
        Border bordaDiscreta = BorderFactory.createLineBorder(new Color(200, 200, 200)); 

        botao.setBorder(bordaDiscreta);
        botao.setBackground(corFundoNormal);
        botao.setContentAreaFilled(false); 
        botao.setOpaque(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(corHover);
                botao.setContentAreaFilled(true);
                botao.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(corFundoNormal);
                botao.setContentAreaFilled(false);
                botao.setOpaque(false);
            }
        });
    }

    // --- CLASSES INTERNAS PARA CUSTOMIZAR A TABELA ---
    // (Sem alterações nestas classes)

    private static class StatusRenderer extends DefaultTableCellRenderer {
        public StatusRenderer() {
            super(); 
            setHorizontalAlignment(JLabel.CENTER); // Centraliza o texto
        }
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
            } else if ("Pendente".equalsIgnoreCase(status)) { 
                c.setForeground(Color.ORANGE); // Laranja para pendente
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

    // (Sem alterações neste método)
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
}