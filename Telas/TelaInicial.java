import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.List;

public class TelaInicial extends JFrame {

    private JButton btnMinhasReservas, btnLogin, btnBuscarVoos;
    private JTextField txtOrigem, txtDestino;
    private JFormattedTextField txtData;

    // --- Constantes de Design ---
    private static final Color COR_PRINCIPAL = new Color(0, 51, 153);
    private static final Color COR_DESTAQUE = new Color(255, 102, 0);
    private static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 24);
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);
    // ----------------------------

    public TelaInicial() {
        setTitle("Venda de Passagens Aéreas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ======== TOPO (HEADER PADRONIZADO) =========
        add(criarHeaderPadrao(), BorderLayout.NORTH);

        // ======== CENTRO (Formulário de Busca e Cards) =========
        JPanel centroContainer = new JPanel(new BorderLayout());
        centroContainer.setBackground(Color.WHITE);

        JPanel centroPanel = new JPanel();
        centroPanel.setLayout(new BoxLayout(centroPanel, BoxLayout.Y_AXIS));
        centroPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        centroPanel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Para onde você quer ir?", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(COR_PRINCIPAL);
        centroPanel.add(titulo);
        centroPanel.add(Box.createVerticalStrut(30));

        // Painel de campos
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        painelCampos.setBackground(Color.WHITE);

        painelCampos.add(criarLabelCampo("Origem:"));
        txtOrigem = criarTextFieldCampo(20);
        painelCampos.add(wrapTextField(txtOrigem));

        painelCampos.add(criarLabelCampo("Destino:"));
        txtDestino = criarTextFieldCampo(20);
        painelCampos.add(wrapTextField(txtDestino));

        painelCampos.add(criarLabelCampo("Datas (Ida e Volta):"));

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/#### - ##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            txtData = new JFormattedTextField(mascaraData);
            txtData.setFont(FONTE_PADRAO);
            txtData.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY),
                    new EmptyBorder(5, 5, 5, 5)));
            txtData.setColumns(20);
        } catch (ParseException e) {
            e.printStackTrace();
            txtData = new JFormattedTextField();
            txtData.setColumns(20);
        }
        painelCampos.add(wrapTextField(txtData));

        centroPanel.add(painelCampos);
        centroPanel.add(Box.createVerticalStrut(30));

        // Botão buscar voos
        btnBuscarVoos = new JButton("Buscar Voos");
        btnBuscarVoos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBuscarVoos.setFont(new Font("Arial", Font.BOLD, 16));
        btnBuscarVoos.setBackground(COR_DESTAQUE);
        btnBuscarVoos.setForeground(Color.WHITE);
        btnBuscarVoos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarVoos.setFocusPainted(false);
        btnBuscarVoos.setPreferredSize(new Dimension(200, 40));
        btnBuscarVoos.setMaximumSize(new Dimension(200, 40));
        centroPanel.add(btnBuscarVoos);

        // --- Painel de Cards de Voos ---
        centroPanel.add(Box.createVerticalStrut(30));
        centroPanel.add(criarPainelVoosDestaque());
        // ----------------------------------------------------

        JScrollPane scrollCentro = new JScrollPane(centroPanel);
        scrollCentro.setBorder(BorderFactory.createEmptyBorder());
        scrollCentro.getVerticalScrollBar().setUnitIncrement(16);
        centroContainer.add(scrollCentro, BorderLayout.CENTER);

        add(centroContainer, BorderLayout.CENTER);

        configurarEventos();
    }

    private JPanel criarHeaderPadrao() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        headerPanel.setPreferredSize(new Dimension(800, 70));

        // Logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
        JLabel lblLogo = new JLabel();
        if (logoIcon.getImage() != null) {
            Image img = logoIcon.getImage().getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("Logo App");
            lblLogo.setFont(FONTE_TITULO);
            lblLogo.setForeground(COR_PRINCIPAL);
        }
        lblLogo.setBorder(new EmptyBorder(10, 20, 10, 20));
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Logo clicável
        lblLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtOrigem.setText("");
                txtDestino.setText("");
                txtData.setValue(null);
            }
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        painelBotoes.setBackground(Color.WHITE);

        // Botão Check-in
        JButton btnCheckin = new JButton("Check-in");
        estilizarBotaoHeader(btnCheckin);
        btnCheckin.addActionListener(e -> {
            new TelaCheckin().setVisible(true);
        });
        painelBotoes.add(btnCheckin);

        btnMinhasReservas = new JButton("Minhas Reservas");
        estilizarBotaoHeader(btnMinhasReservas);
        painelBotoes.add(btnMinhasReservas);

        // Lógica de Login
        String usuario = SessaoUsuario.getNomeUsuario();
        if (usuario != null && !usuario.equals("Fulano")) {
            JLabel lblUsuario = new JLabel("Olá, " + usuario);
            lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
            lblUsuario.setForeground(COR_PRINCIPAL);
            painelBotoes.add(lblUsuario);

            JButton btnSair = new JButton("(Sair)");
            estilizarBotaoHeader(btnSair);
            btnSair.setForeground(Color.RED);
            btnSair.setFont(new Font("Arial", Font.PLAIN, 12));
            btnSair.addActionListener(e -> {
                SessaoUsuario.setNomeUsuario("Fulano");
                new TelaInicial().setVisible(true);
                dispose();
            });
            painelBotoes.add(btnSair);
        } else {
            btnLogin = new JButton("Login");
            estilizarBotaoHeader(btnLogin);
            btnLogin.addActionListener(e -> {
                tela_login telaLogin = new tela_login();
                telaLogin.setVisible(true);
                dispose();
            });
            painelBotoes.add(btnLogin);
        }

        headerPanel.add(painelBotoes, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel criarPainelVoosDestaque() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setBackground(Color.WHITE);

        JLabel tituloVoos = new JLabel("Voos em Destaque", SwingConstants.CENTER);
        tituloVoos.setFont(new Font("Arial", Font.BOLD, 22));
        tituloVoos.setForeground(COR_PRINCIPAL);
        painel.add(tituloVoos, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        gridPanel.setBackground(Color.WHITE);

        List<Reserva> reservas = DadosReservas.getReservas();

        for (int i = 0; i < 4 && i < reservas.size(); i++) {
            gridPanel.add(criarCardVoo(reservas.get(i)));
        }

        painel.add(gridPanel, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarCardVoo(Reserva r) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)));
        card.setBackground(Color.WHITE);

        JLabel lblOrigem = new JLabel(r.getOrigem());
        lblOrigem.setFont(new Font("Arial", Font.PLAIN, 12));
        lblOrigem.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDestino = new JLabel(r.getDestino());
        lblDestino.setFont(new Font("Arial", Font.BOLD, 16));
        lblDestino.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPreco = new JLabel(r.getPrecoFormatado());
        lblPreco.setFont(new Font("Arial", Font.BOLD, 14));
        lblPreco.setForeground(COR_DESTAQUE);
        lblPreco.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblOrigem);
        card.add(lblDestino);
        card.add(Box.createVerticalStrut(5));
        card.add(lblPreco);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return card;
    }

    private void estilizarBotaoHeader(JButton botao) {
        botao.setFont(FONTE_PADRAO);
        botao.setForeground(COR_PRINCIPAL);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JLabel criarLabelCampo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField criarTextFieldCampo(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(FONTE_PADRAO);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(5, 5, 5, 5)));
        return textField;
    }

    private JPanel wrapTextField(JComponent component) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(component);
        return wrapper;
    }

    private void configurarEventos() {
        btnBuscarVoos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origem = txtOrigem.getText().trim();
                String destino = txtDestino.getText().trim();
                String data = txtData.getText();

                boolean dataVazia = data.contains("_");

                if (!origem.isEmpty() && origem.equalsIgnoreCase(destino)) {
                    JOptionPane.showMessageDialog(
                            TelaInicial.this,
                            "Origem e Destino não podem ser iguais.\nNão é possível realizar voos para a mesma cidade.",
                            "Erro na Busca",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (origem.isEmpty() || destino.isEmpty() || dataVazia) {
                    JOptionPane.showMessageDialog(TelaInicial.this, "Preencha todos os campos para buscar voos!",
                            "Erro", JOptionPane.WARNING_MESSAGE);
                } else {
                    TelaResultadosBusca dialog = new TelaResultadosBusca(TelaInicial.this, origem, destino, data);
                    dialog.setVisible(true);
                }
            }
        });

        btnMinhasReservas.addActionListener(e -> {
            JFrame frameReservas = new JFrame("Minhas Reservas");
            frameReservas.setSize(1000, 700);
            frameReservas.setLocationRelativeTo(null);
            frameReservas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameReservas.add(new tela_reserva(this));
            frameReservas.setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TelaInicial tela = new TelaInicial();
            tela.setVisible(true);
        });
    }
}