import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// --- NOVA FUNCIONALIDADE ---
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// ---------------------------


public class TelaPagamento extends JFrame {

    private JButton btnVoltar, btnConfirmar;
    private JRadioButton rbCredito, rbDebito, rbPontos;
    private JTextField txtNumeroCartao, txtValidade, txtCVV, txtNomeTitular;
    private ButtonGroup grupoPagamento;
    private JPanel painelDadosCartao; 

    // --- Constantes de Design ---
    private static final Color COR_PRINCIPAL = new Color(0, 51, 153); 
    private static final Color COR_DESTAQUE = new Color(255, 102, 0); 
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);
    // ----------------------------

    public TelaPagamento() {
        // (Sem alterações no construtor)
        setTitle("Tela de Pagamento");
        setSize(600, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        add(criarHeaderPagamento(), BorderLayout.NORTH);

        JPanel centroPanel = new JPanel();
        centroPanel.setLayout(new BoxLayout(centroPanel, BoxLayout.Y_AXIS));
        centroPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        centroPanel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Pagamento Seguro", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(COR_PRINCIPAL);
        centroPanel.add(titulo);
        centroPanel.add(Box.createVerticalStrut(30));

        JPanel painelFormas = new JPanel();
        painelFormas.setLayout(new BoxLayout(painelFormas, BoxLayout.Y_AXIS));
        painelFormas.setBackground(Color.WHITE);
        painelFormas.setBorder(BorderFactory.createTitledBorder("Forma de Pagamento"));
        
        rbCredito = new JRadioButton("Cartão de Crédito");
        estilizarRadioButton(rbCredito);
        rbDebito = new JRadioButton("Cartão de Débito");
        estilizarRadioButton(rbDebito);
        rbPontos = new JRadioButton("Pontos (Programa de Fidelidade)");
        estilizarRadioButton(rbPontos);

        grupoPagamento = new ButtonGroup();
        grupoPagamento.add(rbCredito);
        grupoPagamento.add(rbDebito);
        grupoPagamento.add(rbPontos);

        painelFormas.add(rbCredito);
        painelFormas.add(rbDebito);
        painelFormas.add(rbPontos);
        
        centroPanel.add(painelFormas);
        centroPanel.add(Box.createVerticalStrut(20));

        painelDadosCartao = new JPanel();
        painelDadosCartao.setLayout(new GridLayout(0, 1, 5, 10)); 
        painelDadosCartao.setBackground(Color.WHITE);
        painelDadosCartao.setBorder(BorderFactory.createTitledBorder("Dados do Cartão"));
        
        painelDadosCartao.add(criarLabelCampo("Nome do Titular:"));
        txtNomeTitular = criarTextFieldCampo();
        painelDadosCartao.add(txtNomeTitular);

        painelDadosCartao.add(criarLabelCampo("Número do Cartão:"));
        txtNumeroCartao = criarTextFieldCampo();
        painelDadosCartao.add(txtNumeroCartao);

        JPanel painelValidadeCVV = new JPanel(new GridLayout(1, 2, 10, 0));
        painelValidadeCVV.setBackground(Color.WHITE);
        
        JPanel painelValidade = new JPanel(new BorderLayout());
        painelValidade.setBackground(Color.WHITE);
        painelValidade.add(criarLabelCampo("Validade (MM/AA):"), BorderLayout.NORTH);
        txtValidade = criarTextFieldCampo();
        painelValidade.add(txtValidade, BorderLayout.CENTER);
        
        JPanel painelCVV = new JPanel(new BorderLayout());
        painelCVV.setBackground(Color.WHITE);
        painelCVV.add(criarLabelCampo("CVV:"), BorderLayout.NORTH);
        txtCVV = criarTextFieldCampo();
        painelCVV.add(txtCVV, BorderLayout.CENTER);
        
        painelValidadeCVV.add(painelValidade);
        painelValidadeCVV.add(painelCVV);
        
        painelDadosCartao.add(painelValidadeCVV);
        
        painelDadosCartao.setVisible(false); 
        centroPanel.add(painelDadosCartao);

        centroPanel.add(Box.createVerticalStrut(30));

        btnConfirmar = new JButton("Confirmar Pagamento");
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 16));
        btnConfirmar.setBackground(new Color(0, 120, 0)); 
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setPreferredSize(new Dimension(250, 40));
        btnConfirmar.setMaximumSize(new Dimension(250, 40));
        centroPanel.add(btnConfirmar);

        add(centroPanel, BorderLayout.CENTER);

        configurarEventos();
    }
    
    /**
     * Cria o cabeçalho padronizado da tela de pagamento.
     */
    private JPanel criarHeaderPagamento() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        headerPanel.setPreferredSize(new Dimension(600, 70));

        // --- Logo (Esquerda) ---
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
        
        // --- NOVA FUNCIONALIDADE: Logo clicável ---
        lblLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Pergunta antes de sair, pois pode perder o pagamento
                int confirm = JOptionPane.showConfirmDialog(
                    TelaPagamento.this, 
                    "Deseja voltar à tela inicial? O pagamento atual será cancelado.",
                    "Voltar ao Início",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    new TelaInicial().setVisible(true);
                    dispose(); // Fecha a tela de pagamento
                }
            }
        });
        // -------------------------------------------
        
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // --- Botão Voltar (Direita) ---
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
    
    private void estilizarRadioButton(JRadioButton rb) {
        // (Sem alterações)
        rb.setFont(FONTE_PADRAO);
        rb.setBackground(Color.WHITE);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private JLabel criarLabelCampo(String texto) {
        // (Sem alterações)
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField criarTextFieldCampo() {
        // (Sem alterações)
        JTextField textField = new JTextField();
        textField.setFont(FONTE_PADRAO);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            new EmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }


    private void configurarEventos() {
        // (Sem alterações)
        ActionListener listenerFormas = e -> {
            if (rbCredito.isSelected() || rbDebito.isSelected()) {
                painelDadosCartao.setVisible(true);
            } else {
                painelDadosCartao.setVisible(false);
            }
            this.revalidate(); 
        };
        rbCredito.addActionListener(listenerFormas);
        rbDebito.addActionListener(listenerFormas);
        rbPontos.addActionListener(listenerFormas);

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicial().setVisible(true);
                dispose(); 
            }
        });

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!rbCredito.isSelected() && !rbDebito.isSelected() && !rbPontos.isSelected()) {
                    JOptionPane.showMessageDialog(TelaPagamento.this, "Selecione uma forma de pagamento!", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (rbCredito.isSelected() || rbDebito.isSelected()) {
                    String nome = txtNomeTitular.getText().trim();
                    String numero = txtNumeroCartao.getText().trim();
                    String validade = txtValidade.getText().trim();
                    String cvv = txtCVV.getText().trim();

                    if (nome.isEmpty() || numero.isEmpty() || validade.isEmpty() || cvv.isEmpty()) {
                        JOptionPane.showMessageDialog(TelaPagamento.this, "Preencha todos os dados do cartão!", "Erro", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                
                if (rbPontos.isSelected()) {
                    // Lógica de validação de pontos
                }

                JOptionPane.showMessageDialog(TelaPagamento.this, "Pagamento confirmado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                JFrame frameReservas = new JFrame("Minhas Reservas");
                frameReservas.setSize(1000, 700);
                frameReservas.setLocationRelativeTo(null); 
                frameReservas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frameReservas.add(new tela_reserva(null)); 
                frameReservas.setVisible(true);
                
                dispose();
            }
        });
    }

    // Main para teste
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