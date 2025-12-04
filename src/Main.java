import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            /* * Define o Look and Feel do sistema para uma aparência mais nativa
             * É bom colocar isso antes de criar qualquer janela.
             */
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            TelaInicial tela = new TelaInicial();
            tela.setVisible(true);
        });
    }
}