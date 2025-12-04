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

public class tela_reserva extends javax.swing.JPanel {

    private JTable tabelaReservas;
    private DefaultTableModel modelTabela;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField txtBusca;
    private JComboBox<String> comboStatus;
    private final String placeholder = "Buscar por destino, data, companhia ou status";

    private List<Reserva> listaDeReservas;

    private JFrame telaAnterior;

    // --- ALTERAÇÃO: Nova cor principal #063D97 ---
    private static final Color COR_PRINCIPAL = Color.decode("#063D97");
    private static final Color COR_DESTAQUE = new Color(255, 102, 0);
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);

    public tela_reserva(JFrame telaAnterior) {
        this.telaAnterior = telaAnterior;
        this.listaDeReservas = DadosReservas.getReservas();

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        add(criarPainelTopo(), BorderLayout.NORTH);
        add(criarPainelCentral(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);
    }

    public tela_reserva() {
        this(null);
    }

    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBackground(Color.WHITE);

        JPanel painelHeader = new JPanel(new BorderLayout());
        painelHeader.setBackground(Color.WHITE);
        painelHeader.setBorder(new EmptyBorder(0, 0, 10, 0));

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
        JLabel lblLogo = new JLabel();
        if (logoIcon.getImage() != null) {
            Image img = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("Logo App");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 24));
            lblLogo.setForeground(COR_PRINCIPAL);
        }
        lblLogo.setBorder(new EmptyBorder(0, 0, 0, 20));

        lblLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                voltarParaHome();
            }
        });

        painelHeader.add(lblLogo, BorderLayout.WEST);

        JPanel painelMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        painelMenu.setBackground(Color.WHITE);

        JLabel lblBemVindo = new JLabel("Bem-vindo, " + SessaoUsuario.getNomeUsuario());
        lblBemVindo.setFont(FONTE_PADRAO);
        painelMenu.add(lblBemVindo);

        JButton btnPaginaInicial = new JButton("Página Inicial");
        estilizarBotaoHeader(btnPaginaInicial);
        btnPaginaInicial.addActionListener(e -> voltarParaHome());
        painelMenu.add(btnPaginaInicial);

        painelHeader.add(painelMenu, BorderLayout.EAST);

        JPanel painelFiltros = new JPanel(new BorderLayout(10, 10));
        painelFiltros.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Minhas Reservas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(COR_PRINCIPAL);
        lblTitulo.setBorder(new EmptyBorder(15, 0, 15, 0));
        painelFiltros.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBuscaContainer = new JPanel(new BorderLayout(10, 5));
        painelBuscaContainer.setBackground(Color.WHITE);

        txtBusca = new JTextField();
        txtBusca.setText(placeholder);
        txtBusca.setForeground(Color.GRAY);
        txtBusca.setFont(FONTE_PADRAO);
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
        btnProximosVoos.setBackground(COR_DESTAQUE);
        btnProximosVoos.setForeground(Color.WHITE);
        btnProximosVoos.setFont(new Font("Arial", Font.BOLD, 12));
        btnProximosVoos.setCursor(new Cursor(Cursor.HAND_CURSOR));

        String[] opcoesStatus = { "Todos", "Confirmada", "Pendente", "Cancelada" };
        comboStatus = new JComboBox<>(opcoesStatus);
        comboStatus.setFont(FONTE_PADRAO);

        comboStatus.addActionListener(e -> atualizarFiltros());
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarFiltros();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarFiltros();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizarFiltros();
            }
        });

        JPanel painelAcoesFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelAcoesFiltro.setBackground(Color.WHITE);
        painelAcoesFiltro.add(btnProximosVoos);
        painelAcoesFiltro.add(comboStatus);

        painelBuscaContainer.add(txtBusca, BorderLayout.CENTER);
        painelBuscaContainer.add(painelAcoesFiltro, BorderLayout.EAST);

        painelFiltros.add(painelBuscaContainer, BorderLayout.CENTER);

        painelTopo.add(painelHeader);
        painelTopo.add(painelFiltros);

        return painelTopo;
    }

    private void estilizarBotaoHeader(JButton botao) {
        botao.setFont(FONTE_PADRAO);
        botao.setForeground(COR_PRINCIPAL);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void voltarParaHome() {
        Window janela = SwingUtilities.getWindowAncestor(this);
        if (telaAnterior != null) {
            telaAnterior.setVisible(true);
        } else {
            new TelaInicial().setVisible(true);
        }
        if (janela != null) {
            janela.dispose();
        }
    }

    private JScrollPane criarPainelCentral() {
        String[] colunas = { "Código da Reserva", "Origem -> Destino", "Data e Hora do Voo", "Companhia Aérea",
                "Status", "Preço" };

        Object[][] dados = new Object[listaDeReservas.size()][colunas.length];
        for (int i = 0; i < listaDeReservas.size(); i++) {
            Reserva r = listaDeReservas.get(i);
            dados[i][0] = r.getCodigo();
            dados[i][1] = r.getOrigemDestinoHtml();
            dados[i][2] = r.getDataHoraPartida();
            dados[i][3] = r.getCompanhiaAerea();
            dados[i][4] = r.getStatus();
            dados[i][5] = r.getPreco();
        }

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
        tabelaReservas.setGridColor(new Color(220, 220, 220));
        tabelaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabelaReservas.setSelectionBackground(new Color(200, 220, 255));
        tabelaReservas.setSelectionForeground(Color.BLACK);

        JTableHeader header = tabelaReservas.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(COR_PRINCIPAL);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        tabelaReservas.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());
        tabelaReservas.getColumnModel().getColumn(5).setCellRenderer(new CurrencyRenderer());

        JScrollPane scrollPane = new JScrollPane(tabelaReservas);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return scrollPane;
    }

    private void atualizarFiltros() {
        if (sorter == null) {
            return;
        }
        List<RowFilter<DefaultTableModel, Object>> filtros = new ArrayList<>();
        String statusSelecionado = (String) comboStatus.getSelectedItem();
        if (statusSelecionado != null && !"Todos".equals(statusSelecionado)) {
            filtros.add(RowFilter.regexFilter(statusSelecionado, 4));
        }
        String textoBusca = txtBusca.getText();
        if (textoBusca != null && !textoBusca.isEmpty() && !textoBusca.equals(placeholder)) {
            filtros.add(RowFilter.regexFilter("(?i)" + textoBusca));
        }
        if (filtros.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    private JPanel criarPainelInferior() {
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        painelInferior.setBackground(Color.WHITE);

        JButton btnDetalhes = new JButton("🔍 Visualizar Detalhes");
        // ALTERAÇÃO: Passando a cor Azul
        configurarBotaoDiscreto(btnDetalhes, COR_PRINCIPAL);
        btnDetalhes.addActionListener(e -> {
            int selectedRow = tabelaReservas.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        tela_reserva.this,
                        "Por favor, selecione uma reserva para visualizar.",
                        "Nenhuma Reserva Selecionada",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int modelRow = tabelaReservas.convertRowIndexToModel(selectedRow);
            Reserva reservaSelecionada = listaDeReservas.get(modelRow);

            Frame owner = (Frame) SwingUtilities.getWindowAncestor(tela_reserva.this);

            tela_detalhes_reserva dialog = new tela_detalhes_reserva(owner, reservaSelecionada, COR_PRINCIPAL,
                    COR_DESTAQUE);
            dialog.setVisible(true);

            if (dialog.foiExclusaoSolicitada()) {
                DadosReservas.removerReserva(modelRow);
                modelTabela.removeRow(modelRow);
            }
        });
        painelInferior.add(btnDetalhes);

        JButton btnCancelar = new JButton("❌ Remover Reserva");
        // ALTERAÇÃO: Passando a cor Laranja
        configurarBotaoDiscreto(btnCancelar, COR_DESTAQUE);

        btnCancelar.addActionListener(e -> {
            int selectedRow = tabelaReservas.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        tela_reserva.this,
                        "Por favor, selecione uma reserva para cancelar.",
                        "Nenhuma Reserva Selecionada",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                int confirm = JOptionPane.showConfirmDialog(
                        tela_reserva.this,
                        "Tem certeza que deseja cancelar a reserva selecionada?",
                        "Confirmar Cancelamento",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    int modelRow = tabelaReservas.convertRowIndexToModel(selectedRow);
                    DadosReservas.removerReserva(modelRow);
                    modelTabela.removeRow(modelRow);
                }
            }
        });
        painelInferior.add(btnCancelar);

        return painelInferior;
    }

    /**
     * ALTERAÇÃO: Agora recebe a cor para estilizar o botão.
     */
    private void configurarBotaoDiscreto(JButton botao, Color corBordaETexto) {
        Color corHover = new Color(230, 230, 250);
        Color corFundoNormal = Color.WHITE;

        // Borda colorida
        Border bordaColorida = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(corBordaETexto),
                new EmptyBorder(5, 10, 5, 10));

        botao.setBorder(bordaColorida);
        botao.setBackground(corFundoNormal);
        botao.setForeground(corBordaETexto); // Texto colorido
        botao.setContentAreaFilled(false);
        botao.setOpaque(true);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setFont(new Font("Arial", Font.BOLD, 12));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(corHover);
                botao.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(corFundoNormal);
                botao.setContentAreaFilled(false);
            }
        });
    }

    public static class StatusRenderer extends DefaultTableCellRenderer {
        public StatusRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) value;
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
            } else {
                c.setBackground(table.getBackground());
            }
            if ("Cancelada".equalsIgnoreCase(status)) {
                c.setForeground(new Color(200, 0, 0));
                c.setFont(new Font("Arial", Font.BOLD, 14));
            } else if ("Confirmada".equalsIgnoreCase(status)) {
                c.setForeground(new Color(0, 120, 0));
                c.setFont(new Font("Arial", Font.BOLD, 14));
            } else if ("Pendente".equalsIgnoreCase(status)) {
                c.setForeground(new Color(230, 120, 0));
                c.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                c.setForeground(Color.BLACK);
                c.setFont(new Font("Arial", Font.PLAIN, 14));
            }
            return c;
        }
    }

    public static class CurrencyRenderer extends DefaultTableCellRenderer {
        private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        public CurrencyRenderer() {
            super();
            setHorizontalAlignment(JLabel.LEFT);
            setFont(new Font("Arial", Font.PLAIN, 14));
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Teste do Painel de Reservas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            tela_reserva painel = new tela_reserva();
            frame.add(painel);

            frame.pack();
            frame.setMinimumSize(new Dimension(1000, 700));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}