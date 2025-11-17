import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class tela_cadastro extends javax.swing.JPanel {

    // --- Constantes de Design ---
    private static final Color COR_PRINCIPAL = new Color(0, 51, 153); // Azul do logo
    private static final Color COR_DESTAQUE = new Color(255, 102, 0); // Laranja do logo
    private static final Color COR_FUNDO_NOVO = new Color(44, 117, 204); // #4297f7
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);
    // ----------------------------

    public tela_cadastro() {
        initComponents();
        personalizarLayout();
    }
    
    private void personalizarLayout() {
        // Define o layout principal do *este* JPanel como BorderLayout
        this.setLayout(new BorderLayout());
        this.setBackground(COR_FUNDO_NOVO); // <<< CORREÇÃO

        // --- PAINEL DO LOGO (TOPO) ---
        JPanel painelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelLogo.setBackground(COR_FUNDO_NOVO); // <<< CORREÇÃO
        painelLogo.setBorder(new EmptyBorder(40, 20, 20, 20));

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logo.png"));
        JLabel lblLogo = new JLabel();
        if (logoIcon.getImage() != null) {
            Image img = logoIcon.getImage().getScaledInstance(-1, 80, Image.SCALE_SMOOTH); 
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("Logo App");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 24));
            lblLogo.setForeground(Color.WHITE); // <<< CORREÇÃO
        }
        painelLogo.add(lblLogo);
        
        // Adiciona o painel do logo ao TOPO
        this.add(painelLogo, BorderLayout.NORTH);

        // --- PAINEL DO FORMULÁRIO (CENTRO) ---
        // (jPanel1 foi renomeado para painelFormulario no código gerado)
        
        // Remove o layout absoluto
        painelFormulario.setLayout(new GridBagLayout()); 
        painelFormulario.setBackground(COR_FUNDO_NOVO); // <<< CORREÇÃO
        painelFormulario.setBorder(new EmptyBorder(20, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa 2 colunas
        gbc.anchor = GridBagConstraints.CENTER;
        jLabel1.setFont(new Font("Arial", Font.BOLD, 24));
        jLabel1.setForeground(Color.WHITE); // <<< CORREÇÃO
        painelFormulario.add(jLabel1, gbc);

        // --- Labels e Campos ---
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // Alinha labels à direita

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        jLabel2.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel2.setForeground(Color.WHITE); // <<< CORREÇÃO
        painelFormulario.add(jLabel2, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START; // Campos à esquerda
        campoNome.setFont(FONTE_PADRAO);
        painelFormulario.add(campoNome, gbc);
        
        // E-mail
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        jLabel3.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel3.setForeground(Color.WHITE); // <<< CORREÇÃO
        painelFormulario.add(jLabel3, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        campoEmail.setFont(FONTE_PADRAO);
        painelFormulario.add(campoEmail, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        jLabel6.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel6.setForeground(Color.WHITE); // <<< CORREÇÃO
        painelFormulario.add(jLabel6, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        campoTelefone.setFont(FONTE_PADRAO);
        painelFormulario.add(campoTelefone, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        jLabel4.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel4.setForeground(Color.WHITE); // <<< CORREÇÃO
        painelFormulario.add(jLabel4, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        campoSenha.setFont(FONTE_PADRAO);
        painelFormulario.add(campoSenha, gbc);

        // Confirmar Senha
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        jLabel5.setFont(new Font("Arial", Font.BOLD, 14));
        jLabel5.setForeground(Color.WHITE); // <<< CORREÇÃO
        painelFormulario.add(jLabel5, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        campoConfirmarSenha.setFont(FONTE_PADRAO);
        painelFormulario.add(campoConfirmarSenha, gbc);

        // Botão Cadastrar
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        b_cadastro.setFont(new Font("Arial", Font.BOLD, 16));
        b_cadastro.setBackground(COR_DESTAQUE);
        b_cadastro.setForeground(Color.WHITE);
        b_cadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b_cadastro.setPreferredSize(new Dimension(200, 40));
        painelFormulario.add(b_cadastro, gbc);

        // Link Voltar
        gbc.gridy = 7;
        lblLinkVoltar.setForeground(Color.WHITE); // <<< CORREÇÃO
        painelFormulario.add(lblLinkVoltar, gbc);

        // Adiciona o painel do formulário ao CENTRO
        this.add(painelFormulario, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        painelFormulario = new javax.swing.JPanel(); // Renomeado
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        campoConfirmarSenha = new javax.swing.JPasswordField();
        campoSenha = new javax.swing.JPasswordField();
        campoNome = new javax.swing.JTextField();
        campoEmail = new javax.swing.JTextField();
        campoTelefone = new javax.swing.JTextField();
        b_cadastro = new javax.swing.JButton();
        lblLinkVoltar = new javax.swing.JLabel(); // NOVO LINK

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        // O layout deste painel será redefinido em personalizarLayout()
        painelFormulario.setLayout(null); 

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crie sua conta ");
        painelFormulario.add(jLabel1);
        jLabel1.setBounds(151, 18, 189, 54); // Coords originais

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Nome");
        painelFormulario.add(jLabel2);
        jLabel2.setBounds(114, 99, 105, 17); // Coords originais

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("E-mail");
        painelFormulario.add(jLabel3);
        jLabel3.setBounds(114, 139, 105, 17); // Coords originais

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Senha");
        painelFormulario.add(jLabel4);
        jLabel4.setBounds(114, 219, 105, 17); // Coords originais

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Confirmar senha");
        painelFormulario.add(jLabel5);
        jLabel5.setBounds(114, 259, 105, 17); // Coords originais

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Telefone");
        painelFormulario.add(jLabel6);
        jLabel6.setBounds(114, 179, 105, 17); // Coords originais

        campoConfirmarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoConfirmarSenhaActionPerformed(evt);
            }
        });
        painelFormulario.add(campoConfirmarSenha);
        campoConfirmarSenha.setBounds(225, 259, 168, 22); // Coords originais

        campoSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaActionPerformed(evt);
            }
        });
        painelFormulario.add(campoSenha);
        campoSenha.setBounds(225, 219, 168, 22); // Coords originais

        campoNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeActionPerformed(evt);
            }
        });
        painelFormulario.add(campoNome);
        campoNome.setBounds(225, 99, 168, 22); // Coords originais

        campoEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoEmailActionPerformed(evt);
            }
        });
        painelFormulario.add(campoEmail);
        campoEmail.setBounds(225, 139, 168, 22); // Coords originais

        campoTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoTelefoneActionPerformed(evt);
            }
        });
        painelFormulario.add(campoTelefone);
        campoTelefone.setBounds(225, 179, 168, 22); // Coords originais

        b_cadastro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        b_cadastro.setText("Cadastrar");
        b_cadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_cadastroActionPerformed(evt);
            }
        });
        painelFormulario.add(b_cadastro);
        b_cadastro.setBounds(114, 311, 296, 34); // Coords originais
        
        // --- NOVO LINK PARA VOLTAR ---
        lblLinkVoltar.setText("<html><a href=\"#\">Voltar para o Login</a></html>");
        lblLinkVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLinkVoltar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLinkVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLinkVoltarMouseClicked(evt);
            }
        });
        // Adiciona ao painel (coordenadas serão ignoradas)
        painelFormulario.add(lblLinkVoltar);

        // Adiciona o painel (que será movido para o CENTRO em personalizarLayout())
        add(painelFormulario);
        painelFormulario.setBounds(0, 0, 502, 401); // Coords originais
    }// </editor-fold>                        

    private void campoSenhaActionPerformed(java.awt.event.ActionEvent evt) {                                           
        campoConfirmarSenha.requestFocus();
    }                                          

    private void campoConfirmarSenhaActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        b_cadastroActionPerformed(evt);
    }                                                   

    private void campoNomeActionPerformed(java.awt.event.ActionEvent evt) {                                          
        campoEmail.requestFocus();
    }                                         

    private void campoEmailActionPerformed(java.awt.event.ActionEvent evt) {                                           
        campoTelefone.requestFocus();
    }                                          

    private void campoTelefoneActionPerformed(java.awt.event.ActionEvent evt) {                                              
        campoSenha.requestFocus();
    }                                             

    private void b_cadastroActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String telefone = campoTelefone.getText();
        char[] senha = campoSenha.getPassword();
        char[] confirmarSenha = campoConfirmarSenha.getPassword();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.length == 0 || confirmarSenha.length == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        if (!java.util.Arrays.equals(senha, confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem. Tente novamente.", "Erro", JOptionPane.WARNING_MESSAGE);
            campoSenha.setText("");
            campoConfirmarSenha.setText("");
            return; 
        }

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos
        campoNome.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        campoSenha.setText("");
        campoConfirmarSenha.setText("");

        java.util.Arrays.fill(senha, ' ');
        java.util.Arrays.fill(confirmarSenha, ' ');  

        // Volta para a tela de login
        lblLinkVoltarMouseClicked(null);
    }                                          

    /**
     * Ação do novo link "Voltar"
     */
    private void lblLinkVoltarMouseClicked(java.awt.event.MouseEvent evt) {
        // Encontra o JFrame "pai" deste JPanel
        Window janela = SwingUtilities.getWindowAncestor(this);
        
        // Cria a nova tela de login
        tela_login telaLogin = new tela_login();
        telaLogin.setVisible(true);
        
        // Fecha a janela atual (que contém o painel de cadastro)
        if (janela != null) {
            janela.dispose();
        }
    }
    
    public static void main(String args[]) {
        javax.swing.JFrame frameDeTeste = new javax.swing.JFrame("Teste Cadastro");
        frameDeTeste.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        
        // Adiciona o painel de cadastro
        frameDeTeste.add(new tela_cadastro());
        
        frameDeTeste.pack();
        frameDeTeste.setSize(550, 700); // Define um tamanho razoável
        frameDeTeste.setLocationRelativeTo(null);
        frameDeTeste.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton b_cadastro;
    private javax.swing.JPasswordField campoConfirmarSenha;
    private javax.swing.JTextField campoEmail;
    private javax.swing.JTextField campoNome;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JTextField campoTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel painelFormulario; // Renomeado
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblLinkVoltar; // NOVO
    // End of variables declaration                   
}