#include <stdio.h>
#include <locale.h>
#include <math.h>
#include <string.h>

int opcao, totalPassagens = 0, c = 0, encontrados = 0;

int i = 0;

int passEditadoPOS = 0;

char buscado[10];

char *res;

int opcao, codigo, totalIdentificado = 0;

char busca[20], verificacaoCD;

int nivelAcesso;




typedef struct{
    int codigo, quantidade,destino;
    char nome[30];
    float precoVenda;
}Passagens;

typedef struct{
    int codigo, quantidadeVendida;
    Passagens passagem;
    char data[10];
} Venda;

// lista de produtos
Venda vendasRealizadas[50];
int totalVendas = 0;




/*Maria Eduarda Rodrigues Franco Camargo CP3012069
Pablo Ferreira Fontes CP300743X
Rafael Franco Lourençon CP3013138
Vanessa Amaral Dos Santos CP3012298 */

Passagens listaDePassagens[50];

void editarPassagem() {

    int edPass = buscarPassagem();

    if ( edPass >= 0 ) {

        Passagens p = listaDePassagens[ edPass ];

        printf("\nPassagem encontrada: %s\n", p.nome);

        int opcaoOpcao;

        do{

            printf("\n\n1) Alterar o nome\n");
            printf("2) Alterar o preco de venda\n");
            printf("3) Alterar o estoque\n");
            printf("0) Sair\n");
            printf("\nEscolha uma opcao: ");
            scanf("%d", &opcaoOpcao);

            switch(opcaoOpcao) {

                case 1:
                    ("\n-----------------------------------------------------------------------------------");

                    printf("\nNome atual: %s\n\nNovo nome: ", p.nome);
                    getchar();
                    scanf("%29[^\n]", listaDePassagens[ edPass ].nome );

                    printf("\nNome do produto atualizado para: %s", listaDePassagens[edPass].nome );
                    ("\n-----------------------------------------------------------------------------------");
                    break;

                case 2:
                    ("\n-----------------------------------------------------------------------------------");

                    printf("\nPreco de venda atual: R$ %.2f\nNovo preco de venda: R$ ", p.precoVenda);
                    getchar();
                    scanf("%f", &listaDePassagens[edPass].precoVenda );

                    printf("\nPreco de venda do produto atualizado para: R$ %.2f", listaDePassagens[edPass].precoVenda );
                    printf("\n\n-----------------------------------------------\n");
                    break;

                case 3:
                    ("\n-----------------------------------------------------------------------------------");

                    printf("\nEstoque atual: %d\nNovo estoque: ", p.quantidade);
                    getchar();
                    scanf("%d", &listaDePassagens[edPass].quantidade );

                    printf("\nEstoque atualizado para: %d", listaDePassagens[edPass].quantidade);
                    ("\n-----------------------------------------------------------------------------------");
                    break;

                case 0: break;
                default: printf("\nOpcao invalida!\n");

            }

        } while(opcaoOpcao!=0);

    }

}

int buscarPassagem() {

    setbuf(stdin, NULL);

    getchar();

    printf("\nDigite parte do nome do passagem: ");

    scanf("%9[^\n]", &buscado);

    setbuf(stdin, NULL);

    for(int x = 0; x < totalPassagens; x++) {

        Passagens p = listaDePassagens[ x ];

        res = strstr(strlwr(p.nome), strlwr(buscado));

        if ( res ) { return x; }

    }

    printf("\nNenhuma passagem encontrada com: %s.", buscado);

    return -1;

}

void buscarPassagens() {

    setbuf(stdin, NULL);

    printf("Digite parte do nome da passagem: ");

    scanf("%9[^\n]", &buscado);

    setbuf(stdin, NULL);

    printf("\nCodigo\tEstoque\tPreco Venda\tNome da passagem");

    encontrados = 0;

    for(int x = 0; x < totalPassagens; x++) {

        Passagens p = listaDePassagens[ x ];

        res = strstr(p.nome, buscado);

        if ( res ) {

            encontrados++;

            printf("\n%d\t%d\t%.2f\t\t%.2f\t\t%s", p.codigo, p.quantidade, p.precoVenda, p.nome);

        }

    }

    if (encontrados == 0) {
        printf("\nNenhuma passagem encontrada com: %s.", buscado);
    } else {
        printf("\n%d passagem(ns) encontrada(s)\n", encontrados);
    }

}

