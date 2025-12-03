/**
 * Classe estática para armazenar dados da sessão do usuário,
 * como o nome de usuário após o login.
 */
public class SessaoUsuario {

    // Começa com "Fulano" como padrão, caso o login seja pulado
    private static String nomeUsuario = "Fulano";

    /**
     * Define o nome do usuário logado.
     */
    public static void setNomeUsuario(String nome) {
        if (nome != null && !nome.isEmpty()) {
            nomeUsuario = nome;
        }
    }

    /**
     * Retorna o nome do usuário logado.
     */
    public static String getNomeUsuario() {
        return nomeUsuario;
    }
}