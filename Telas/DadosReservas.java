import java.util.ArrayList;
import java.util.List;

/**
 * Classe centralizada para gerenciar os dados mockados das reservas.
 * Isso garante que a TelaInicial e a TelaReservas usem a mesma fonte de dados.
 */
public class DadosReservas {

    private static List<Reserva> listaDeReservas;

    /**
     * Retorna a lista de reservas, carregando-a se ainda não existir.
     */
    public static List<Reserva> getReservas() {
        if (listaDeReservas == null) {
            carregarDadosMock();
        }
        return listaDeReservas;
    }

    /**
     * Remove uma reserva da lista centralizada (usado pela tela_reserva).
     * @param index O índice da reserva a ser removida (baseado no modelRow).
     */
    public static void removerReserva(int index) {
        if (listaDeReservas != null && index >= 0 && index < listaDeReservas.size()) {
            listaDeReservas.remove(index);
        }
    }

    // --- NOVA FUNCIONALIDADE ---
    /**
     * Adiciona uma nova reserva à lista centralizada.
     * @param reserva A nova reserva a ser adicionada.
     */
    public static void adicionarReserva(Reserva reserva) {
        if (listaDeReservas == null) {
            getReservas(); // Garante que a lista esteja inicializada
        }
        // Adiciona no início da lista (índice 0) para que apareça no topo
        listaDeReservas.add(0, reserva);
    }
    // -------------------------

    /**
     * Carrega os dados simulados.
     */
    private static void carregarDadosMock() {
        listaDeReservas = new ArrayList<>();
        
        listaDeReservas.add(new Reserva(
            "#R1234", "São Paulo", "Recife", "19/05/2024 às 15:30", "Gol", "Confirmada", 950.00,
            "G3 1500", "Aeroporto de Guarulhos (GRU)", "Aeroporto Int. de Recife (REC)", "19/05/2024 às 18:40",
            "2", "B12", "Fulano de Tal", "CPF 123.456.789-00", "22A", "10/04/2024", "Cartão Visa **** 1234"
        ));
        listaDeReservas.add(new Reserva(
            "#R5678", "Rio de Janeiro", "Belo Horizonte", "25/05/2024 às 09:45", "Latam", "Pendente", 720.00,
            "LA 3201", "Aeroporto Santos Dumont (SDU)", "Aeroporto Int. de Confins (CNF)", "25/05/2024 às 10:50",
            "1", "A04", "Fulano de Tal", "CPF 123.456.789-00", "10C", "20/05/2024", "Pagamento Pendente"
        ));
        listaDeReservas.add(new Reserva(
            "#R9101", "Brasília", "Salvador", "02/07/2024 às 20:10", "Azul", "Cancelada", 650.00,
            "AD 4010", "Aeroporto Int. de Brasília (BSB)", "Aeroporto Int. de Salvador (SSA)", "02/07/2024 às 22:00",
            "1", "C05", "Fulano de Tal", "CPF 123.456.789-00", "5F", "01/06/2024", "Boleto Bancário"
        ));
         listaDeReservas.add(new Reserva(
            "#R1121", "São Paulo", "Porto Alegre", "10/08/2024 às 10:00", "Azul", "Confirmada", 880.00,
            "AD 4010", "Aeroporto de Congonhas (CGH)", "Aeroporto Int. Salgado Filho (POA)", "10/08/2024 às 11:45",
            "1", "D07", "Fulano de Tal", "CPF 123.456.789-00", "12B", "01/07/2024", "Cartão Master **** 5678"
        ));
    }
}