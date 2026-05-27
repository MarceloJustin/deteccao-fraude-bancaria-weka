package com.ia.ml.weka;

import weka.classifiers.Classifier; // Interface que define metodos obrigatorios para algoritmos de classificacao
import weka.classifiers.trees.J48; // Algoritmo decisao que aprende a responder: "e' fraude? sim ou nao"

import weka.core.Attribute; // Representa uma coluna dos dados (ex: "valor", "origem")
import weka.core.Instance; // Representa uma linha de dados (como uma linha do Excel)
import weka.core.DenseInstance; // Representa uma linha completa de dados (com valores reais)
import weka.core.Instances; // O conjunto completo de dados (como uma planilha)

//Ferramentas de log usadas para ocultar avisos do Weka (nao afetam o funcionamento do codigo)
import java.util.logging.Level; // Controla o nivel de importancia dos avisos exibidos
import java.util.logging.Logger; // Usado para configurar o sistema de logs do Java

import java.util.ArrayList;

public class DeteccaoDeFraudeBancaria {

	private Classifier classificador;
	private Instances dadosTreinamento;

	// Atributos do conjunto de dados
	private Attribute atributoValor;
	private Attribute atributoOrigem;
	private Attribute atributoFraude;

	// Definição dos atributos (colunas da "planilha")
	public void definirAtributos() {
		 atributoValor = new Attribute("Valor"); // valor da transacao
		 
		 ArrayList<String> valoresOrigem = new ArrayList<>();
		 valoresOrigem.add("internacional");
		 valoresOrigem.add("nacional");
		 atributoOrigem = new Attribute("origem", valoresOrigem); // origem da transação
		 
		 ArrayList<String> valoresFraude = new ArrayList<>();
		 valoresFraude.add("nao");
		 valoresFraude.add("sim");
		 atributoFraude = new Attribute("fraude", valoresFraude); // rótulo: se é fraude ou não
		 
		 ArrayList<Attribute> atributos = new ArrayList<>();
		 atributos.add(atributoValor);
		 atributos.add(atributoOrigem);
		 atributos.add(atributoFraude);
		 
		 // Cria o dataset chamado "transacoes" com os atributos definidos. Começa vazio (0 linhas).
		 dadosTreinamento = new Instances("transacoes", atributos, 0);
		 
		 // Define o ultimo atributo ("fraude") como a classe alvo a previsão.
		 dadosTreinamento.setClassIndex(dadosTreinamento.numAttributes() - 1);
	}
		 
		 // Método auxiliar para criar e adicionar uma nova transação ao dataset de treino
	private void adicionarTransacao(double valor, String origem, String fraude) {
		Instance instancia = new DenseInstance(dadosTreinamento.numAttributes());
		//setDataset(..) é uma configuração obirgatória que diz a instancia:
		// "Você vai seguir a mesma estrutura do dataset - os mesmos atributos,
		// na mesma ordem e com os mesmos tipos de dados."
		instancia.setDataset(dadosTreinamento);

		instancia.setValue(atributoValor, valor);
		instancia.setValue(atributoOrigem, origem);
		instancia.setValue(atributoFraude, fraude);
		dadosTreinamento.add(instancia);
	}

	// Adição de exemplos: dados para o modelo aprender
	public void adicionarExemplos() {
		adicionarTransacao(5000, "internacional", "sim");
		adicionarTransacao(10000, "internacional", "sim");
		adicionarTransacao(7500, "internacional", "sim");
		adicionarTransacao(8000, "internacional", "sim");

		// Exemplo de transacoes NORMAIS (valores baixos + origem nacional)
		adicionarTransacao(200, "nacional", "nao");
		adicionarTransacao(150, "nacional", "nao");
		adicionarTransacao(300, "nacional", "nao");
		adicionarTransacao(400, "nacional", "nao");

		// Exemplos adiconais:
		// Trasaçoes como valores medios/altos em território nacional
		// Pode ser usados para demonstrar variação ou desafiar o modelo:
		adicionarTransacao(1000, "nacional", "sim");
		adicionarTransacao(1500, "nacional", "sim");
		adicionarTransacao(20000, "nacional", "sim");
	}

	// Treinamento do modelo
	public void treinarModelo() throws Exception {
		classificador = new J48(); // (MODELO MATEMÁTICO)
		classificador.buildClassifier(dadosTreinamento); // Treina o modelo com os dados fornecidos
		//Imprime a arvore de decisão
		//System.out.println(classificador.toString());
	}

	// Classificação de novas transacões
	public String classificarTrasacao(double valor, String origem) throws Exception {
		// Cria uma nova transacao para prever se é fraude ou não
		Instance novaInstancia = new DenseInstance(dadosTreinamento.numAttributes());
		novaInstancia.setDataset(dadosTreinamento);
		novaInstancia.setValue(atributoValor, valor);
		novaInstancia.setValue(atributoOrigem, origem);

		// Pedir para o classificador prever se a nova transacao e fraude ou não
		// O resultado sera 0.0 (não é fraude) ou 1.0 (é fraude), conforme o treinamento
		double previsao = classificador.classifyInstance(novaInstancia);
		/*
		 * Obs: classifyInstance retorna número decimal porque usa o mesmo metodo para
		 * prever classes e numeros.
		 */

		// Transforma o numero previsto em texto (ex: 0 "nao", 1 "sim") e monta a
		// resposta final
		return "Fraude: " + dadosTreinamento.classAttribute().value((int) previsao);
	}
	
	// Teste completo do processo de ML
	public static void main(String [] args) {
		// Oculta avisos sobre bibliotecas nativas (não afeta o funcionamento)
		Logger.getLogger("com.github.fommil.netlib").setLevel(Level.SEVERE);
		Logger.getLogger("com.github.fommil.jni").setLevel(Level.SEVERE);

		// Criacao do detector
		DeteccaoDeFraudeBancaria detector = new DeteccaoDeFraudeBancaria();
		try {
			detector.definirAtributos();
			detector.adicionarExemplos();
			detector.treinarModelo();
			String resultado1 = detector.classificarTrasacao(5000, "internacional");
			String resultado2 = detector.classificarTrasacao(200, "nacional");
			String resultado3 = detector.classificarTrasacao(10000, "internacional");
			String resultado4 = detector.classificarTrasacao(150, "nacional");
			String resultado5 = detector.classificarTrasacao(7500, "internacional");
			String resultado6 = detector.classificarTrasacao(300, "nacional");
			String resultado7 = detector.classificarTrasacao(8000, "internacional");
			String resultado8 = detector.classificarTrasacao(400, "nacional");
			
			String resultado9 = detector.classificarTrasacao(401, "nacional");
			String resultado10 = detector.classificarTrasacao(399, "internacional");
			
			System.out.println("Teste 1: " + resultado1);
			System.out.println("Teste 2: " + resultado2);
			System.out.println("Teste 3: " + resultado3);
			System.out.println("Teste 4: " + resultado4);
			System.out.println("Teste 5: " + resultado5);
			System.out.println("Teste 6: " + resultado6);
			System.out.println("Teste 7: " + resultado7);
			System.out.println("Teste 8: " + resultado8);
			
			System.out.println("Teste 9: " + resultado9);
			System.out.println("Teste 10: " + resultado10);
			
		} catch(Exception e) {
			System.err.println("Erro ao classificar a transação: " + e.getMessage());
		}
	}
}