import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class TelaResultadosBusca extends JDialog {

    private JTable tabelaResultados;
    private DefaultTableModel modelTabela;
    
    // --- ALTERAÇÃO: Adicionadas variáveis para guardar origem/destino ---
    private String origem;
    private String destino;
    // -----------------------------------------------------------------
    
    private static final Color COR_PRINCIPAL = new Color(0, 51, 153);
    private static final Color COR_DESTAQUE = new Color(255, 102, 0);

    public TelaResultadosBusca(Frame owner, String origem, String destino, String dataBusca) {
        super(owner, "Resultados da Busca", true); 
        
        // --- ALTERAÇÃO: Salva origem e destino ---
        this.origem = origem;
        this.destino = destino;
        // -----------------------------------------
        
        setSize(700, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelConteudo = new JPanel(new BorderLayout(10, 10));
        painelConteudo.setBorder(new EmptyBorder(15, 15, 15, 15));
        painelConteudo.setBackground(Color.WHITE);

        // --- Título ---
        JLabel lblTitulo = new JLabel(String.format("Voos de %s para %s", origem, destino), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(COR_PRINCIPAL);
        painelConteudo.add(lblTitulo, BorderLayout.NORTH);

        // --- Tabela de Resultados ---
        String[] colunas = {"Data Partida", "Hora Partida", "Previsão Chegada", "Companhia", "Preço"};
        modelTabela = new DefaultTableModel(colunas, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tabelaResultados = new JTable(modelTabela);
        configurarTabela();
        
        gerarDadosAleatorios(dataBusca);

        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        scrollPane.getViewport().setBackground(Color.WHITE);
        painelConteudo.add(scrollPane, BorderLayout.CENTER);

        // --- Botões de Ação ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(Color.WHITE);
        
        JButton btnSelecionar = new JButton("Selecionar Voo");
        btnSelecionar.setBackground(COR_DESTAQUE);
        btnSelecionar.setForeground(Color.WHITE);
        btnSelecionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSelecionar.addActionListener(e -> acaoSelecionarVoo());
        
        // --- NOVA FUNCIONALIDADE: Botão Reservar Voo ---
        JButton btnReservar = new JButton("Reservar Voo");
        btnReservar.setFont(new Font("Arial", Font.BOLD, 14));
        btnReservar.setBackground(COR_PRINCIPAL); // Cor diferente
        btnReservar.setForeground(Color.WHITE);
        btnReservar.addActionListener(e -> acaoReservarVoo());
        // ------------------------------------------------
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVoltar.addActionListener(e -> dispose());

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnReservar); // Adicionado
        painelBotoes.add(btnSelecionar);
        painelConteudo.add(painelBotoes, BorderLayout.SOUTH);

        add(painelConteudo);
    }
    
    private void configurarTabela() {
        // (Sem alterações)
        tabelaResultados.setRowHeight(25);
        tabelaResultados.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaResultados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaResultados.getTableHeader().setBackground(Color.WHITE);
        tabelaResultados.getTableHeader().setForeground(COR_PRINCIPAL);
        tabelaResultados.getColumnModel().getColumn(4).setCellRenderer(new tela_reserva.CurrencyRenderer()); 
    }
    
    private void acaoSelecionarVoo() {
        // (Sem alterações)
        if (tabelaResultados.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um voo na lista.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Voo selecionado! Você será redirecionado para o pagamento.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        
        Window owner = getOwner(); 
        
        TelaPagamento telaPagamento = new TelaPagamento();
        telaPagamento.setVisible(true);
        
        owner.dispose(); 
        this.dispose(); 
    }

    // --- NOVA FUNCIONALIDADE: Ação para o botão "Reservar Voo" ---
    private void acaoReservarVoo() {
        int selectedRow = tabelaResultados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um voo na lista para reservar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Obter dados da tabela
        String dataPartida = (String) modelTabela.getValueAt(selectedRow, 0);
        String horaPartida = (String) modelTabela.getValueAt(selectedRow, 1);
        String previsaoChegada = (String) modelTabela.getValueAt(selectedRow, 2);
        String companhia = (String) modelTabela.getValueAt(selectedRow, 3);
        double preco = (double) modelTabela.getValueAt(selectedRow, 4);

        // 2. Criar uma nova Reserva (com dados simulados/placeholders)
        Random rand = new Random();
        String novoCodigo = "#R" + (rand.nextInt(9000) + 1000); // Ex: #R8123
        String numVoo = companhia.substring(0, 2).toUpperCase() + " " + (rand.nextInt(4000) + 1000);
        String dataCompra = "17/11/2025"; // Data de hoje (simulada)

        Reserva novaReserva = new Reserva(
            novoCodigo, this.origem, this.destino,
            dataPartida + " às " + horaPartida, // dataHoraPartida
            companhia, "Pendente", preco,       // status, preco
            numVoo,                             // numeroVoo
            this.origem + " (Aeroporto)",       // aeroportoOrigem (placeholder)
            this.destino + " (Aeroporto)",      // aeroportoDestino (placeholder)
            previsaoChegada,                    // dataHoraChegada (já está formatada)
            "1", "A1",                          // terminal, portao (placeholders)
            SessaoUsuario.getNomeUsuario(),     // nomePassageiro (pega da sessão!)
            "CPF Pendente",                     // documentoPassageiro (placeholder)
            "A-" + (rand.nextInt(30) + 1),      // assento (placeholder)
            dataCompra,                         // dataCompra
            "Pagamento Pendente"                // metodoPagamento
        );

        // 3. Adicionar à lista central
        DadosReservas.adicionarReserva(novaReserva);

        // 4. Feedback
        JOptionPane.showMessageDialog(this, 
            "Voo reservado com sucesso! (Status: Pendente)\nVocê será redirecionado para 'Minhas Reservas'.", 
            "Reserva Concluída", 
            JOptionPane.INFORMATION_MESSAGE);

        // 5. Navegar para tela_reserva
        Window owner = getOwner(); // Pega a TelaInicial

        JFrame frameReservas = new JFrame("Minhas Reservas");
        frameReservas.setSize(1000, 700);
        frameReservas.setLocationRelativeTo(null);
        frameReservas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Passa a TelaInicial (owner) como a "tela anterior" para poder voltar
        frameReservas.add(new tela_reserva((JFrame) owner));
        frameReservas.setVisible(true);

        owner.dispose(); // Fecha a TelaInicial
        this.dispose(); // Fecha esta dialog
    }
    // -------------------------------------------------------------

    private void gerarDadosAleatorios(String dataBusca) {
        // (Sem alterações)
        Random random = new Random();
        int numVoos = random.nextInt(5) + 1; // 1 a 5 voos
        String[] companhias = {"Gol", "Latam", "Azul"};
        
        String dataIda = dataBusca.split(" - ")[0];

        for (int i = 0; i < numVoos; i++) {
            int diaOffset = random.nextInt(3); 
            String dataPartida = somarDiasData(dataIda, diaOffset);

            int horaPartida = random.nextInt(24);
            int minPartida = random.nextInt(60);
            String strHoraPartida = String.format("%02d:%02d", horaPartida, minPartida);
            
            int duracaoHoras = random.nextInt(5) + 1; 
            
            int horaChegada = horaPartida + duracaoHoras;
            String dataChegada = dataPartida;
            
            if (horaChegada >= 24) {
                horaChegada -= 24;
                dataChegada = somarDiasData(dataPartida, 1);
            }
            String strHoraChegada = String.format("%02d:%02d (%s)", horaChegada, minPartida, dataChegada);
            
            String companhia = companhias[random.nextInt(companhias.length)];
            double preco = 500 + random.nextInt(1000); 

            modelTabela.addRow(new Object[]{dataPartida, strHoraPartida, strHoraChegada, companhia, preco});
        }
    }
    
    private String somarDiasData(String data, int dias) {
        // (Sem alterações)
        try {
            int dia = Integer.parseInt(data.substring(0, 2));
            String resto = data.substring(2); // /MM/yyyy
            return String.format("%02d%s", dia + dias, resto);
        } catch (Exception e) {
            return "Data Inválida";
        }
    }
}