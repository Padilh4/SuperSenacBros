# SuperSenacBros

Um jogo de plataforma 2D desenvolvido em **Java** utilizando a biblioteca **LibGDX**. 

Este projeto foi criado com foco em mecânicas clássicas de plataforma, navegação de menus fluida e suporte completo tanto para Teclado quanto para Controles (Gamepads).

## Funcionalidades

* **Física e Movimentação:** Pulos com gravidade e colisões.
* **Suporte a Controles (Gamepad):** Jogue perfeitamente usando controles de Xbox, PlayStation ou genéricos (suporte a Analógico e D-Pad).
* **Menus Interativos:** Tela de registro estilo Arcade com navegação controlada e efeitos sonoros.
* **Mapas em Tiled:** Fases construídas utilizando o Tiled Map Editor (`.tmx` / `.tsx`).
* **Game Over e Reset:** Sistema de vidas, pontuação e reinício rápido.

## Tecnologias Utilizadas

* **Linguagem:** Java (JDK 11+)
* **IDE:** [Eclipse IDE](https://eclipseide.org/)
* **Engine:** [LibGDX](https://libgdx.com/)
* **Gerenciador de Dependências:** Gradle
* **Design de Fases:** Tiled Map Editor

## Controles do Jogo

O jogo detecta automaticamente se você está usando o teclado ou um controle!

| Ação | Teclado | Controle (Xbox / PS) |
| :--- | :--- | :--- |
| **Andar** | Setas `Esquerda / Direita` ou `A / D` | Analógico Esquerdo ou D-Pad |
| **Pular** | Seta `Cima`, `W` ou `Espaço` | Botão `A` (Xbox) / `X` (PS) |
| **Confirmar (Menus)** | `Enter` | Botão `A` / Botão `X` |
| **Resetar (Game Over)** | `Enter` | Botão `B` (Xbox) / `O` (PS) |

## Observações finais
Para uso do banco de dados para guardar os scores dos jogadores, apenas instale o arquivo PLAYERS_DB.sql e utilize o código mysql no seu computador local.

## Desenvolvedores
 
**Arthur Cardoso Padilha** - Desenvolvimento e Lógica - [Padilh4](https://github.com/Padilh4)

**Carlos Henrique Cardozo** - Designer do mapa e texturas [Carlos-CardozoS](https://github.com/Carlos-CardozoS)

**Mizael Da Rosa Giehl** - Designer principal - [Mizael-Giehl](https://github.com/Mizael-Giehl)
