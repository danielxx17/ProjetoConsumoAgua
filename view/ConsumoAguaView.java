package view;

import model.ConsumoAgua;         
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsumoAguaView {
    private Scanner scanner;

    public ConsumoAguaView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarInicio() {
        System.out.println("=== Sistema de Cálculo de Consumo de Água ===");
    }

    public String lerNomeUso() {
        System.out.print("Digite o nome do uso ou equipamento (ex: Chuveiro, Máquina de Lavar): ");
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao ler o nome do uso.");
            return "";
        }
    }

    public String lerCategoria() {
        System.out.print("Digite a categoria do uso (Residencial/Industrial): ");
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao ler a categoria.");
            return "";
        }
    }

    public double lerConsumoLitros() {
        System.out.print("Digite o consumo de água (litros): ");
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Valor inválido! Digite um número.");
            scanner.nextLine(); 
            return 0;
        }
    }

    public boolean lerReuso() {
        System.out.print("O uso utiliza água de reuso? (true/false): ");
        try {
            return scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Digite true ou false.");
            scanner.nextLine(); 
            return false;
        }
    }

    public int mostrarMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Ver dados do consumo");
        System.out.println("2. Aplicar otimização");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Opção inválida! Digite um número inteiro.");
            scanner.nextLine();
            return -1;
        }
    }

    public void mostrarConsumo(ConsumoAgua consumoAgua) {
        try {
            System.out.println("\n--- Dados do Consumo ---");
            System.out.println("Uso/Equipamento: " + consumoAgua.getNomeUso());
            System.out.println("Categoria: " + consumoAgua.getCategoria());
            System.out.println("Consumo de Água: " + consumoAgua.getConsumoLitros() + " litros");
            System.out.println("Usa água de reuso: " + (consumoAgua.isUsaReuso() ? "Sim" : "Não"));
            System.out.println("Consumo base: " + consumoAgua.getConsumoBase() + " litros");
            System.out.println("Eficiência aplicada: " + consumoAgua.getEficiencia() + "%");
            System.out.println("Consumo otimizado: " + consumoAgua.getConsumoOtimizado() + " litros");
        } catch (Exception e) {
            System.out.println("Erro ao mostrar os dados do consumo.");
        }
    }

    public double lerPercentualOtimizacao() {
        System.out.print("Digite o percentual de otimização (0 a 100): ");
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Valor inválido! Digite um número entre 0 e 100.");
            scanner.nextLine(); 
            return 0;
        }
    }

    public void mostrarMensagem(String mensagem) {
        try {
            System.out.println(mensagem);
        } catch (Exception e) {
            System.out.println("Erro ao exibir a mensagem.");
        }
    }
}
