import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class tela_login extends javax.swing.JFrame {
    
    // --- Constantes de Design ---
    private static final Color COR_PRINCIPAL = new Color(0, 51, 153); 
    private static final Color COR_DESTAQUE = new Color(255, 102, 0); 
    private static final Color COR_FUNDO_NOVO = new Color(44, 117, 204); 
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);
    // ----------------------------
    
    public tela_login() {
        initComponents();
        personalizarLayout();
    }

    private void personalizarLayout() {
        // (Sem alterações neste método)
        setTitle("Login");
        setSize(500, 600); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(COR_FUNDO_NOVO); 

        // --- PAINEL DO LOGO (TOPO) ---
        JPanel painelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelLogo.setBackground(COR_FUNDO_NOVO); 
        painelLogo.setBorder(new EmptyBorder(40, 20, 20, 20));

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
        JLabel lblLogo = new JLabel();
        if (logoIcon.getImage() != null) {
            Image img = logoIcon.getImage().getScaledInstance(-1, 80, Image.SCALE_SMOOTH); 
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("Logo App");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 24));
            lblLogo.setForeground(Color.WHITE); 
        }
        
        lblLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaInicial().setVisible(true);
                dispose();
            }
        });
        
        painelLogo.add(lblLogo);
        
        getContentPane().add(painelLogo, BorderLayout.NORTH);

        // --- PAINEL DO FORMULÁRIO (CENTRO) ---
        painelFormulario.setLayout(new GridBagLayout()); 
        painelFormulario.setBackground(COR_FUNDO_NOVO); 
        painelFormulario.setBorder(new EmptyBorder(20, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0; 

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        jLabel1.setFont(new Font("Arial", Font.BOLD, 24));
        jLabel1.setForeground(Color.WHITE); 
        painelFormulario.add(jLabel1, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        jLabel4.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel4.setForeground(Color.WHITE); 
        painelFormulario.add(jLabel4, gbc);

        gbc.gridy = 2;
        campoUsuario.setFont(FONTE_PADRAO);
        painelFormulario.add(campoUsuario, gbc);

        gbc.gridy = 3;
        jLabel3.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel3.setForeground(Color.WHITE); 
        painelFormulario.add(jLabel3, gbc);

        gbc.gridy = 4;
        campoSenha.setFont(FONTE_PADRAO);
        painelFormulario.add(campoSenha, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        ck_mostrarSenha.setBackground(COR_FUNDO_NOVO); 
        ck_mostrarSenha.setForeground(Color.WHITE); 
        painelFormulario.add(ck_mostrarSenha, gbc);
        
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5); 
        b_entrar.setFont(new Font("Arial", Font.BOLD, 16));
        b_entrar.setBackground(COR_DESTAQUE);
        b_entrar.setForeground(Color.WHITE);
        b_entrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b_entrar.setPreferredSize(new Dimension(150, 40));
        painelFormulario.add(b_entrar, gbc);

        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        lblLinkCadastro.setFont(FONTE_PADRAO);
        lblLinkCadastro.setForeground(Color.WHITE); 
        painelFormulario.add(lblLinkCadastro, gbc);

        getContentPane().add(painelFormulario, BorderLayout.CENTER);
        
        pack(); 
        setSize(500, 600); 
        setLocationRelativeTo(null); 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        painelFormulario = new javax.swing.JPanel(); // Renomeado
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campoUsuario = new javax.swing.JTextField();
        b_entrar = new javax.swing.JButton();
        campoSenha = new javax.swing.JPasswordField();
        ck_mostrarSenha = new javax.swing.JCheckBox();
        lblLinkCadastro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // O layout deste painel será redefinido em personalizarLayout()
        painelFormulario.setLayout(null); 

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Entrar na sua conta");
        painelFormulario.add(jLabel1);
        jLabel1.setBounds(77, 45, 261, 40);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Senha");
        painelFormulario.add(jLabel3);
        jLabel3.setBounds(77, 148, 55, 40);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Usuário");
        painelFormulario.add(jLabel4);
        jLabel4.setBounds(77, 102, 55, 40);
        
        painelFormulario.add(campoUsuario);
        campoUsuario.setBounds(138, 102, 199, 22); // Coordenadas originais

        b_entrar.setText("Entrar");
        b_entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_entrarActionPerformed(evt);
            }
        });
        painelFormulario.add(b_entrar);
        b_entrar.setBounds(262, 203, 75, 23); // Coordenadas originais

        campoSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaActionPerformed(evt);
            }
        });
        painelFormulario.add(campoSenha);
        campoSenha.setBounds(138, 148, 199, 22); // Coordenadas originais

        ck_mostrarSenha.setText("Mostrar senha");
        ck_mostrarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ck_mostrarSenhaActionPerformed(evt);
            }
        });
        painelFormulario.add(ck_mostrarSenha);
        ck_mostrarSenha.setBounds(138, 203, 93, 20); // Coordenadas originais

        lblLinkCadastro.setText("<html>Ainda não tem uma conta? <a href=\"#\">Cadastre-se</a>.</html>");
        lblLinkCadastro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLinkCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLinkCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLinkCadastroMouseClicked(evt);
            }
        });
        painelFormulario.add(lblLinkCadastro);
        lblLinkCadastro.setBounds(0, 241, 430, 16); // Coordenadas originais

        // Adiciona o painel (que será movido para o CENTRO em personalizarLayout())
        getContentPane().add(painelFormulario);
        painelFormulario.setBounds(0, 0, 430, 300); // Coordenadas originais

        pack();
    }// </editor-fold>                        

    private void b_entrarActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String usuario = campoUsuario.getText();
        char[] senha = campoSenha.getPassword();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o nome do usuário.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (senha.length == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, informe a senha.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // --- ALTERAÇÃO: Salva o nome do usuário na sessão ---
        SessaoUsuario.setNomeUsuario(usuario);
        // ----------------------------------------------------
        
        // Lógica de login (simulação)
        JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!", "Bem-vindo", JOptionPane.INFORMATION_MESSAGE);
        
        // Abre a tela inicial e fecha a de login
        new TelaInicial().setVisible(true);
        this.dispose();
    }                                        

    private void campoSenhaActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Permite logar pressionando Enter no campo de senha
        b_entrarActionPerformed(evt);
    }                                          

    private void ck_mostrarSenhaActionPerformed(java.awt.event.ActionEvent evt) {                                                
        if(ck_mostrarSenha.isSelected()){
            campoSenha.setEchoChar((char)0);
        } 
        else {
            campoSenha.setEchoChar('•'); // Usa '•' que é mais comum
        }
    }                                               


    private void lblLinkCadastroMouseClicked(java.awt.event.MouseEvent evt) {                                             
        javax.swing.JFrame frameCadastro = new javax.swing.JFrame("Cadastro de Usuário");
        
        frameCadastro.setContentPane(new tela_cadastro()); // Usa o JPanel de cadastro
        
        frameCadastro.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        frameCadastro.pack(); 
        frameCadastro.setLocationRelativeTo(null); 
        frameCadastro.setVisible(true); 
        
        this.dispose(); // Fecha a tela de login
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(tela_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

        java.awt.EventQueue.invokeLater(() -> new tela_login().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton b_entrar;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JTextField campoUsuario;
    private javax.swing.JCheckBox ck_mostrarSenha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblLinkCadastro;
    private javax.swing.JPanel painelFormulario; // Renomeado
    // End of variables declaration                   
}