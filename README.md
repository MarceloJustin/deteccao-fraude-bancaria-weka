# 🤖 Detecção de Fraude Bancária com Machine Learning

![Java](https://img.shields.io/badge/Java-22-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen?logo=springboot)
![WEKA](https://img.shields.io/badge/WEKA-3.8.6-orange)
![License](https://img.shields.io/badge/Licença-MIT-lightgrey)

## 📌 Sobre o Projeto

Projeto de estudo de **Inteligência Artificial e Machine Learning em Java**, desenvolvido com a biblioteca WEKA. Implementa um classificador que analisa transações bancárias e decide, com base em dados históricos, se uma transação é **fraude ou não** — usando o algoritmo de árvore de decisão **J48 (C4.5)**.

O objetivo é entender na prática o ciclo completo de ML: definição de atributos, montagem do dataset de treino, treinamento do modelo e classificação de novos dados.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão |
|---|---|
| Java | 22 |
| Spring Boot | 4.0.6 |
| WEKA (weka-stable) | 3.8.6 |
| Maven Wrapper | 3.9.15 |

---

## 🏗️ Como o Algoritmo Funciona

O fluxo completo de Machine Learning aplicado neste projeto:

```
1. Definir atributos (colunas do dataset)
        ↓
2. Adicionar exemplos de treino (transações históricas com rótulo)
        ↓
3. Treinar o modelo J48 (árvore de decisão)
        ↓
4. Classificar nova transação (fraude ou não fraude)
```

O algoritmo **J48** aprende padrões nos dados de treino e constrói uma árvore de decisão com regras como:

> *"Se o valor for alto E a origem for internacional → fraude."*

Ao receber uma nova transação, ele percorre a árvore e retorna a classificação.

---

## 📋 Atributos do Modelo

| Atributo | Tipo | Valores Possíveis | Papel |
|---|---|---|---|
| `Valor` | Numérico | Qualquer valor `double` | Entrada |
| `Origem` | Categórico | `nacional`, `internacional` | Entrada |
| `Fraude` | Categórico | `nao`, `sim` | **Rótulo (classe alvo)** |

---

## 📦 Dados de Treinamento

O modelo aprende com 11 exemplos históricos:

| Valor (R$) | Origem | Fraude |
|---|---|---|
| 5.000 | internacional | sim |
| 10.000 | internacional | sim |
| 7.500 | internacional | sim |
| 8.000 | internacional | sim |
| 200 | nacional | nao |
| 150 | nacional | nao |
| 300 | nacional | nao |
| 400 | nacional | nao |
| 1.000 | nacional | sim |
| 1.500 | nacional | sim |
| 20.000 | nacional | sim |

**Padrão aprendido:** valores altos + origem internacional são fortes indicadores de fraude. Os três últimos exemplos (valores médios/altos de origem nacional) foram incluídos para desafiar o modelo e demonstrar variação nos dados.

---

## ▶️ Como Executar

### Pré-requisitos

- **Java 22** instalado
- **Maven não é necessário** — o projeto usa Maven Wrapper (`mvnw`), que baixa a versão correta automaticamente

### Clonar o repositório

```bash
git clone https://github.com/MarceloJustin/meu-projeto-ia.git
cd meu-projeto-ia
```

### Executar via Maven Wrapper

**Linux / macOS:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

### Executar a classe diretamente (pela IDE)

Abra o projeto na sua IDE (Eclipse, IntelliJ) e rode o método `main` da classe:

```
src/main/java/com/ia/ml/weka/DeteccaoDeFraudeBancaria.java
```

### Saída esperada no console

```
Teste 1: Fraude: sim
Teste 2: Fraude: nao
Teste 3: Fraude: sim
Teste 4: Fraude: nao
...
Teste 9: Fraude: sim
Teste 10: Fraude: sim
```

---

## 🧪 Testes

O projeto conta apenas com o teste de contexto padrão gerado pelo Spring Initializr (`contextLoads`). Não há testes de ML implementados ainda — isso está previsto nas melhorias futuras.

```bash
# Linux / macOS
./mvnw test

# Windows
mvnw.cmd test
```

---

## 🔮 Próximos Passos

- [ ] Expor a classificação via API REST (`POST /transacoes/classificar`)
- [ ] Persistir transações e resultados em banco de dados (Spring Data JPA)
- [ ] Ampliar o dataset de treino com mais exemplos e atributos (ex: país, horário, frequência)
- [ ] Avaliar o modelo com métricas reais: precisão, recall, F1-score e matriz de confusão
- [ ] Carregar dataset externo a partir de arquivo `.arff` ou `.csv`

---

## 👨‍💻 Autor

**Marcelo Justin**

[![GitHub](https://img.shields.io/badge/GitHub-MarceloJustin-181717?logo=github)](https://github.com/MarceloJustin)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-marcelojustin-0A66C2?logo=linkedin)](https://www.linkedin.com/in/marcelojustin)

---

## 📄 Licença

Este projeto está licenciado sob a licença [MIT](LICENSE).
