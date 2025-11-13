
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {

    private JButton btnMinhasReservas, btnLogin, btnBuscarVoos;
    private JTextField txtOrigem, txtDestino, txtData;
    private JLabel lblPromocao, lblNovosDestinos;

    public TelaInicial() {
        setTitle("Venda de Passagens Aéreas");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ======== TOPO =========
        JPanel topoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnMinhasReservas = new JButton("Minhas Reservas");
        btnLogin = new JButton("Login");
        topoPanel.add(btnMinhasReservas);
        topoPanel.add(btnLogin);
        add(topoPanel, BorderLayout.NORTH);

        // ======== CENTRO =========
        JPanel centroPanel = new JPanel();
        centroPanel.setLayout(new BoxLayout(centroPanel, BoxLayout.Y_AXIS));
        centroPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel titulo = new JLabel("Venda de Passagens Aéreas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centroPanel.add(titulo);
        centroPanel.add(Box.createVerticalStrut(20));

        // Campos de busca
        centroPanel.add(new JLabel("Origem:"));
        txtOrigem = new JTextField();
        centroPanel.add(txtOrigem);

        centroPanel.add(new JLabel("Destino:"));
        txtDestino = new JTextField();
        centroPanel.add(txtDestino);

        centroPanel.add(new JLabel("Datas (Ida e Volta):"));
        txtData = new JTextField("dd/mm/aaaa - dd/mm/aaaa");
        centroPanel.add(txtData);

        centroPanel.add(Box.createVerticalStrut(20));

        // Botão buscar voos
        btnBuscarVoos = new JButton("Buscar Voos");
        btnBuscarVoos.setAlignmentX(Component.CENTER_ALIGNMENT);
        centroPanel.add(btnBuscarVoos);

        add(centroPanel, BorderLayout.CENTER);

        // ======== RODAPÉ =========
        JPanel rodapePanel = new JPanel(new GridLayout(1, 2));
        lblPromocao = new JLabel("Promoção: R$152,80 ou 7.000 pontos!", SwingConstants.CENTER);
        lblNovosDestinos = new JLabel("<html>Novos destinos diretos:<br>Porto Seguro, Salvador, Fortaleza<br>Saindo de Congonhas</html>", SwingConstants.CENTER);
        rodapePanel.add(lblPromocao);
        rodapePanel.add(lblNovosDestinos);
        add(rodapePanel, BorderLayout.SOUTH);

        // ======== FUNCIONALIDADES =========
        configurarEventos();
    }

    private void configurarEventos() {
        // Botão Buscar Voos
        btnBuscarVoos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origem = txtOrigem.getText();
                String destino = txtDestino.getText();
                String data = txtData.getText();

                if (origem.isEmpty() || destino.isEmpty() || data.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos para buscar voos!");
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Buscando voos de " + origem + " para " + destino + " em " + data);
                }
            }
        });

        // Botão Minhas Reservas
        btnMinhasReservas.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Abrindo tela de reservas...");
            new TelaPagamento().setVisible(true); // Exemplo: abrir a tela de pagamento
            dispose(); // Fecha a tela atual
        });

        // Botão Login
        btnLogin.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Abrindo tela de login...");
        });
    }
}