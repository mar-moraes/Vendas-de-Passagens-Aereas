import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class tela_cadastro extends JPanel {

    // --- Constantes de Design ---
    private static final Color COR_PRINCIPAL = new Color(0, 51, 153);
    private static final Color COR_DESTAQUE = new Color(255, 102, 0);
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);
    private static final Font FONTE_TITULO = new Font("Arial", Font.BOLD, 24);
    // ----------------------------

    private JTextField campoNome, campoEmail, campoTelefone;
    private JPasswordField campoSenha, campoConfirmarSenha;
    private JButton b_cadastro;
    private JLabel lblLinkVoltar;

    public tela_cadastro() {
        initComponents();
        personalizarLayout();
    }

    private void initComponents() {
        campoNome = new JTextField();
        campoEmail = new JTextField();

        try {
            javax.swing.text.MaskFormatter maskTel = new javax.swing.text.MaskFormatter("(##) #####-####");
            maskTel.setPlaceholderCharacter('_');
            campoTelefone = new JFormattedTextField(maskTel);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            campoTelefone = new JTextField(); // Fallback
        }

        campoSenha = new JPasswordField();
        campoConfirmarSenha = new JPasswordField();
        b_cadastro = new JButton("Cadastrar");
        lblLinkVoltar = new JLabel("<html><a href='#'>Voltar para o Login</a></html>");
    }

    private void personalizarLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Header com Logo ---
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 0, 10, 0));

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
        JLabel lblLogo = new JLabel();
        if (logoIcon.getImage() != null) {
            Image img = logoIcon.getImage().getScaledInstance(-1, 60, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("Venda de Passagens");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
            lblLogo.setForeground(COR_PRINCIPAL);
        }
        header.add(lblLogo);
        add(header, BorderLayout.NORTH);

        // --- Formulário Centralizado ---
        JPanel centroWrapper = new JPanel(new GridBagLayout());
        centroWrapper.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(30, 40, 30, 40)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Título do Card
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblTitulo = new JLabel("Crie sua conta");
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(COR_PRINCIPAL);
        formPanel.add(lblTitulo, gbc);

        // Campos
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        adicionarCampo(formPanel, "Nome Completo", campoNome, gbc, 1);
        adicionarCampo(formPanel, "E-mail", campoEmail, gbc, 3);
        adicionarCampo(formPanel, "Telefone", campoTelefone, gbc, 5);
        adicionarCampo(formPanel, "Senha", campoSenha, gbc, 7);
        adicionarCampo(formPanel, "Confirmar Senha", campoConfirmarSenha, gbc, 9);

        // Botão
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 5, 10, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        b_cadastro.setFont(new Font("Arial", Font.BOLD, 16));
        b_cadastro.setBackground(COR_DESTAQUE);
        b_cadastro.setForeground(Color.WHITE);
        b_cadastro.setFocusPainted(false);
        b_cadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b_cadastro.setPreferredSize(new Dimension(250, 40));
        b_cadastro.addActionListener(e -> acaoCadastrar());

        formPanel.add(b_cadastro, gbc);

        // Link Voltar
        gbc.gridy = 12;
        gbc.insets = new Insets(5, 5, 5, 5);
        lblLinkVoltar.setFont(FONTE_PADRAO);
        lblLinkVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLinkVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acaoVoltar();
            }
        });
        formPanel.add(lblLinkVoltar, gbc);

        centroWrapper.add(formPanel);
        add(centroWrapper, BorderLayout.CENTER);
    }

    private void adicionarCampo(JPanel panel, String label, JComponent campo, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(new Color(80, 80, 80));
        panel.add(lbl, gbc);

        gbc.gridy = y + 1;
        campo.setFont(FONTE_PADRAO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(5, 8, 5, 8)));
        campo.setPreferredSize(new Dimension(300, 35));
        panel.add(campo, gbc);
    }

    private void acaoCadastrar() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String telefone = campoTelefone.getText();
        String senha = new String(campoSenha.getPassword());
        String confirmacao = new String(campoConfirmarSenha.getPassword());

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!senha.equals(confirmacao)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        acaoVoltar();
    }

    private void acaoVoltar() {
        Window janela = SwingUtilities.getWindowAncestor(this);
        if (janela != null) {
            janela.dispose();
            new tela_login().setVisible(true);
        }
    }
}