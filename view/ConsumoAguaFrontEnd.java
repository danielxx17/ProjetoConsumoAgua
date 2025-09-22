package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.ConsumoAgua;

public class ConsumoAguaFrontEnd extends Application {

    private TextField nomeField;
    private TextField litrosField;
    private CheckBox reusoCheckBox;
    private TextField categoriaField;
    private TextField eficienciaField;
    private TextArea resultadoArea;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) {
        controller = new Controller();

        primaryStage.setTitle("Calculadora de Consumo de Água");

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        Label titulo = new Label("Sistema de Cálculo de Consumo de Água");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane formulario = criarFormulario();
        HBox botoes = criarBotoes();

        resultadoArea = new TextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setPrefRowCount(10);
        resultadoArea.setStyle("-fx-font-family: monospace;");

        mainLayout.getChildren().addAll(titulo, formulario, botoes, resultadoArea);

        Scene scene = new Scene(mainLayout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(new Label("Nome do Uso/Equipamento:"), 0, 0);
        nomeField = new TextField();
        grid.add(nomeField, 1, 0);

        grid.add(new Label("Consumo de Água (litros):"), 0, 1);
        litrosField = new TextField();
        grid.add(litrosField, 1, 1);

        grid.add(new Label("Usa Água de Reuso:"), 0, 2);
        reusoCheckBox = new CheckBox();
        grid.add(reusoCheckBox, 1, 2);

        grid.add(new Label("Categoria (Residencial/Industrial):"), 0, 3);
        categoriaField = new TextField();
        grid.add(categoriaField, 1, 3);

        grid.add(new Label("Eficiência (%):"), 0, 4);
        eficienciaField = new TextField();
        grid.add(eficienciaField, 1, 4);

        return grid;
    }

    private HBox criarBotoes() {
        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);

        Button calcularBtn = new Button("Calcular Consumo");
        calcularBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        calcularBtn.setOnAction(e -> calcularConsumo());

        Button limparBtn = new Button("Limpar");
        limparBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        limparBtn.setOnAction(e -> limparCampos());

        botoes.getChildren().addAll(calcularBtn, limparBtn);
        return botoes;
    }

    private void calcularConsumo() {
        try {
            String nome = nomeField.getText();
            double litros = Double.parseDouble(litrosField.getText());
            boolean reuso = reusoCheckBox.isSelected();
            String categoria = categoriaField.getText();
            double eficiencia = Double.parseDouble(eficienciaField.getText());

            ConsumoAgua consumo = new ConsumoAgua(nome, litros, reuso, categoria);
            consumo.aplicarOtimizacao(eficiencia);

            controller.setConsumoAgua(consumo);

            String resultado = controller.gerarRelatorio();
            resultadoArea.setText(resultado);

        } catch (NumberFormatException ex) {
            resultadoArea.setText("Erro: Por favor, insira valores numéricos válidos para litros e eficiência.");
        } catch (Exception ex) {
            resultadoArea.setText("Erro: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        nomeField.clear();
        litrosField.clear();
        reusoCheckBox.setSelected(false);
        categoriaField.clear();
        eficienciaField.clear();
        resultadoArea.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
