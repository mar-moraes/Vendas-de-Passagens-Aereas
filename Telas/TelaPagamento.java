import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

public class TelaPagamento extends JFrame {

    private JButton btnVoltar, btnConfirmar;
    private JRadioButton rbCartaoSalvo, rbPix, rbNovoCartao, rbBoleto;
    private JTextField txtNumeroCartao, txtNomeTitular, txtValidade, txtCVV, txtCPF;
    private ButtonGroup grupoPagamento;
    private JPanel painelNovoCartao;
    private Reserva reservaPendente;

    // --- Constantes de Design ---
    private static final Color COR_PRINCIPAL = new Color(0, 51, 153);
    private static final Color COR_DESTAQUE = new Color(0, 102, 204); // Azul mais claro para botões
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);
    private static final Font FONTE_BOLD = new Font("Arial", Font.BOLD, 14);
    // ----------------------------

    public TelaPagamento(Reserva reservaPendente) {
        super("Tela de Pagamento");
        this.reservaPendente = reservaPendente;

        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245)); // Fundo cinza claro

        add(criarHeaderPagamento(), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(245, 245, 245));

        // --- Opções de Pagamento ---
        grupoPagamento = new ButtonGroup();

        // 1. Cartão Salvo
        rbCartaoSalvo = criarOpcaoPagamento("Cartão **** 1234");
        mainPanel.add(criarPainelOpcao(rbCartaoSalvo));
        mainPanel.add(Box.createVerticalStrut(10));

        // 2. Pix
        rbPix = criarOpcaoPagamento("Pix");
        mainPanel.add(criarPainelOpcao(rbPix));
        mainPanel.add(Box.createVerticalStrut(20));

        // Label "Cartões"
        JLabel lblCartoes = new JLabel("Cartões");
        lblCartoes.setFont(FONTE_PADRAO);
        lblCartoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblCartoes);
        mainPanel.add(Box.createVerticalStrut(5));

        // 3. Novo Cartão de Crédito
        rbNovoCartao = criarOpcaoPagamento("Novo cartão de crédito");
        rbNovoCartao.setSelected(true); // Default
        JPanel painelNovoCartaoContainer = criarPainelOpcao(rbNovoCartao);

        // Formulário do Novo Cartão (dentro do container)
        painelNovoCartao = criarFormularioNovoCartao();
        painelNovoCartaoContainer.add(painelNovoCartao, BorderLayout.SOUTH);

        mainPanel.add(painelNovoCartaoContainer);
        mainPanel.add(Box.createVerticalStrut(20));

        // Label "Outros meios de pagamento"
        JLabel lblOutros = new JLabel("Outros meios de pagamento");
        lblOutros.setFont(FONTE_PADRAO);
        lblOutros.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblOutros);
        mainPanel.add(Box.createVerticalStrut(5));

        // 4. Boleto
        rbBoleto = criarOpcaoPagamento("Boleto");
        mainPanel.add(criarPainelOpcao(rbBoleto));

        mainPanel.add(Box.createVerticalStrut(30));

        // Botão Confirmar
        btnConfirmar = new JButton("Continuar");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
        btnConfirmar.setBackground(COR_DESTAQUE);
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinhar à esquerda (preencher largura)

        // Tamanho: largura máxima para preencher o painel, altura fixa
        btnConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        // Remover setPreferredSize fixo para permitir expansão ou definir apenas altura
        btnConfirmar.setPreferredSize(new Dimension(0, 40));

        mainPanel.add(btnConfirmar);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        configurarEventos();
    }

    public TelaPagamento() {
        this(null);
    }

    private JRadioButton criarOpcaoPagamento(String texto) {
        JRadioButton rb = new JRadioButton(texto);
        rb.setFont(FONTE_BOLD);
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        grupoPagamento.add(rb);
        return rb;
    }

    private JPanel criarPainelOpcao(JRadioButton rb) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinhar à esquerda no BoxLayout

        // Wrapper para o RadioButton para adicionar margem/padding se necessário
        JPanel rbPanel = new JPanel(new BorderLayout());
        rbPanel.setBackground(Color.WHITE);
        rbPanel.add(rb, BorderLayout.NORTH);

        panel.add(rbPanel, BorderLayout.CENTER);

        // Borda arredondada simulada (apenas visual simples por enquanto)
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(5, 5, 5, 5)));

        return panel;
    }

    private JPanel criarFormularioNovoCartao() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(10, 25, 0, 0)); // Indentação
        form.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinhar à esquerda

        form.add(criarLabelCampo("Número do cartão"));
        try {
            MaskFormatter maskCard = new MaskFormatter("#### #### #### ####");
            maskCard.setPlaceholderCharacter(' ');
            txtNumeroCartao = new JFormattedTextField(maskCard);
        } catch (ParseException e) {
            txtNumeroCartao = new JTextField();
        }
        estilizarCampo(txtNumeroCartao);
        form.add(txtNumeroCartao);
        form.add(Box.createVerticalStrut(10));

        form.add(criarLabelCampo("Nome do titular"));
        txtNomeTitular = new JTextField();
        estilizarCampo(txtNomeTitular);
        txtNomeTitular.setText("Ex.: Fulano Silva");
        txtNomeTitular.setForeground(Color.GRAY);
        txtNomeTitular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtNomeTitular.getText().equals("Ex.: Fulano Silva")) {
                    txtNomeTitular.setText("");
                    txtNomeTitular.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtNomeTitular.getText().isEmpty()) {
                    txtNomeTitular.setText("Ex.: Fulano Silva");
                    txtNomeTitular.setForeground(Color.GRAY);
                }
            }
        });
        form.add(txtNomeTitular);
        form.add(Box.createVerticalStrut(10));

        // Linha Vencimento e CVV
        JPanel row3 = new JPanel(new GridLayout(1, 2, 10, 0));
        row3.setBackground(Color.WHITE);
        row3.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pValidade = new JPanel(new BorderLayout());
        pValidade.setBackground(Color.WHITE);
        pValidade.add(criarLabelCampo("Vencimento"), BorderLayout.NORTH);
        try {
            MaskFormatter maskVal = new MaskFormatter("##/##");
            maskVal.setPlaceholderCharacter(' ');
            txtValidade = new JFormattedTextField(maskVal);
        } catch (ParseException e) {
            txtValidade = new JTextField();
        }
        estilizarCampo(txtValidade);
        // Placeholder MM/AA
        txtValidade.setText("MM/AA");
        txtValidade.setForeground(Color.GRAY);
        txtValidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtValidade.getText().equals("MM/AA")) {
                    txtValidade.setText("");
                    txtValidade.setForeground(Color.BLACK);
                }
            }
        });
        pValidade.add(txtValidade, BorderLayout.CENTER);

        JPanel pCVV = new JPanel(new BorderLayout());
        pCVV.setBackground(Color.WHITE);
        pCVV.add(criarLabelCampo("Código de segurança"), BorderLayout.NORTH);
        try {
            MaskFormatter maskCVV = new MaskFormatter("###");
            maskCVV.setPlaceholderCharacter(' ');
            txtCVV = new JFormattedTextField(maskCVV);
        } catch (ParseException e) {
            txtCVV = new JTextField();
        }
        estilizarCampo(txtCVV);
        txtCVV.setText("Ex.: 123");
        txtCVV.setForeground(Color.GRAY);
        txtCVV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtCVV.getText().equals("Ex.: 123")) {
                    txtCVV.setText("");
                    txtCVV.setForeground(Color.BLACK);
                }
            }
        });
        pCVV.add(txtCVV, BorderLayout.CENTER);

        row3.add(pValidade);
        row3.add(pCVV);
        form.add(row3);
        form.add(Box.createVerticalStrut(10));

        // Documento do titular
        form.add(criarLabelCampo("Documento do titular"));
        JPanel pDoc = new JPanel(new BorderLayout(5, 0));
        pDoc.setBackground(Color.WHITE);
        pDoc.setAlignmentX(Component.LEFT_ALIGNMENT);
        JComboBox<String> comboDoc = new JComboBox<>(new String[] { "CPF", "CNPJ" });
        comboDoc.setBackground(Color.WHITE);
        pDoc.add(comboDoc, BorderLayout.WEST);

        try {
            MaskFormatter maskCPF = new MaskFormatter("###.###.###-##");
            maskCPF.setPlaceholderCharacter(' ');
            txtCPF = new JFormattedTextField(maskCPF);
        } catch (ParseException e) {
            txtCPF = new JTextField();
        }
        estilizarCampo(txtCPF);
        pDoc.add(txtCPF, BorderLayout.CENTER);

        form.add(pDoc);

        return form;
    }

    private JLabel criarLabelCampo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(FONTE_PADRAO);
        campo.setPreferredSize(new Dimension(100, 35));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 10, 5, 10)));
    }

    private JPanel criarHeaderPagamento() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        headerPanel.setPreferredSize(new Dimension(600, 70));

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
        lblLogo.setBorder(new EmptyBorder(10, 20, 10, 20));

        lblLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmarSaida();
            }
        });

        headerPanel.add(lblLogo, BorderLayout.WEST);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        painelBotoes.setBackground(Color.WHITE);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(FONTE_PADRAO);
        btnVoltar.setForeground(COR_PRINCIPAL);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelBotoes.add(btnVoltar);

        headerPanel.add(painelBotoes, BorderLayout.EAST);
        return headerPanel;
    }

    private void configurarEventos() {
        ActionListener toggleForm = e -> {
            painelNovoCartao.setVisible(rbNovoCartao.isSelected());
            revalidate();
            repaint();
        };

        rbCartaoSalvo.addActionListener(toggleForm);
        rbPix.addActionListener(toggleForm);
        rbNovoCartao.addActionListener(toggleForm);
        rbBoleto.addActionListener(toggleForm);

        btnVoltar.addActionListener(e -> confirmarSaida());

        btnConfirmar.addActionListener(e -> processarPagamento());
    }

    private void confirmarSaida() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja voltar à tela inicial? O pagamento atual será cancelado.",
                "Voltar ao Início",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            new TelaInicial().setVisible(true);
            dispose();
        }
    }

    private void processarPagamento() {
        if (rbNovoCartao.isSelected()) {
            // Validação simples
            if (txtNumeroCartao.getText().trim().isEmpty() || txtNomeTitular.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os dados do cartão.", "Erro",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (reservaPendente != null) {
            reservaPendente.setStatus("Confirmada");
            if (rbCartaoSalvo.isSelected())
                reservaPendente.setMetodoPagamento("Cartão Salvo");
            else if (rbPix.isSelected())
                reservaPendente.setMetodoPagamento("Pix");
            else if (rbNovoCartao.isSelected())
                reservaPendente.setMetodoPagamento("Novo Cartão");
            else if (rbBoleto.isSelected())
                reservaPendente.setMetodoPagamento("Boleto");
        }

        JOptionPane.showMessageDialog(this, "Pagamento confirmado com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        JFrame frameReservas = new JFrame("Minhas Reservas");
        frameReservas.setSize(1000, 700);
        frameReservas.setLocationRelativeTo(null);
        frameReservas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameReservas.add(new tela_reserva(null));
        frameReservas.setVisible(true);

        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TelaPagamento tela = new TelaPagamento();
            tela.setVisible(true);
        });
    }
}