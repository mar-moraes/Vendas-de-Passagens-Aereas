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

    private String origem;
    private String destino;

    private static final Color COR_PRINCIPAL = new Color(0, 51, 153);
    private static final Color COR_DESTAQUE = new Color(255, 102, 0);

    public TelaResultadosBusca(Frame owner, String origem, String destino, String dataBusca) {
        super(owner, "Resultados da Busca", true);

        this.origem = origem;
        this.destino = destino;

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
        String[] colunas = { "Data Partida", "Hora Partida", "Previsão Chegada", "Companhia", "Preço" };
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

        JButton btnReservar = new JButton("Reservar Voo");
        btnReservar.setFont(new Font("Arial", Font.BOLD, 14));
        btnReservar.setBackground(COR_PRINCIPAL);
        btnReservar.setForeground(Color.WHITE);
        btnReservar.addActionListener(e -> acaoReservarVoo());

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVoltar.addActionListener(e -> dispose());

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnReservar);
        painelBotoes.add(btnSelecionar);
        painelConteudo.add(painelBotoes, BorderLayout.SOUTH);

        add(painelConteudo);
    }

    private void configurarTabela() {
        tabelaResultados.setRowHeight(25);
        tabelaResultados.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaResultados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaResultados.getTableHeader().setBackground(Color.WHITE);
        tabelaResultados.getTableHeader().setForeground(COR_PRINCIPAL);
        tabelaResultados.getColumnModel().getColumn(4).setCellRenderer(new tela_reserva.CurrencyRenderer());
    }

    private void acaoSelecionarVoo() {
        int selectedRow = tabelaResultados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um voo na lista.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obter dados do voo selecionado
        String dataPartida = (String) modelTabela.getValueAt(selectedRow, 0);
        String horaPartida = (String) modelTabela.getValueAt(selectedRow, 1);
        String previsaoChegada = (String) modelTabela.getValueAt(selectedRow, 2);
        String companhia = (String) modelTabela.getValueAt(selectedRow, 3);
        double preco = (double) modelTabela.getValueAt(selectedRow, 4);

        // Empacotar dados para passar para a próxima tela
        Object[] dadosVoo = {
                dataPartida, horaPartida, previsaoChegada, companhia, preco,
                this.origem, this.destino
        };

        Window owner = getOwner();

        // Fecha a tela inicial (owner) e a tela de resultados (this)
        // para dar lugar à TelaSelecaoAssentos como janela principal (JFrame)
        if (owner != null) {
            owner.dispose();
        }
        this.dispose();

        TelaSelecaoAssentos telaAssentos = new TelaSelecaoAssentos(dadosVoo);
        telaAssentos.setVisible(true);
    }

    private void acaoReservarVoo() {
        int selectedRow = tabelaResultados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um voo na lista para reservar.", "Erro",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dataPartida = (String) modelTabela.getValueAt(selectedRow, 0);
        String horaPartida = (String) modelTabela.getValueAt(selectedRow, 1);
        String previsaoChegada = (String) modelTabela.getValueAt(selectedRow, 2);
        String companhia = (String) modelTabela.getValueAt(selectedRow, 3);
        double preco = (double) modelTabela.getValueAt(selectedRow, 4);

        Random rand = new Random();
        String novoCodigo = "#R" + (rand.nextInt(9000) + 1000);
        String numVoo = companhia.substring(0, 2).toUpperCase() + " " + (rand.nextInt(4000) + 1000);
        String dataCompra = "17/11/2025";

        Reserva novaReserva = new Reserva(
                novoCodigo, this.origem, this.destino,
                dataPartida + " às " + horaPartida,
                companhia, "Pendente", preco,
                numVoo,
                this.origem + " (Aeroporto)",
                this.destino + " (Aeroporto)",
                previsaoChegada,
                "1", "A1",
                SessaoUsuario.getNomeUsuario(),
                "CPF Pendente",
                "A-" + (rand.nextInt(30) + 1),
                dataCompra,
                "Pagamento Pendente");

        DadosReservas.adicionarReserva(novaReserva);

        Window owner = getOwner();

        if (owner != null) {
            owner.dispose();
        }
        this.dispose();

        JFrame frameReservas = new JFrame("Minhas Reservas");
        frameReservas.setSize(1000, 700);
        frameReservas.setLocationRelativeTo(null);
        frameReservas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameReservas.add(new tela_reserva(null));
        frameReservas.setVisible(true);
    }

    private void gerarDadosAleatorios(String dataBusca) {
        Random random = new Random();
        int numVoos = random.nextInt(5) + 1;
        String[] companhias = { "Gol", "Latam", "Azul" };

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

            modelTabela.addRow(new Object[] { dataPartida, strHoraPartida, strHoraChegada, companhia, preco });
        }
    }

    private String somarDiasData(String data, int dias) {
        try {
            int dia = Integer.parseInt(data.substring(0, 2));
            String resto = data.substring(2); // /MM/yyyy
            return String.format("%02d%s", dia + dias, resto);
        } catch (Exception e) {
            return "Data Inválida";
        }
    }
}