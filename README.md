# Sistema de Vendas de Passagens Aéreas

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)

*Aplicação desktop para simulação de compra e reserva de passagens aéreas, desenvolvida como trabalho acadêmico para a disciplina de **Linguagens de Programação 2**.*

</div>

---

## Sumário

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
- [Estrutura de Arquivos](#-estrutura-de-arquivos)
- [Como Executar](#-como-executar)
- [Fluxo de Uso](#-fluxo-de-uso)
- [Conceitos Aplicados](#-conceitos-aplicados)
- [Escalabilidade e Melhorias Futuras](#-escalabilidade-e-melhorias-futuras)
- [Licença](#-licença)

---

## Sobre o Projeto

O **Sistema de Vendas de Passagens Aéreas** é um protótipo funcional que simula um portal de e-commerce voltado para viagens aéreas. O usuário pode se cadastrar, fazer login, buscar voos por origem e destino, escolher assentos, efetuar pagamentos e gerenciar suas reservas — tudo dentro de uma aplicação desktop com interface gráfica construída com **Java Swing**.

O projeto foi desenvolvido com ênfase na aplicação de **Programação Orientada a Objetos (POO)**, boas práticas de **design de software** e princípios de **Interação Humano-Computador (IHC)**.

---

## Funcionalidades

| # | Funcionalidade | Requisito | Status |
|---|---|---|---|
| 1 | Cadastro de novo usuário | RF03 | Sim |
| 2 | Login com validação de dados | RF04 | Sim |
| 3 | Busca de voos por origem/destino | RF01 | Sim |
| 4 | Validação de busca (mesma cidade) | RF02 | Sim |
| 5 | Exibição de resultados de voos | RF05 | Sim |
| 6 | Seleção de assentos interativa | RF06 | Sim |
| 7 | Fluxo de pagamento | RF07 | Sim |
| 8 | Reserva de voo ("Pendente") | RF09 | Sim |
| 9 | Listagem e gerenciamento de reservas | RF10 | Sim |
| 10 | Visualização de detalhes da reserva | RF08 | Sim |
| 11 | Edição de dados (nome, documento) | RF08 | Sim |
| 12 | Remoção de reserva | RF10 | Sim |
| 13 | Geração de comprovante (simulada) | RF08 | Sim |
| 14 | Check-in online | — | Sim |

---

## Arquitetura e Tecnologias

### Tecnologias Utilizadas

- **Linguagem:** Java (JDK 11+)
- **Interface Gráfica:** Java Swing
- **Build:** Compilação via `javac` / IDE (IntelliJ IDEA, Eclipse, NetBeans)

### Diagrama de Arquitetura

```
┌──────────────────────────────────────────────────────────┐
│                      CAMADA DE VIEWS                     │
│                                                          │
│  TelaInicial ──► TelaResultadosBusca ──► TelaSelecaoAs.  │
│       │                                        │         │
│  tela_login                             TelaPagamento    │
│  tela_cadastro                          TelaCheckin      │
│  tela_reserva ──► tela_detalhes_reserva                  │
└───────────────────────────┬──────────────────────────────┘
                            │ usa
┌───────────────────────────▼──────────────────────────────┐
│                    CAMADA DE DADOS                        │
│                                                          │
│   SessaoUsuario (estado do usuário logado)               │
│   DadosReservas (lista centralizada de reservas)         │
│   Reserva       (modelo de entidade — POJO)              │
└──────────────────────────────────────────────────────────┘
```

---

## Estrutura de Arquivos

```
Vendas-de-Passagens-Aereas/
│
├── src/
│   ├── Main.java                  # Ponto de entrada da aplicação
│   │
│   ├── # ── Modelo e Dados ──
│   ├── Reserva.java               # Entidade de negócio (POJO)
│   ├── DadosReservas.java         # Repositório estático de reservas
│   ├── SessaoUsuario.java         # Gerenciador de sessão do usuário
│   │
│   ├── # ── Telas (Views) ──
│   ├── TelaInicial.java           # Tela de busca de voos
│   ├── TelaResultadosBusca.java   # Lista de voos disponíveis
│   ├── TelaSelecaoAssentos.java   # Mapa interativo de assentos
│   ├── TelaPagamento.java         # Formulário de pagamento
│   ├── TelaCheckin.java           # Tela de check-in online
│   ├── tela_login.java            # Autenticação de usuário
│   ├── tela_cadastro.java         # Criação de nova conta
│   ├── tela_reserva.java          # Listagem de reservas do usuário
│   └── tela_detalhes_reserva.java # Detalhes e edição de reserva
│
├── README.md
└── LICENSE
```

---

## Como Executar

### Pré-requisitos

- **Java JDK 11** ou superior instalado
- Variável `JAVA_HOME` configurada

### Passo a passo

**1. Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/Vendas-de-Passagens-Aereas.git
cd Vendas-de-Passagens-Aereas
```

**2. Compile o projeto:**
```bash
javac -d out src/*.java
```

**3. Execute a aplicação:**
```bash
java -cp out Main
```

> **Usando uma IDE?** Basta importar a pasta `src/` como projeto Java, definir `Main.java` como classe de execução e clicar em **Run**.

---

## Fluxo de Uso

```
  Iniciar App
      │
      ▼
 [Tela de Login] ──► Não tem conta? ──► [Tela de Cadastro]
      │                                         │
      │◄────────────────────────────────────────┘
      ▼
 [Tela Inicial] ──► Buscar Voo (Origem + Destino + Data)
      │
      ▼
 [Resultados da Busca]
      │
      ├──► "Selecionar Voo" ──► [Seleção de Assentos] ──► [Pagamento]
      │
      └──► "Reservar Voo" ──► adicionado com status "Pendente"
                                        │
                                        ▼
                              [Minhas Reservas]
                                        │
                                        ▼
                              [Detalhes da Reserva]
                                   │         │
                              [Editar]   [Remover]
                                   │
                              [Gerar Comprovante PDF]
```

---

## Conceitos Aplicados

### Programação Orientada a Objetos

| Conceito | Onde é Aplicado |
|---|---|
| **Herança** | Todas as telas herdam de `JFrame` ou `JPanel` |
| **Encapsulamento** | Classe `Reserva` com getters/setters para proteger seus dados |
| **Polimorfismo** | Componentes Swing reutilizados com comportamentos distintos por tela |

### Design de Software

- **Separação de Responsabilidades (SoC):** As *Views* (telas) não manipulam dados diretamente; delegam para as classes de modelo e estado.
- **Singleton por Convenção Estática:** `SessaoUsuario` e `DadosReservas` atuam como serviços globais e centralizados, evitando passagem manual de estado entre telas.
- **POJO / Modelo de Domínio:** A classe `Reserva` representa a entidade de negócio de forma limpa, sem lógica de interface.

---

## Escalabilidade e Melhorias Futuras

A arquitetura modular facilita a manutenção, mas há limitações inerentes à tecnologia desktop:

| Limitação Atual | Proposta de Evolução |
|---|---|
| App monolítico (Swing) — não escala para múltiplos usuários | Migrar para arquitetura **Cliente-Servidor** com API REST (Spring Boot) |
| Dados em memória (classes estáticas) — perdidos ao fechar | Integrar um banco de dados real (**MySQL** ou **PostgreSQL**) |
| Sem autenticação real | Implementar **JWT / OAuth2** para autenticação segura |
| Geração de PDF simulada | Integrar biblioteca **iText** ou **Apache PDFBox** para comprovantes reais |

### Visão de Arquitetura Futura

```
[Cliente Java Swing]  ◄──HTTP──►  [API REST Spring Boot]  ◄──►  [Banco de Dados]
```

---

## Licença

Este projeto está licenciado sob a licença **MIT**. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---
