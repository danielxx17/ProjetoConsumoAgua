package controller;

import model.ConsumoAgua;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
    private ConsumoAgua consumoAgua;
    private Scanner scanner; 

    public Controller() {
        scanner = new Scanner(System.in);
    }

    public void cadastrarConsumo() {
        
        String nomeUso;
        while (true) {
            System.out.print("Digite o nome do uso ou equipamento (ex: Chuveiro, Máquina de Lavar): ");
            nomeUso = scanner.nextLine().trim();
            if (!nomeUso.isEmpty()) {
                break;
            } else {
                System.out.println("Erro: Nome não pode estar vazio. Tente novamente.");
            }
        }

        
        double consumoLitros;
        while (true) {
            try {
                System.out.print("Digite o consumo de água (litros): ");
                consumoLitros = scanner.nextDouble();
                if (consumoLitros >= 0) {
                    break;
                } else {
                    System.out.println("Erro: O consumo deve ser um valor positivo. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número válido para o consumo. Tente novamente.");
                scanner.nextLine(); 
            }
        }

        
        boolean usaReuso;
        while (true) {
            try {
                System.out.print("Usa água de reuso? (true/false): ");
                usaReuso = scanner.nextBoolean();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite 'true' ou 'false'. Tente novamente.");
                scanner.nextLine(); 
            }
        }

        scanner.nextLine(); 

        
        String categoria;
        while (true) {
            System.out.print("Digite a categoria de uso (ex: Residencial, Industrial): ");
            categoria = scanner.nextLine().trim();
            if (!categoria.isEmpty()) {
                break;
            } else {
                System.out.println("Erro: Categoria não pode estar vazia. Tente novamente.");
            }
        }

        
        double eficiencia;
        while (true) {
            try {
                System.out.print("Digite a eficiência de otimização (%): ");
                eficiencia = scanner.nextDouble();
                if (eficiencia >= 0 && eficiencia <= 100) {
                    break;
                } else {
                    System.out.println("Erro: A eficiência deve estar entre 0 e 100%. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número válido para a eficiência. Tente novamente.");
                scanner.nextLine(); // limpa entrada inválida
            }
        }

        
        consumoAgua = new ConsumoAgua(nomeUso, consumoLitros, usaReuso, categoria);
        consumoAgua.aplicarOtimizacao(eficiencia);

        System.out.println("\nConsumo cadastrado com sucesso!");
    }

    public void exibirConsumo() {
        if (consumoAgua != null) {
            System.out.println("=== CÁLCULO DE CONSUMO DE ÁGUA ===");
            System.out.println();
            System.out.println("DADOS DO USO");
            System.out.println("   Nome: " + consumoAgua.getNomeUso());
            System.out.println("   Categoria: " + consumoAgua.getCategoria());
            System.out.println("   Consumo de Água: " + String.format("%.2f", consumoAgua.getConsumoLitros()) + " litros");
            System.out.println("   Água de Reuso: " + (consumoAgua.isUsaReuso() ? "Sim" : "Não"));
            System.out.println("   Eficiência Aplicada: " + String.format("%.1f", consumoAgua.getEficiencia()) + "%");
            System.out.println();
            System.out.println("ANÁLISE DE CONSUMO");
            System.out.println("   Consumo Base: " + String.format("%.2f", consumoAgua.getConsumoBase()) + " litros");
            System.out.println("   Consumo Otimizado: " + String.format("%.2f", consumoAgua.getConsumoOtimizado()) + " litros");
            System.out.println("   Redução: " + String.format("%.2f", consumoAgua.getEficiencia()) + "%");
            System.out.println();

            
            String analiseConsumo;
            if (consumoAgua.getConsumoOtimizado() < consumoAgua.getConsumoBase() * 0.5) {
                analiseConsumo = "Excelente otimização! Grande economia de água.";
            } else if (consumoAgua.getConsumoOtimizado() < consumoAgua.getConsumoBase() * 0.8) {
                analiseConsumo = "Boa otimização. Há espaço para melhorias.";
            } else {
                analiseConsumo = "Otimização limitada. Considere melhorar a eficiência.";
            }
            System.out.println("   " + analiseConsumo);

            String tipoReuso = consumoAgua.isUsaReuso() ?
                    "Uso consciente - aproveitamento de água de reuso" : "Consumo convencional - maior impacto hídrico";
            System.out.println("   Tipo de Uso: " + tipoReuso);

            String statusOtimizacao;
            if (consumoAgua.getEficiencia() > 80) {
                statusOtimizacao = "Altamente otimizado";
            } else if (consumoAgua.getEficiencia() > 50) {
                statusOtimizacao = "Moderadamente otimizado";
            } else {
                statusOtimizacao = "Precisa de mais otimização";
            }
            System.out.println("   Status da Otimização: " + statusOtimizacao);

        } else {
            System.out.println("Nenhum consumo cadastrado ainda.");
        }
    }

    
    public ConsumoAgua getConsumoAgua() {
        return this.consumoAgua;
    }

    public void setConsumoAgua(ConsumoAgua consumo) {
        this.consumoAgua = consumo;
    }

    public String gerarRelatorio() {
        if (consumoAgua == null) {
            return "Nenhum consumo calculado ainda.";
        }

        double consumoBase = consumoAgua.getConsumoBase();
        double consumoOtimizado = consumoAgua.getConsumoOtimizado();
        double reducao = consumoAgua.getEficiencia();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== CÁLCULO DE CONSUMO DE ÁGUA ===\n\n");

        relatorio.append("DADOS DO USO\n");
        relatorio.append(String.format("   Nome: %s\n", consumoAgua.getNomeUso()));
        relatorio.append(String.format("   Categoria: %s\n", consumoAgua.getCategoria()));
        relatorio.append(String.format("   Consumo de Água: %.2f litros\n", consumoAgua.getConsumoLitros()));
        relatorio.append(String.format("   Água de Reuso: %s\n", consumoAgua.isUsaReuso() ? "Sim" : "Não"));
        relatorio.append(String.format("   Eficiência Aplicada: %.1f%%\n\n", consumoAgua.getEficiencia()));

        relatorio.append("ANÁLISE DE CONSUMO\n");
        relatorio.append(String.format("   Consumo Base: %.2f litros\n", consumoBase));
        relatorio.append(String.format("   Consumo Otimizado: %.2f litros\n", consumoOtimizado));
        relatorio.append(String.format("   Redução: %.2f%%\n\n", reducao));

        String analiseConsumo;
        if (consumoOtimizado < consumoBase * 0.5) {
            analiseConsumo = "Excelente otimização! Grande economia de água.";
        } else if (consumoOtimizado < consumoBase * 0.8) {
            analiseConsumo = "Boa otimização. Há espaço para melhorias.";
        } else {
            analiseConsumo = "Otimização limitada. Considere melhorar a eficiência.";
        }
        relatorio.append(String.format("   %s\n", analiseConsumo));

        String tipoReuso = consumoAgua.isUsaReuso() ?
                "Uso consciente - aproveitamento de água de reuso" : "Consumo convencional - maior impacto hídrico";
        relatorio.append(String.format("   Tipo de Uso: %s\n", tipoReuso));

        String statusOtimizacao;
        if (consumoAgua.getEficiencia() > 80) {
            statusOtimizacao = "Altamente otimizado";
        } else if (consumoAgua.getEficiencia() > 50) {
            statusOtimizacao = "Moderadamente otimizado";
        } else {
            statusOtimizacao = "Precisa de mais otimização";
        }
        relatorio.append(String.format("   Status da Otimização: %s\n", statusOtimizacao));

        return relatorio.toString();
    }
}
