
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPagamento extends JFrame {

    private JButton btnVoltar, btnConfirmar;
    private JRadioButton rbCredito, rbDebito, rbPontos;
    private JTextField txtNumeroCartao, txtValidade, txtCVV;
    private ButtonGroup grupoPagamento;

    public TelaPagamento() {
        setTitle("Tela de Pagamento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TOPO =====
        JPanel topoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVoltar = new JButton("Voltar");
        topoPanel.add(btnVoltar);
        add(topoPanel, BorderLayout.NORTH);

        // ===== CENTRO =====
        JPanel centroPanel = new JPanel();
        centroPanel.setLayout(new BoxLayout(centroPanel, BoxLayout.Y_AXIS));
        centroPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel titulo = new JLabel("Pagamento", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centroPanel.add(titulo);
        centroPanel.add(Box.createVerticalStrut(20));

        // ===== Forma de pagamento =====
        JLabel lblForma = new JLabel("Forma de Pagamento:");
        lblForma.setFont(new Font("Arial", Font.BOLD, 14));
        centroPanel.add(lblForma);

        rbCredito = new JRadioButton("Cartão de Crédito");
        rbDebito = new JRadioButton("Cartão de Débito");
        rbPontos = new JRadioButton("Pontos (Programa de Fidelidade)");

        grupoPagamento = new ButtonGroup();
        grupoPagamento.add(rbCredito);
        grupoPagamento.add(rbDebito);
        grupoPagamento.add(rbPontos);

        centroPanel.add(rbCredito);
        centroPanel.add(rbDebito);
        centroPanel.add(rbPontos);

        centroPanel.add(Box.createVerticalStrut(20));

        // ===== Dados do cartão =====
        JLabel lblDados = new JLabel("Dados do Cartão:");
        lblDados.setFont(new Font("Arial", Font.BOLD, 14));
        centroPanel.add(lblDados);

        centroPanel.add(new JLabel("Número do Cartão:"));
        txtNumeroCartao = new JTextField();
        centroPanel.add(txtNumeroCartao);

        centroPanel.add(new JLabel("Validade (MM/AA):"));
        txtValidade = new JTextField();
        centroPanel.add(txtValidade);

        centroPanel.add(new JLabel("CVV:"));
        txtCVV = new JTextField();
        centroPanel.add(txtCVV);

        centroPanel.add(Box.createVerticalStrut(20));

        // ===== Botão Confirmar =====
        btnConfirmar = new JButton("Confirmar Pagamento");
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        centroPanel.add(btnConfirmar);

        add(centroPanel, BorderLayout.CENTER);

        configurarEventos();
    }

    private void configurarEventos() {
        // Botão Voltar
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaInicial().setVisible(true);
                dispose(); // Fecha esta tela
            }
        });

        // Botão Confirmar Pagamento
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!rbCredito.isSelected() && !rbDebito.isSelected() && !rbPontos.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Selecione uma forma de pagamento!");
                    return;
                }

                String numero = txtNumeroCartao.getText().trim();
                String validade = txtValidade.getText().trim();
                String cvv = txtCVV.getText().trim();

                if (numero.isEmpty() || validade.isEmpty() || cvv.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os dados do cartão!");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Pagamento confirmado com sucesso!");
                new TelaInicial().setVisible(true);
                dispose();
            }
        });
    }
}