void listarPassagens() {

    printf("\nPASSAGENS CADASTRADAS\n");

                printf("\n-----------------------------------------------------------------------------------");

                printf("\nCódigo\t\tNome do Destino\t\tQuantidade\tPreço de Venda(Passagem)");

                for(i = 0; i < totalPassagens; i++) {
                    Passagens p = listaDePassagens[ i ];
                    printf("\n%d \t\t%s \t\t\t%d \t\t%.2f", p.codigo, p.nome, p.quantidade, p.precoVenda);

                }

                printf("\n-----------------------------------------------------------------------------------");

                printf("\nTotal:\t%d passagem(ns) cadastrado(s)\n", totalPassagens);
}

void cadastroPassagens() {

    printf("\n\nCADASTRO DE PASSAGEM\n");

                getchar();

                if( totalPassagens == 54){

                    printf("-----------------------------------------------\n");

                    printf("Não foi possível cadastrar mais passagens!!!\n");

                    printf("-----------------------------------------------\n");

                }
                else {

                    do{
                        verificacaoCD = 's';

                        printf("-----------------------------------------------\n");

                        printf("\nCódigo (inteiro positivo): ");
                        scanf( "%d", &listaDePassagens[ totalPassagens ].codigo );

                        for(i=0; i<totalPassagens; i++){

                            if( listaDePassagens[ i ].codigo == listaDePassagens[ totalPassagens ].codigo){

                                verificacaoCD = 'n';
                            }
                        }
                        if( verificacaoCD == 'n' ){

                            printf("\nEste código ja esta sendo utilizado, Digite novamente!!!\n");
                        }
                    }while( verificacaoCD == 'n' );


                    if ( listaDePassagens[ totalPassagens ].codigo < 0 ){

                        printf("---------------\n");

                        printf("Código Inválido\n");

                        printf("---------------\n");

                    } else {

                        getchar();

                        printf("\nNome do Destino: ");
                        scanf( "%29[^\n]", &listaDePassagens[ totalPassagens ].nome );

                        printf("\nQuantidade de passagens no estoque : ");
                        scanf( "%d", &listaDePassagens[ totalPassagens ].quantidade );

                        if(listaDePassagens [ totalPassagens ].quantidade < 0){

                            printf("------------------------------\n");

                            printf("QUANTIDADE DO ESTOQUE INVÁLIDO\n");

                            printf("------------------------------\n");

                        }else{

                                printf("\nPreço de venda (R$): ");
                                scanf( "%f", &listaDePassagens [ totalPassagens ].precoVenda );

                                printf("-----------------------------------------------\n");

                                if(listaDePassagens [ totalPassagens ].precoVenda < 0){

                                    printf("-----------------------\n");

                                    printf("PREÇO DE VENDA INVÁLIDO\n");

                                    printf("-----------------------\n");

                                }else{

                                    printf("\nPASSAGEM CADASTRADA:\n");

                                    printf("-----------------------------------------------\n");

                                    printf("\nCódigo: %d\n", listaDePassagens[ totalPassagens ].codigo);

                                    printf("\nNome do destino: %s\n", listaDePassagens[ totalPassagens ].nome);

                                    printf("\nQuantidade em estoque: %d\n", listaDePassagens [ totalPassagens ].quantidade);

                                    printf("\nPreço de venda: R$ %.2f\n\n", listaDePassagens [ totalPassagens ].precoVenda);

                                    printf("-----------------------------------------------\n");

                                    totalPassagens++;

                                }
                            }
                        }
                    }
}

