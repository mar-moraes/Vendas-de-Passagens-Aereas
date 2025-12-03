import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaSelecaoAssentos extends JFrame {

    private static final Color COR_PRINCIPAL = new Color(0, 51, 153);
    private static final Color COR_DESTAQUE = new Color(255, 102, 0);
    private static final Color COR_ASSENTO_LIVRE = Color.WHITE;
    private static final Color COR_ASSENTO_OCUPADO = Color.RED;
    private static final Color COR_ASSENTO_SELECIONADO = new Color(0, 153, 51);; // Azul para selecionado

    private List<JButton> assentosSelecionados = new ArrayList<>();
    private String assentoEscolhido = null;
    private Object[] dadosVoo; // Para passar para a próxima tela

    public TelaSelecaoAssentos(Object[] dadosVoo) {
        super("Seleção de Assentos - Boeing 737");
        this.dadosVoo = dadosVoo;

        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // --- Cabeçalho ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(COR_PRINCIPAL);
        headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("Selecione seu Assento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);
        add(headerPanel, BorderLayout.NORTH);

        // --- Painel de Assentos (Boeing 737: 3-3) ---
        JPanel painelAssentos = new JPanel(new GridLayout(0, 7, 10, 10)); // 3 assentos + corredor + 3 assentos
        painelAssentos.setBorder(new EmptyBorder(20, 20, 20, 20));
        painelAssentos.setBackground(Color.WHITE);

        // Letras das colunas
        String[] colunas = { "A", "B", "C", "", "D", "E", "F" };
        for (String col : colunas) {
            JLabel lblCol = new JLabel(col, SwingConstants.CENTER);
            lblCol.setFont(new Font("Arial", Font.BOLD, 16));
            painelAssentos.add(lblCol);
        }

        // Gerar assentos (ex: 20 fileiras)
        int numFileiras = 20;
        Random random = new Random();
        int assentosOcupadosPreviamente = random.nextInt(5) + 1; // 1 a 5 passageiros já reservaram
        List<String> assentosOcupados = gerarAssentosOcupados(numFileiras, assentosOcupadosPreviamente);

        for (int i = 1; i <= numFileiras; i++) {
            for (int j = 0; j < 7; j++) {
                if (j == 3) { // Corredor
                    painelAssentos.add(new JLabel(""));
                } else {
                    String letra = colunas[j];
                    String numeroAssento = i + letra;
                    JButton btnAssento = new JButton(numeroAssento);
                    estilizarBotaoAssento(btnAssento);

                    if (assentosOcupados.contains(numeroAssento)) {
                        btnAssento.setBackground(COR_ASSENTO_OCUPADO);
                        btnAssento.setBorder(BorderFactory.createLineBorder(COR_ASSENTO_OCUPADO));
                        btnAssento.setEnabled(false);
                        btnAssento.setToolTipText("Ocupado");
                    } else {
                        btnAssento.addActionListener(e -> selecionarAssento(btnAssento));
                    }
                    painelAssentos.add(btnAssento);
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(painelAssentos);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // --- Legenda ---
        JPanel painelLegenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelLegenda.setBackground(Color.WHITE);
        painelLegenda.add(criarItemLegenda("Livre", COR_ASSENTO_LIVRE));
        painelLegenda.add(criarItemLegenda("Selecionado", COR_ASSENTO_SELECIONADO));
        painelLegenda.add(criarItemLegenda("Ocupado", COR_ASSENTO_OCUPADO));

        // --- Botões de Ação ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        painelBotoes.setBackground(Color.WHITE);

        JButton btnCancelar = new JButton("Cancelar reserva");
        btnCancelar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.GRAY);
        btnCancelar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setPreferredSize(new Dimension(150, 40));
        btnCancelar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });
        Color corBotaoContinuar = Color.decode("#063D97");

        JButton btnContinuar = new JButton("Continuar a reserva");
        btnContinuar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnContinuar.setFont(new Font("Arial", Font.BOLD, 14));
        btnContinuar.setBackground(corBotaoContinuar);
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setBorder(BorderFactory.createLineBorder(corBotaoContinuar));
        btnContinuar.setFocusPainted(false);
        btnContinuar.setPreferredSize(new Dimension(180, 40));
        btnContinuar.addActionListener(e -> avancarParaPagamento());

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnContinuar);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.add(painelLegenda, BorderLayout.NORTH);
        footerPanel.add(painelBotoes, BorderLayout.SOUTH);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private List<String> gerarAssentosOcupados(int fileiras, int quantidade) {
        List<String> ocupados = new ArrayList<>();
        Random random = new Random();
        String[] letras = { "A", "B", "C", "D", "E", "F" };

        while (ocupados.size() < quantidade) {
            int fila = random.nextInt(fileiras) + 1;
            String letra = letras[random.nextInt(letras.length)];
            String assento = fila + letra;
            if (!ocupados.contains(assento)) {
                ocupados.add(assento);
            }
        }
        return ocupados;
    }

    private void estilizarBotaoAssento(JButton btn) {
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBackground(COR_ASSENTO_LIVRE);
        btn.setBorder(BorderFactory.createLineBorder(COR_PRINCIPAL)); // Borda azul para todos
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 10));
        btn.setMargin(new Insets(2, 2, 2, 2));
    }

    private JPanel criarItemLegenda(String texto, Color cor) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        painel.setBackground(Color.WHITE);

        JPanel corBox = new JPanel();
        corBox.setPreferredSize(new Dimension(20, 20));
        corBox.setBackground(cor);
        corBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        painel.add(corBox);
        painel.add(label);
        return painel;
    }

    private void selecionarAssento(JButton btn) {
        // Se já estava selecionado, deseleciona
        if (assentosSelecionados.contains(btn)) {
            btn.setBackground(COR_ASSENTO_LIVRE);
            assentosSelecionados.remove(btn);
            assentoEscolhido = null;
        } else {
            // Se outro estava selecionado, limpa a seleção anterior (permite apenas 1 por
            // vez por enquanto)
            if (!assentosSelecionados.isEmpty()) {
                JButton anterior = assentosSelecionados.get(0);
                anterior.setBackground(COR_ASSENTO_LIVRE);
                assentosSelecionados.clear();
            }

            btn.setBackground(COR_ASSENTO_SELECIONADO);
            assentosSelecionados.add(btn);
            assentoEscolhido = btn.getText();
        }
    }

    private void avancarParaPagamento() {
        if (assentoEscolhido == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um assento.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Criar reserva pendente com os dados do voo e assento
        // Nota: A lógica de criação da Reserva que estava em TelaResultadosBusca deve
        // vir para cá ou ser adaptada
        // Para simplificar, vou passar os dados para a TelaPagamento ou criar a reserva
        // aqui

        // Recuperando dados do voo passados pelo construtor
        // Ordem esperada em dadosVoo: {dataPartida, horaPartida, previsaoChegada,
        // companhia, preco, origem, destino}

        String dataPartida = (String) dadosVoo[0];
        String horaPartida = (String) dadosVoo[1];
        String previsaoChegada = (String) dadosVoo[2];
        String companhia = (String) dadosVoo[3];
        double preco = (double) dadosVoo[4];
        String origem = (String) dadosVoo[5];
        String destino = (String) dadosVoo[6];

        Random rand = new Random();
        String novoCodigo = "#R" + (rand.nextInt(9000) + 1000);
        String numVoo = companhia.substring(0, 2).toUpperCase() + " " + (rand.nextInt(4000) + 1000);
        String dataCompra = "22/11/2025"; // Data atual simulada

        Reserva novaReserva = new Reserva(
                novoCodigo, origem, destino,
                dataPartida + " às " + horaPartida,
                companhia, "Pendente", preco,
                numVoo,
                origem + " (Aeroporto)",
                destino + " (Aeroporto)",
                previsaoChegada,
                "1", "A1",
                SessaoUsuario.getNomeUsuario(),
                "CPF Pendente",
                assentoEscolhido, // Assento selecionado!
                dataCompra,
                "Pagamento Pendente");

        // Adiciona a reserva como pendente (ou passa para tela de pagamento para
        // confirmar depois)
        DadosReservas.adicionarReserva(novaReserva);

        TelaPagamento telaPagamento = new TelaPagamento(novaReserva);
        telaPagamento.setVisible(true);
        dispose();
    }
}
