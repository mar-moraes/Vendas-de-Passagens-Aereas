import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCheckin extends JFrame {

    private JTextField txtCodigoReserva;
    private JTextField txtSobrenome;
    private JButton btnRealizarCheckin;

    public TelaCheckin() {
        setTitle("Check-in Online");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 51, 153));
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel("Check-in");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo);
        add(header, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        formPanel.add(criarLabel("Código da Reserva:"));
        txtCodigoReserva = criarTextField();
        formPanel.add(txtCodigoReserva);

        formPanel.add(criarLabel("Sobrenome do Passageiro:"));
        txtSobrenome = criarTextField();
        formPanel.add(txtSobrenome);

        add(formPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(0, 0, 20, 0));

        btnRealizarCheckin = new JButton("Realizar Check-in");
        btnRealizarCheckin.setFont(new Font("Arial", Font.BOLD, 14));
        btnRealizarCheckin.setBackground(new Color(255, 102, 0));
        btnRealizarCheckin.setForeground(Color.WHITE);
        btnRealizarCheckin.setFocusPainted(false);
        btnRealizarCheckin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRealizarCheckin.setPreferredSize(new Dimension(200, 40));

        btnRealizarCheckin.addActionListener(e -> realizarCheckin());

        footer.add(btnRealizarCheckin);
        add(footer, BorderLayout.SOUTH);
    }

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private JTextField criarTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 5, 5, 5)));
        return txt;
    }

    private void realizarCheckin() {
        String codigo = txtCodigoReserva.getText().trim();
        String sobrenome = txtSobrenome.getText().trim();

        if (codigo.isEmpty() || sobrenome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Simulação de validação
        if (codigo.length() < 5) {
            JOptionPane.showMessageDialog(this, "Código de reserva inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Check-in realizado com sucesso!\n\n" +
                        "Passageiro: " + sobrenome.toUpperCase() + "\n" +
                        "Reserva: " + codigo + "\n" +
                        "Assento: 12F (Confirmado)",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
