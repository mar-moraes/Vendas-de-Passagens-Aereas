import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Um JDialog modal que exibe os detalhes completos de uma Reserva.
 * --- CLASSE ATUALIZADA ---
 * (Agora gerencia "Modo Leitura" e "Modo Edição" e sinaliza exclusão)
 */
public class tela_detalhes_reserva extends JDialog {

    private Reserva reserva;
    
    // --- Campos para Modo de Edição ---
    private JLabel lblNomeValor;
    private JLabel lblDocumentoValor;
    private JLabel lblAssentoValor;
    private JTextField txtNome;
    private JTextField txtDocumento;
    private JTextField txtAssento;
    private JPanel cardPanelNome;
    private JPanel cardPanelDocumento;
    private JPanel cardPanelAssento;
    private JPanel painelBotoesLeitura;
    private JPanel painelBotoesEdicao;
    
    // --- NOVO CAMPO PARA SINALIZAR EXCLUSÃO ---
    private boolean solicitouExclusao = false;
    // ------------------------------------------

    /**
     * Construtor da tela de detalhes.
     * @param owner O Frame pai (a tela principal)
     * @param reserva O objeto Reserva com todos os dados a serem exibidos.
     */
    public tela_detalhes_reserva(Frame owner, Reserva reserva) {
        super(owner, "Detalhes da Reserva: " + reserva.getCodigo(), true); 
        this.reserva = reserva;
        
        setSize(700, 600);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(owner); 

        JPanel painelConteudo = new JPanel(new BorderLayout(10, 10));
        painelConteudo.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 1. Painel de Detalhes (Centro)
        JPanel painelDetalhes = new JPanel();
        painelDetalhes.setLayout(new BoxLayout(painelDetalhes, BoxLayout.Y_AXIS));
        painelDetalhes.add(criarSecaoVoo());
        painelDetalhes.add(Box.createRigidArea(new Dimension(0, 15))); 
        painelDetalhes.add(criarSecaoPassageiro()); 
        painelDetalhes.add(Box.createRigidArea(new Dimension(0, 15))); 
        painelDetalhes.add(criarSecaoPagamento());
        
        JScrollPane scrollPane = new JScrollPane(painelDetalhes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        painelConteudo.add(scrollPane, BorderLayout.CENTER);

        // 2. Painel de Ações (Sul)
        painelConteudo.add(criarSecaoAcoes(), BorderLayout.SOUTH); 

        add(painelConteudo);
        
        alternarModoEdicao(false);
    }
    
    // --- NOVO MÉTODO GETTER ---
    /**
     * Usado pela tela_reserva para verificar se a exclusão foi confirmada.
     * @return true se o usuário confirmou a exclusão, false caso contrário.
     */
    public boolean foiExclusaoSolicitada() {
        return this.solicitouExclusao;
    }
    // --------------------------

    /**
     * Cria um painel de seção com um Título e GridBagLayout
     */
    private JPanel criarPainelSecao(String titulo) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), 
                titulo, 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("SansSerif", Font.BOLD, 14), 
                Color.BLACK));
        return painel;
    }

    /**
     * Adiciona um par (Label, Valor) a um painel com GridBagLayout
     * @param valorComponent O Componente de valor (pode ser um JLabel ou JPanel com CardLayout)
     */
    private void adicionarDetalhe(JPanel painel, String label, Component valorComponent, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Label (Negrito)
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.1; 
        JLabel lbl = new JLabel("<html><b>" + label + "</b></html>");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painel.add(lbl, gbc);

        // Componente de Valor (JLabel, JTextField, etc.)
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.weightx = 0.9; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        valorComponent.setFont(new Font("SansSerif", Font.PLAIN, 14));
        painel.add(valorComponent, gbc);
    }
    
    /**
     * Helper para criar um JLabel de valor simples (não-editável)
     */
    private JLabel criarLabelValor(String texto) {
        JLabel val = new JLabel(texto);
        val.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return val;
    }

    /**
     * Helper para criar um painel "Card" que troca entre JLabel e JTextField
     * @param textoInicial O texto do JLabel e do JTextField
     * @return O JPanel com CardLayout
     */
    private JPanel criarCampoEditavel(String textoInicial, String tipo) {
        // 1. Cria os componentes
        JLabel label = new JLabel(textoInicial);
        JTextField textField = new JTextField(textoInicial);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // 2. Armazena as referências nos campos da classe
        if ("nome".equals(tipo)) {
            lblNomeValor = label;
            txtNome = textField;
        } else if ("documento".equals(tipo)) {
            lblDocumentoValor = label;
            txtDocumento = textField;
        } else if ("assento".equals(tipo)) {
            lblAssentoValor = label;
            txtAssento = textField;
        }

        // 3. Cria o painel com CardLayout
        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.add(label, "LEITURA"); // Card 1
        cardPanel.add(textField, "EDICAO"); // Card 2
        
        // 4. Armazena o painel para o método 'alternarModoEdicao'
        if ("nome".equals(tipo)) cardPanelNome = cardPanel;
        else if ("documento".equals(tipo)) cardPanelDocumento = cardPanel;
        else if ("assento".equals(tipo)) cardPanelAssento = cardPanel;

        return cardPanel;
    }


    /**
     * Cria a seção com detalhes do Voo (Não-editável)
     */
    private JPanel criarSecaoVoo() {
        JPanel painel = criarPainelSecao("Detalhes do Voo");

        adicionarDetalhe(painel, "Status:", criarLabelValor(reserva.getStatus()), 0);
        adicionarDetalhe(painel, "Companhia:", criarLabelValor(reserva.getCompanhiaAerea() + " (" + reserva.getNumeroVoo() + ")"), 1);
        adicionarDetalhe(painel, "Origem:", criarLabelValor(reserva.getAeroportoOrigem()), 2);
        adicionarDetalhe(painel, "Destino:", criarLabelValor(reserva.getAeroportoDestino()), 3);
        adicionarDetalhe(painel, "Partida:", criarLabelValor(reserva.getDataHoraPartida() + " (Terminal " + reserva.getTerminalPartida() + ", Portão " + reserva.getPortaoEmbarque() + ")"), 4);
        adicionarDetalhe(painel, "Chegada:", criarLabelValor(reserva.getDataHoraChegada()), 5);

        return painel;
    }

    /**
     * Cria a seção com detalhes do Passageiro (Agora com campos editáveis)
     */
    private JPanel criarSecaoPassageiro() {
        JPanel painel = criarPainelSecao("Passageiro");

        adicionarDetalhe(painel, "Nome:", criarCampoEditavel(reserva.getNomePassageiro(), "nome"), 0);
        adicionarDetalhe(painel, "Documento:", criarCampoEditavel(reserva.getDocumentoPassageiro(), "documento"), 1);
        adicionarDetalhe(painel, "Assento:", criarCampoEditavel(reserva.getAssento(), "assento"), 2);

        return painel;
    }

    /**
     * Cria a seção com detalhes do Pagamento (Não-editável)
     */
    private JPanel criarSecaoPagamento() {
        JPanel painel = criarPainelSecao("Pagamento");

        adicionarDetalhe(painel, "Valor Total:", criarLabelValor(reserva.getPrecoFormatado()), 0);
        adicionarDetalhe(painel, "Data da Compra:", criarLabelValor(reserva.getDataCompra()), 1);
        adicionarDetalhe(painel, "Método:", criarLabelValor(reserva.getMetodoPagamento()), 2);

        return painel;
    }
    
    /**
     * Ação de clique unificada para os botões "Remover Reserva".
     * Pergunta, define a flag e fecha a janela.
     */
    private void acaoRemoverReserva() {
        int confirm = JOptionPane.showConfirmDialog(
            tela_detalhes_reserva.this, // o "this" é o JDialog
            "Tem certeza que deseja cancelar a reserva selecionada?",
            "Confirmar Cancelamento",
            JOptionPane.YES_NO_OPTION
        );
    
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Avisar a tela_reserva que a exclusão foi confirmada
            this.solicitouExclusao = true;
            // 2. Fechar a tela de detalhes (como pedido)
            this.dispose(); 
        }
    }

    /**
     * Cria o painel inferior com botões contextuais (Leitura e Edição)
     * --- MÉTODO ATUALIZADO ---
     */
    private JPanel criarSecaoAcoes() {
        JPanel painelAcoes = new JPanel(new BorderLayout());
        
        // --- Painel 1: Botões do Modo Leitura ---
        painelBotoesLeitura = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        // Lógica de ações contextuais
        if ("Confirmada".equals(reserva.getStatus())) {
            JButton btnAlterar = new JButton("🔄 Alterar Reserva");
            btnAlterar.addActionListener(e -> alternarModoEdicao(true));
            painelBotoesLeitura.add(btnAlterar);
            
            // Botão Remover (Confirmada)
            JButton btnRemoverConfirmada = new JButton("❌ Remover Reserva");
            btnRemoverConfirmada.addActionListener(e -> acaoRemoverReserva()); // LÓGICA ATUALIZADA
            painelBotoesLeitura.add(btnRemoverConfirmada);
            
            JButton btnComprovante = new JButton("📄 Gerar Comprovante");
            btnComprovante.addActionListener(e -> simularGerarPDF());
            painelBotoesLeitura.add(btnComprovante);
            
        } else if ("Pendente".equals(reserva.getStatus())) {
            painelBotoesLeitura.add(new JButton("Efetuar Pagamento"));
            
            // Botão Remover (Pendente)
            JButton btnRemoverPendente = new JButton("❌ Remover Reserva");
            btnRemoverPendente.addActionListener(e -> acaoRemoverReserva()); // LÓGICA ATUALIZADA
            painelBotoesLeitura.add(btnRemoverPendente);
            
        } else {
            painelBotoesLeitura.add(new JLabel("Reserva cancelada."));
        }
        
        // --- Painel 2: Botões do Modo Edição ---
        painelBotoesEdicao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JButton btnSalvar = new JButton("✅ Salvar Alterações");
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        painelBotoesEdicao.add(btnSalvar);
        
        JButton btnCancelar = new JButton("❌ Cancelar Edição");
        btnCancelar.addActionListener(e -> cancelarEdicao());
        painelBotoesEdicao.add(btnCancelar);

        
        // Adiciona os dois painéis (um ficará invisível)
        JPanel painelAcoesEsquerda = new JPanel();
        painelAcoesEsquerda.add(painelBotoesLeitura);
        painelAcoesEsquerda.add(painelBotoesEdicao);
        
        // --- Botão Voltar (Sempre visível) ---
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            // Se estiver editando, pergunta antes de sair
            if (painelBotoesEdicao.isVisible()) {
                int confirm = JOptionPane.showConfirmDialog(
                    this, 
                    "Você tem alterações não salvas. Deseja descartá-las e voltar?",
                    "Descartar Alterações?",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.NO_OPTION) {
                    return; // Não faz nada
                }
            }
            // Fecha o JDialog
            this.dispose();
        });
        painelVoltar.add(btnVoltar);

        painelAcoes.add(painelAcoesEsquerda, BorderLayout.WEST);
        painelAcoes.add(painelVoltar, BorderLayout.EAST);
        
        return painelAcoes;
    }
    
    // --- MÉTODOS DE AÇÃO ---

    /**
     * Alterna a visibilidade dos componentes entre Leitura e Edição
     * @param modoEdicao true para modo de edição, false para modo de leitura
     */
    private void alternarModoEdicao(boolean modoEdicao) {
        CardLayout clNome = (CardLayout) cardPanelNome.getLayout();
        CardLayout clDoc = (CardLayout) cardPanelDocumento.getLayout();
        CardLayout clAssento = (CardLayout) cardPanelAssento.getLayout();
        
        if (modoEdicao) {
            clNome.show(cardPanelNome, "EDICAO");
            clDoc.show(cardPanelDocumento, "EDICAO");
            clAssento.show(cardPanelAssento, "EDICAO");
            painelBotoesLeitura.setVisible(false);
            painelBotoesEdicao.setVisible(true);
            setTitle("Editando Reserva: " + reserva.getCodigo());
        } else {
            clNome.show(cardPanelNome, "LEITURA");
            clDoc.show(cardPanelDocumento, "LEITURA");
            clAssento.show(cardPanelAssento, "LEITURA");
            painelBotoesLeitura.setVisible(true);
            painelBotoesEdicao.setVisible(false);
            setTitle("Detalhes da Reserva: " + reserva.getCodigo());
        }
    }
    
    /**
     * Ação do botão "Salvar Alterações"
     */
    private void salvarAlteracoes() {
        String novoNome = txtNome.getText();
        String novoDoc = txtDocumento.getText();
        String novoAssento = txtAssento.getText();
        
        reserva.setNomePassageiro(novoNome);
        reserva.setDocumentoPassageiro(novoDoc);
        reserva.setAssento(novoAssento);
        
        lblNomeValor.setText(novoNome);
        lblDocumentoValor.setText(novoDoc);
        lblAssentoValor.setText(novoAssento);
        
        alternarModoEdicao(false);
        
        JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Ação do botão "Cancelar Edição"
     */
    private void cancelarEdicao() {
        txtNome.setText(reserva.getNomePassageiro());
        txtDocumento.setText(reserva.getDocumentoPassageiro());
        txtAssento.setText(reserva.getAssento());
        
        alternarModoEdicao(false);
    }
    
    /**
     * Simula a geração de PDF (ação do botão)
     */
    private void simularGerarPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Comprovante PDF");
        fileChooser.setSelectedFile(new File("Reserva_" + reserva.getCodigo() + ".pdf"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos PDF (*.pdf)", "pdf"));

        int userSelection = fileChooser.showSaveDialog(tela_detalhes_reserva.this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            JOptionPane.showMessageDialog(
                tela_detalhes_reserva.this,
                "Simulação: PDF do comprovante salvo com sucesso em:\n" + filePath,
                "PDF Gerado",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}