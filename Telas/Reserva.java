import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe de modelo (POJO) que representa UMA reserva com todos os seus detalhes.
 * (Sem alterações nesta classe)
 */
public class Reserva {

    // --- Dados da Tabela Principal ---
    private String codigo;
    private String origem;
    private String destino;
    private String dataHoraPartida;
    private String companhiaAerea;
    private String status;
    private double preco;

    // --- Dados dos Detalhes ---
    private String numeroVoo;
    private String aeroportoOrigem;
    private String aeroportoDestino;
    private String dataHoraChegada;
    private String terminalPartida;
    private String portaoEmbarque;
    private String nomePassageiro;
    private String documentoPassageiro;
    private String assento;
    private String dataCompra;
    private String metodoPagamento;

    // Formatador de moeda (para o getter)
    private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    // Construtor
    public Reserva(String codigo, String origem, String destino, String dataHoraPartida, String companhiaAerea, String status, double preco, String numeroVoo, String aeroportoOrigem, String aeroportoDestino, String dataHoraChegada, String terminalPartida, String portaoEmbarque, String nomePassageiro, String documentoPassageiro, String assento, String dataCompra, String metodoPagamento) {
        this.codigo = codigo;
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.companhiaAerea = companhiaAerea;
        this.status = status;
        this.preco = preco;
        this.numeroVoo = numeroVoo;
        this.aeroportoOrigem = aeroportoOrigem;
        this.aeroportoDestino = aeroportoDestino;
        this.dataHoraChegada = dataHoraChegada;
        this.terminalPartida = terminalPartida;
        this.portaoEmbarque = portaoEmbarque;
        this.nomePassageiro = nomePassageiro;
        this.documentoPassageiro = documentoPassageiro;
        this.assento = assento;
        this.dataCompra = dataCompra;
        this.metodoPagamento = metodoPagamento;
    }

    // --- Getters ---

    public String getCodigo() {
        return codigo;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public String getDataHoraPartida() {
        return dataHoraPartida;
    }

    public String getCompanhiaAerea() {
        return companhiaAerea;
    }

    public String getStatus() {
        return status;
    }

    public double getPreco() {
        return preco;
    }
    
    public String getPrecoFormatado() {
        return FORMATO_MOEDA.format(preco);
    }

    public String getNumeroVoo() {
        return numeroVoo;
    }

    public String getAeroportoOrigem() {
        return aeroportoOrigem;
    }

    public String getAeroportoDestino() {
        return aeroportoDestino;
    }

    public String getDataHoraChegada() {
        return dataHoraChegada;
    }

    public String getTerminalPartida() {
        return terminalPartida;
    }

    public String getPortaoEmbarque() {
        return portaoEmbarque;
    }

    public String getNomePassageiro() {
        return nomePassageiro;
    }

    public String getDocumentoPassageiro() {
        return documentoPassageiro;
    }

    public String getAssento() {
        return assento;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }
    
    // --- Getters Helper (para a tabela) ---
    
    /**
     * Retorna o HTML formatado para a coluna da tabela.
     */
    public String getOrigemDestinoHtml() {
        return "<html><body style='width: 150px;'>" + origem + "<br><b>" + destino + "</b></html>";
    }

    // --- Setters (para o modo de edição) ---

    public void setNomePassageiro(String nomePassageiro) {
        this.nomePassageiro = nomePassageiro;
    }

    public void setDocumentoPassageiro(String documentoPassageiro) {
        this.documentoPassageiro = documentoPassageiro;
    }

    public void setAssento(String assento) {
        this.assento = assento;
    }
}