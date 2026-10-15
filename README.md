# SistemaEventos

Sistema de cadastro, consulta e acompanhamento de eventos na cidade de Porto Alegre, desenvolvido em Java para execução via console.

## Sobre o projeto

O SistemaEventos tem como objetivo facilitar o cadastro e a consulta de eventos realizados em Porto Alegre, permitindo organizar informações sobre atividades culturais, esportivas, musicais, educacionais e de outras categorias.

A aplicação está sendo desenvolvida como um sistema de console, com interação por meio de menus textuais. A proposta é oferecer uma estrutura simples e organizada para o gerenciamento de usuários e eventos, além de permitir que os participantes acompanhem as atividades de seu interesse.

## Objetivos

- Implementar o cadastro de usuários e eventos.
- Organizar os eventos por categorias predefinidas.
- Permitir a consulta das informações de cada evento.
- Identificar eventos futuros, em andamento e encerrados.
- Ordenar os eventos de acordo com a proximidade de seus horários.
- Permitir que usuários confirmem e cancelem sua participação.
- Armazenar os eventos em arquivo de texto para preservar os dados entre execuções.

## Funcionalidades

### Cadastro de usuários

O sistema possui uma estrutura de usuário com informações que permitem identificar e organizar os interesses de cada participante.

Os atributos previstos incluem:

- Identificador;
- Nome;
- E-mail;
- Telefone;
- Bairro;
- Categorias de interesse.

### Cadastro de eventos

Cada evento possui informações para identificação, localização e acompanhamento de sua realização.

Os dados incluem:

- Nome do evento;
- Endereço;
- Cidade;
- Categoria;
- Data e horário de início;
- Descrição;
- Duração prevista.

A proposta é manter os cadastros organizados e permitir que os usuários consultem os detalhes antes de decidir participar.

### Categorias de eventos

As categorias são delimitadas por um conjunto de opções predefinidas, evitando classificações inconsistentes.

As categorias previstas são:

- Cultura;
- Esportes;
- Música;
- Educação;
- Gastronomia;
- Tecnologia;
- Lazer;
- Outros.

### Consulta e acompanhamento dos eventos

A aplicação permite consultar os eventos cadastrados e visualizar suas informações.

Também estão previstas funcionalidades para:

- Ordenar os eventos pelos horários mais próximos;
- Identificar eventos que estão ocorrendo no momento;
- Consultar eventos que já foram encerrados;
- Confirmar participação em um evento;
- Visualizar os eventos com presença confirmada;
- Cancelar uma participação anteriormente confirmada.

### Notificações

O sistema utiliza informações de data e horário para verificar se um evento está em andamento.

A lógica considera o horário de início e a duração prevista para determinar a situação do evento. Essa funcionalidade serve como base para a identificação e a exibição de eventos que estejam ocorrendo no momento.

### Persistência de dados

Está prevista a utilização do arquivo `events.data` para armazenar os eventos cadastrados.

Na versão completa, o sistema deverá:

- Gravar os eventos no arquivo;
- Ler os dados salvos ao iniciar a aplicação;
- Preservar os cadastros entre diferentes execuções.

## Tecnologias utilizadas

- **Java:** linguagem utilizada no desenvolvimento da aplicação.
- **IntelliJ IDEA:** ambiente de desenvolvimento.
- **LocalDateTime:** representação e manipulação de datas e horários.
- **Enum:** definição das categorias permitidas para os eventos.
- **Arquivo de texto:** armazenamento persistente dos eventos, por meio de `events.data`.

## Estrutura do projeto

A estrutura inicial utiliza um arquivo Java principal para organizar a aplicação e suas classes relacionadas.

```text
SistemaEventos/
├── src/
│   └── SistemaEventos.java
├── events.data
└── README.md
```

O arquivo `events.data` será utilizado para persistência dos eventos. Sua localização poderá variar conforme a configuração do projeto.

## Como executar

### Pré-requisitos

- JDK instalado e configurado;
- IntelliJ IDEA ou outro ambiente compatível com Java.

### Execução pelo IntelliJ IDEA

1. Abra o projeto no IntelliJ IDEA.
2. Verifique se o JDK está configurado nas definições do projeto.
3. Abra o arquivo `SistemaEventos.java`.
4. Execute a classe que contém o método `main`.
5. Utilize as opções exibidas no console para interagir com o sistema.

Caso o projeto esteja configurado para execução pelo terminal, também é possível compilar e executar o programa a partir da pasta que contém o arquivo Java:

```bash
javac SistemaEventos.java
java SistemaEventos
```

## Organização do código

A estrutura do sistema utiliza classes para representar os principais elementos da aplicação.

- **Usuario:** representa os participantes e seus dados cadastrais.
- **Evento:** representa as atividades cadastradas, seus horários, endereços e demais informações.
- **Categoria:** define as categorias permitidas por meio de um `enum`.
- **Estrutura de notificações:** prevista para organizar o comportamento de notificação e permitir o uso de abstração e polimorfismo.

A evolução do projeto deverá contemplar uma classe abstrata e métodos sobrescrevíveis, atendendo ao requisito de orientação a objetos da atividade.

## Próximas etapas

O desenvolvimento prevê a conclusão e a validação dos seguintes pontos:

- Implementar a confirmação de participação nos eventos;
- Criar a listagem de participações confirmadas;
- Permitir o cancelamento de participações;
- Ordenar os eventos por proximidade temporal;
- Separar eventos futuros, em andamento e encerrados;
- Restringir os cadastros à cidade de Porto Alegre;
- Implementar a leitura e a gravação dos eventos em `events.data`;
- Incorporar e demonstrar a classe abstrata e os métodos necessários ao requisito de polimorfismo;
- Realizar testes das funcionalidades e da persistência dos dados.

## Contexto acadêmico

Projeto desenvolvido em Java como atividade acadêmica, com foco em programação orientada a objetos, manipulação de datas e horários, organização de dados e persistência em arquivos.

## Status do projeto

Em desenvolvimento. As funcionalidades descritas como previstas serão incorporadas e testadas ao longo da implementação.