void entradaEstoque() {

    passEditadoPOS = buscarPassagem();

    if (passEditadoPOS >= 0) {

        Passagens p = listaDePassagens[ passEditadoPOS ];

        printf("\nPassagem que esta tendo entrada: %s\n", p.nome);

        printf("\nQtde em estoque atualmente: %d\n", p.quantidade);

        printf("\nQuantas unidades estao sendo inseridas?");
        setbuf(stdin, NULL);
        int qtde;
        scanf("%d", &qtde);

        qtde = qtde + p.quantidade;

        listaDePassagens[ passEditadoPOS ].quantidade = qtde;

        printf("\nNova quantidade em estoque de passagem %s : %d\n\n", p.nome, qtde );

    }
}

void buscarPeloCodigo() {

                totalIdentificado = 0;

                printf("\n\nBUSCAR PELO CÓDIGO\n\n");

                printf("\n----------------------------------");

                printf("\nDigite o código a ser pesquisado: ");

                // scanf("\nResultado encontrado foi: %d", &codigo);

                scanf("%d", &codigo);

                for(i = 0; i < totalPassagens; i++) {

                    Passagens p = listaDePassagens[ i ];

                    if (p.codigo == codigo) {

                        printf( "%s\n", p.nome );

                        totalIdentificado++;

                    }

                }

                if ( totalIdentificado == 0 ) {

                    printf("Nenhuma passagem encontrada com o código fornecido.");

                    printf("\n----------------------------------");

                } else {

                    printf("%d passagem(ns) encontrada(s)", totalIdentificado);

                    printf("\n----------------------------------");
                }

}

void buscarPeloDestino() {

    printf("\n\nBUSCAR PELO NOME DO DESTINO\n");

                printf("\n----------------------------------");

                printf("\nDigite o destino a ser pesquisado: ");

                int destino;

                scanf("%s", &busca);

                printf( "\nO(s) destino(s) encontrado(s) foi: ");

                totalIdentificado = 0;

                for(i = 0; i < totalPassagens; i++) {

                    Passagens p = listaDePassagens[ i ];

                    if (strstr(p.nome, busca)) {

                        printf( "%s\n", p.nome );

                        printf("\n----------------------------------");

                        totalIdentificado++;

                    }
                }

                if(totalIdentificado == 0){

                    printf("\n-----------------------------");

                    printf("\n*NENHUM DESTINO ENCONTRADO*");

                    printf("\n-----------------------------");

                }
}

void listarVendas() {

    printf("\n\nVendas:");

    if (totalVendas > 0) {

        for(c = 0; c < totalVendas; c++) {

            Venda t = vendasRealizadas[c];
            float subtotal;
            subtotal = t.quantidadeVendida*t.passagem.precoVenda;


            printf("\n%s: %s - %d unidade(s) por R$ %.2f cada - subtotal R$ %.2f", t.data, t.passagem.nome, t.quantidadeVendida,t.passagem.precoVenda, subtotal);

        }

    } else {

        printf("\nNenhuma venda encontrada.");

    }


}

void registrarVenda() {

    Venda v;


    setbuf(stdin, NULL);

    printf("\n\nRegistrar venda:");

    printf("\n\nCódigo da venda: ");
    scanf("%d", &v.codigo);

    setbuf(stdin, NULL);

    printf("Data da venda no formato dd/mm/aaaa: ");
    scanf("%10[^\n]", v.data);

    setbuf(stdin, NULL);

    int codPass = buscarPassagem();

    if (codPass >= 0) {

        v.passagem = listaDePassagens[ codPass ];

        printf("Quantidade vendida do produto %s: ", v.passagem.nome);

        scanf("%d", &v.quantidadeVendida);

        setbuf(stdin, NULL);

        vendasRealizadas[ totalVendas++ ] = v;
         float subtotal2;
            subtotal2 = v.quantidadeVendida*v.passagem.precoVenda;

        // confirmacao

        printf("\n\nVenda registrada com sucesso! Detalhes abaixo:\n");

        printf("Data: %s\nPassagem: %s\nValor por passagem: %.2f\nValor final: %.2f\nQuantidade vendida: %d\n", v.data, v.passagem.nome, v.passagem.precoVenda,subtotal2, v.quantidadeVendida);

    } else {

        printf("\n\nPassagem inválida! Registro de venda cancelado!!!");

    }

}

