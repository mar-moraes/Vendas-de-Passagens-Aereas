import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class tela_detalhes_reserva extends JDialog {

    private Reserva reserva;

    // Campos para Edição
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

    private boolean solicitouExclusao = false;

    // --- Cores Padronizadas ---
    private Color corPrincipal;
    private Color corDestaque;
    private static final Font FONTE_PADRAO = new Font("Arial", Font.PLAIN, 14);
    private static final Font FONTE_LABEL = new Font("Arial", Font.BOLD, 14);

    /**
     * Construtor atualizado para receber as cores.
     */
    public tela_detalhes_reserva(Frame owner, Reserva reserva, Color corPrincipal, Color corDestaque) {
        super(owner, "Detalhes da Reserva: " + reserva.getCodigo(), true);
        this.reserva = reserva;
        this.corPrincipal = (corPrincipal != null) ? corPrincipal : new Color(0, 51, 153);
        this.corDestaque = (corDestaque != null) ? corDestaque : new Color(255, 102, 0);

        setSize(700, 650); // Aumentei a altura
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(owner);
        getContentPane().setBackground(Color.WHITE);

        JPanel painelConteudo = new JPanel(new BorderLayout(10, 10));
        painelConteudo.setBorder(new EmptyBorder(15, 15, 15, 15));
        painelConteudo.setBackground(Color.WHITE);

        // 1. Painel de Detalhes (Centro)
        JPanel painelDetalhes = new JPanel();
        painelDetalhes.setLayout(new BoxLayout(painelDetalhes, BoxLayout.Y_AXIS));
        painelDetalhes.setBackground(Color.WHITE);

        painelDetalhes.add(criarSecaoVoo());
        painelDetalhes.add(Box.createRigidArea(new Dimension(0, 15)));
        painelDetalhes.add(criarSecaoPassageiro());
        painelDetalhes.add(Box.createRigidArea(new Dimension(0, 15)));
        painelDetalhes.add(criarSecaoPagamento());

        JScrollPane scrollPane = new JScrollPane(painelDetalhes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);

        painelConteudo.add(scrollPane, BorderLayout.CENTER);

        // 2. Painel de Ações (Sul)
        painelConteudo.add(criarSecaoAcoes(), BorderLayout.SOUTH);

        add(painelConteudo);

        alternarModoEdicao(false);
    }

    // Construtor de fallback (caso as cores não sejam passadas)
    public tela_detalhes_reserva(Frame owner, Reserva reserva) {
        this(owner, reserva, null, null);
    }

    public boolean foiExclusaoSolicitada() {
        return this.solicitouExclusao;
    }

    /**
     * Cria um painel de seção com Título e GridBagLayout
     */
    private JPanel criarPainelSecao(String titulo) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                titulo,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                corPrincipal)); // Usa a cor principal no título
        return painel;
    }

    private void adicionarDetalhe(JPanel painel, String label, Component valorComponent, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Label
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.3; // Mais espaço para o label
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONTE_LABEL);
        painel.add(lbl, gbc);

        // Componente de Valor
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        valorComponent.setFont(FONTE_PADRAO);
        painel.add(valorComponent, gbc);
    }

    private JLabel criarLabelValor(String texto) {
        JLabel val = new JLabel(texto);
        val.setFont(FONTE_PADRAO);
        return val;
    }

    private JPanel criarCampoEditavel(String textoInicial, String tipo) {
        JLabel label = new JLabel(textoInicial);
        label.setFont(FONTE_PADRAO);

        JTextField textField = new JTextField(textoInicial);
        textField.setFont(FONTE_PADRAO);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(corPrincipal),
                new EmptyBorder(3, 3, 3, 3)));

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

        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.add(label, "LEITURA");
        cardPanel.add(textField, "EDICAO");

        if ("nome".equals(tipo))
            cardPanelNome = cardPanel;
        else if ("documento".equals(tipo))
            cardPanelDocumento = cardPanel;
        else if ("assento".equals(tipo))
            cardPanelAssento = cardPanel;

        return cardPanel;
    }

    private JPanel criarSecaoVoo() {
        JPanel painel = criarPainelSecao("Detalhes do Voo");

        adicionarDetalhe(painel, "Status:", criarLabelValor(reserva.getStatus()), 0);
        adicionarDetalhe(painel, "Companhia:",
                criarLabelValor(reserva.getCompanhiaAerea() + " (" + reserva.getNumeroVoo() + ")"), 1);
        adicionarDetalhe(painel, "Origem:", criarLabelValor(reserva.getAeroportoOrigem()), 2);
        adicionarDetalhe(painel, "Destino:", criarLabelValor(reserva.getAeroportoDestino()), 3);
        adicionarDetalhe(painel, "Partida:", criarLabelValor(reserva.getDataHoraPartida() + " (Terminal "
                + reserva.getTerminalPartida() + ", Portão " + reserva.getPortaoEmbarque() + ")"), 4);
        adicionarDetalhe(painel, "Chegada:", criarLabelValor(reserva.getDataHoraChegada()), 5);

        return painel;
    }

    private JPanel criarSecaoPassageiro() {
        JPanel painel = criarPainelSecao("Passageiro");

        adicionarDetalhe(painel, "Nome:", criarCampoEditavel(reserva.getNomePassageiro(), "nome"), 0);
        adicionarDetalhe(painel, "Documento:", criarCampoEditavel(reserva.getDocumentoPassageiro(), "documento"), 1);
        adicionarDetalhe(painel, "Assento:", criarCampoEditavel(reserva.getAssento(), "assento"), 2);

        return painel;
    }

    private JPanel criarSecaoPagamento() {
        JPanel painel = criarPainelSecao("Pagamento");

        adicionarDetalhe(painel, "Valor Total:", criarLabelValor(reserva.getPrecoFormatado()), 0);
        adicionarDetalhe(painel, "Data da Compra:", criarLabelValor(reserva.getDataCompra()), 1);
        adicionarDetalhe(painel, "Método:", criarLabelValor(reserva.getMetodoPagamento()), 2);

        return painel;
    }

    private void acaoRemoverReserva() {
        int confirm = JOptionPane.showConfirmDialog(
                tela_detalhes_reserva.this,
                "Tem certeza que deseja cancelar a reserva selecionada?",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            this.solicitouExclusao = true;
            this.dispose();
        }
    }

    /**
     * Aplica o estilo de botão principal (cor de destaque).
     */
    private void estilizarBotaoPrincipal(JButton botao) {
        botao.setBackground(corDestaque);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setFocusPainted(false);
    }

    /**
     * Aplica o estilo de botão secundário (discreto).
     */
    private void estilizarBotaoSecundario(JButton botao) {
        botao.setBackground(Color.WHITE);
        botao.setForeground(new Color(100, 100, 100));
        botao.setFont(new Font("Arial", Font.PLAIN, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    private JPanel criarSecaoAcoes() {
        JPanel painelAcoes = new JPanel(new BorderLayout());
        painelAcoes.setBackground(Color.WHITE);
        painelAcoes.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        painelAcoes.setBorder(new EmptyBorder(10, 0, 0, 0));

        // --- Painel 1: Botões do Modo Leitura ---
        painelBotoesLeitura = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelBotoesLeitura.setBackground(Color.WHITE);

        if ("Confirmada".equals(reserva.getStatus())) {
            JButton btnAlterar = new JButton("🔄 Alterar Reserva");
            estilizarBotaoPrincipal(btnAlterar);
            btnAlterar.addActionListener(e -> alternarModoEdicao(true));
            painelBotoesLeitura.add(btnAlterar);

            JButton btnRemoverConfirmada = new JButton("❌ Remover Reserva");
            estilizarBotaoSecundario(btnRemoverConfirmada);
            btnRemoverConfirmada.addActionListener(e -> acaoRemoverReserva());
            painelBotoesLeitura.add(btnRemoverConfirmada);

            JButton btnComprovante = new JButton("📄 Gerar Comprovante");
            estilizarBotaoSecundario(btnComprovante);
            btnComprovante.addActionListener(e -> simularGerarPDF());
            painelBotoesLeitura.add(btnComprovante);

        } else if ("Pendente".equals(reserva.getStatus())) {
            JButton btnPagar = new JButton("Efetuar Pagamento");
            estilizarBotaoPrincipal(btnPagar);
            btnPagar.addActionListener(e -> {
                TelaPagamento telaPagamento = new TelaPagamento(reserva);
                telaPagamento.setVisible(true);
                this.dispose();
                if (getOwner() != null)
                    getOwner().dispose();
            });
            painelBotoesLeitura.add(btnPagar);

            JButton btnRemoverPendente = new JButton("❌ Remover Reserva");
            estilizarBotaoSecundario(btnRemoverPendente);
            btnRemoverPendente.addActionListener(e -> acaoRemoverReserva());
            painelBotoesLeitura.add(btnRemoverPendente);

        } else {
            JLabel lblCancelada = new JLabel("Reserva cancelada.");
            lblCancelada.setFont(new Font("Arial", Font.ITALIC, 14));
            lblCancelada.setForeground(Color.RED);
            painelBotoesLeitura.add(lblCancelada);
        }

        // --- Painel 2: Botões do Modo Edição ---
        painelBotoesEdicao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelBotoesEdicao.setBackground(Color.WHITE);

        JButton btnSalvar = new JButton("✅ Salvar Alterações");
        estilizarBotaoPrincipal(btnSalvar);
        btnSalvar.setBackground(new Color(0, 120, 0)); // Verde para salvar
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        painelBotoesEdicao.add(btnSalvar);

        JButton btnCancelar = new JButton("❌ Cancelar Edição");
        estilizarBotaoSecundario(btnCancelar);
        btnCancelar.addActionListener(e -> cancelarEdicao());
        painelBotoesEdicao.add(btnCancelar);

        JPanel painelAcoesEsquerda = new JPanel();
        painelAcoesEsquerda.setBackground(Color.WHITE);
        painelAcoesEsquerda.add(painelBotoesLeitura);
        painelAcoesEsquerda.add(painelBotoesEdicao);

        // --- Botão Voltar (Sempre visível) ---
        JPanel painelVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        painelVoltar.setBackground(Color.WHITE);
        JButton btnVoltar = new JButton("Voltar");
        estilizarBotaoSecundario(btnVoltar);
        btnVoltar.addActionListener(e -> {
            if (painelBotoesEdicao.isVisible()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Você tem alterações não salvas. Deseja descartá-las e voltar?",
                        "Descartar Alterações?",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            this.dispose();
        });
        painelVoltar.add(btnVoltar);

        painelAcoes.add(painelAcoesEsquerda, BorderLayout.WEST);
        painelAcoes.add(painelVoltar, BorderLayout.EAST);

        return painelAcoes;
    }

    // --- MÉTODOS DE AÇÃO ---

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

    private void salvarAlteracoes() {
        String novoNome = txtNome.getText();
        String novoDoc = txtDocumento.getText();
        String novoAssento = txtAssento.getText();

        if (novoNome.isEmpty() || novoDoc.isEmpty() || novoAssento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        reserva.setNomePassageiro(novoNome);
        reserva.setDocumentoPassageiro(novoDoc);
        reserva.setAssento(novoAssento);

        lblNomeValor.setText(novoNome);
        lblDocumentoValor.setText(novoDoc);
        lblAssentoValor.setText(novoAssento);

        alternarModoEdicao(false);

        JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarEdicao() {
        // Restaura os valores originais
        txtNome.setText(reserva.getNomePassageiro());
        txtDocumento.setText(reserva.getDocumentoPassageiro());
        txtAssento.setText(reserva.getAssento());

        alternarModoEdicao(false);
    }

    private void simularGerarPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Comprovante PDF");
        fileChooser.setSelectedFile(new File("Reserva_" + reserva.getCodigo().replace("#", "") + ".pdf"));
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
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}