void apagarVenda(){

    printf("LISTAGEM DE TODAS AS VENDAS REALIZADAS\n");

    listarVendas();

    int posicaoApagar;

    do{

        printf("Qual o índice que deseja apagar? ");
        scanf("%d", &posicaoApagar);

        if(posicaoApagar < 0 || posicaoApagar >= totalVendas){

            printf("Posicao da venda invalida!\n");
        }

    }while(posicaoApagar < 0 || posicaoApagar >= totalVendas);

    for(int c = posicaoApagar; c < totalVendas; c++){

        vendasRealizadas[ c ] = vendasRealizadas[ c+1 ];
    }

    totalVendas--;

    printf("Venda cancelada com sucesso!\n\n");

}

void carregarDados() {

    /*Passagens p1;
    p1.codigo = 10;
    p1.quantidade = 100;
    strcpy(p1.nome, "Rio de Janeiro");
    p1.precoVenda = 700,00;
    listaDePassagens[ 0 ] = p1;
    totalPassagens++;

    Passagens p2;
    p2.codigo = 20;
    p2.quantidade = 100;
    strcpy(p2.nome, "Fortaleza");
    p2.precoVenda = 1350,00;
    listaDePassagens[ 1 ] = p2;
    totalPassagens++;

   Passagens p3;
    p3.codigo = 30;
    p3.quantidade = 100;
    strcpy(p3.nome, "Gramado");
    p3.precoVenda = 1700,00;
    listaDePassagens[ 2 ] = p3;
    totalPassagens++;

    Passagens p4;
    p4.codigo = 40;
    p4.quantidade = 200;
    strcpy(p4.nome, "Estados Unidos");
    p4.precoVenda = 6500,00;
    listaDePassagens[ 3 ] = p4;
    totalPassagens++;

    Passagens p5;
    p5.codigo = 50;
    p5.quantidade = 100;
    strcpy(p5.nome, "Paris");
    p5.precoVenda = 8000,00;
    listaDePassagens[ 4 ] = p5;
    totalPassagens++;

    Passagens p6;
    p6.codigo = 60;
    p6.quantidade = 100;
    strcpy(p6.nome, "Grecia");
    p6.precoVenda = 14,000,00;
    listaDePassagens[ 5 ] = p6;
    totalPassagens++;

    Passagens p7;
    p7.codigo = 70;
    p7.quantidade = 100;
    strcpy(p7.nome, "Mexico");
    p7.precoVenda = 6500,00;
    listaDePassagens[ 6 ] = p7;
    totalPassagens++;

    Passagens p8;
    p8.codigo = 80;
    p8.quantidade = 100;
    strcpy(p8.nome, "Maldivas");
    p8.precoVenda = 5900,00;
    listaDePassagens[ 7 ] = p8;
    totalPassagens++;*/

    carregarPassagens();

    carregarVendas();
}

int salvarPassagens() {

    // open the file for writing
    FILE *fp = fopen("passagens.txt", "w");
    if (fp == NULL)
    {
        printf("Error opening the file passagens.txt");
        return -1;
    }

    fprintf(fp, "%d\n", totalPassagens);

    // para cada produto da listaProdutos
    for(i = 0; i < totalPassagens; i++) {

        printf("Salvando o codigo: %d\n", listaDePassagens[ i ].codigo);

        fprintf(fp, "%d\n", listaDePassagens[ i ].codigo);

        printf("Salvando o nome: %s\n", listaDePassagens[ i ].nome);

        fprintf(fp, "%s\n", listaDePassagens[ i ].nome);

        printf("Salvando a qtde: %d\n", listaDePassagens[ i ].quantidade);

        fprintf(fp, "%d\n", listaDePassagens[ i ].quantidade);

        printf("Salvando o precoVenda: %f\n", listaDePassagens[ i ].precoVenda);

        fprintf(fp, "%f\n", listaDePassagens[ i ].precoVenda);

    }

    // close the file
    fclose(fp);

    return 0;

}

int carregarPassagens() {

    // abre pra leitura
    FILE *arqPassagens = fopen("passagens.txt", "r");

    // se nao abriu
    if (arqPassagens == NULL) {

        printf("Falha ao abrir o arquivo de passagens para leitura.");

        return 1;

    }

    // se assim que abriu ja atingiu o fim do arquivo, significa q ele esta vazio
    if (feof(arqPassagens)) {

        fclose(arqPassagens);

        return 1;

    }

    // le o total de produtos cadastrados que esta na 1a linha
    fscanf(arqPassagens, "%d", &totalPassagens);

    // se o total for 0, sai daqui =)
    if ( totalPassagens == 0 ) {

        fclose(arqPassagens);

        printf("Nenhuma passagem para carregar em memória.\n");

        return 1;

    }

    // mostra o total
    printf("\nCarregando passagens...");

    // string auxiliar
    char aux[2];

    for( i = 0; i < totalPassagens; i++) {

        fscanf(arqPassagens, "%d", &listaDePassagens[ i ].codigo);
        fgets(aux, 2, arqPassagens);

        printf("\n%d\n", listaDePassagens[i].codigo);

        fscanf(arqPassagens, "%29[^\n]", listaDePassagens[ i ].nome);
        // fgets(aux, 2, arqPassagens);

        printf("%s\n", listaDePassagens[i].nome);

        fscanf(arqPassagens, "%d", &listaDePassagens[ i ].quantidade);
        fgets(aux, 2, arqPassagens);

        printf("%d\n", listaDePassagens[i].quantidade);

        fscanf(arqPassagens, "%f", &listaDePassagens[ i ].precoVenda);
        fgets(aux, 2, arqPassagens);

        printf("%f\n", listaDePassagens[i].precoVenda);

    }

    if ( totalPassagens > 0 ) {
        printf("\n%d passagem(ns) carregada(s)\n", totalPassagens);
    } else {
        printf("\nNão havia passagem salvas.\n");
    }

    fclose(arqPassagens);

    return 1;

}

int carregarVendas() {

}

int main(){

    // carregarDados();

    //configurando a linguagem
    setlocale(LC_ALL, "Portuguese");

    //declaração do vetor de Produtos

    do{
        printf("Selecione uma OPCAO:\n 1-Gerente\n 2-Sair\n");
        scanf("%d", &nivelAcesso);

        switch(nivelAcesso){

            // ger
            case 1:

                do{

                //apresentação do menu
                    printf("\n\n MENU AÉREO \n");
                    printf("1  - Cadastrar passagem\n");
                    printf("2  - Editar passagem\n");
                    printf("3  - Listar passagem cadastrada\n");
                    printf("4  - Buscar passagem por destino\n");
                    printf("5  - Buscar passagem por código\n");
                    printf("6  - Registrar venda de passagem\n");
                    printf("7  - Cancelar venda de passagem\n");
                    printf("8  - Listar todas as vendas realizadas\n");
                    printf("9  - Entrada de Estoque\n");
                    printf("10 - Sair\n");

                    printf("\nEscolha a opção desejada: ");
                    scanf("%d", &opcao);

                    switch(opcao){

                        case 1: cadastroPassagens();break;

                        case 2: editarPassagem();break;

                        case 3: listarPassagens();break;

                        case 4: buscarPeloDestino();break;

                        case 5: buscarPeloCodigo();break;

                        case 6: registrarVenda();break;

                        case 7: apagarVenda(); break;

                        case 8: listarVendas();break;

                        case 9: entradaEstoque();break;

                        case 10:

                            printf("Saindo\n");

                            break;

                        default:

                            printf("Opção inválida. Escolha uma opção do menu\n");

                            break;

                    }

                }while(opcao != 10);
                break;

        }
    }while(nivelAcesso > 0 && nivelAcesso < 2);

    salvarPassagens();

    return 0;
}
