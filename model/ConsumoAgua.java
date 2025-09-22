package model;

public class ConsumoAgua {
    private String nomeUso;         
    private double consumoLitros;    
    private boolean usaReuso;         
    private String categoria;        
    private double eficiencia;        

    public ConsumoAgua(String nomeUso, double consumoLitros, boolean usaReuso, String categoria) {
        this.nomeUso = nomeUso;
        this.consumoLitros = consumoLitros;
        this.usaReuso = usaReuso;
        this.categoria = categoria;
        this.eficiencia = 0;
    }

    public ConsumoAgua(String nomeUso, double consumoLitros, boolean usaReuso, String categoria, double eficiencia) {
        this.nomeUso = nomeUso;
        this.consumoLitros = consumoLitros;
        this.usaReuso = usaReuso;
        this.categoria = categoria;
        this.eficiencia = eficiencia;
    }

    public String getNomeUso() {
        return nomeUso;
    }

    public void setNomeUso(String nomeUso) {
        this.nomeUso = nomeUso;
    }

    public double getConsumoLitros() {
        return consumoLitros;
    }

    public void setConsumoLitros(double consumoLitros) {
        this.consumoLitros = consumoLitros;
    }

    public boolean isUsaReuso() {
        return usaReuso;
    }

    public void setUsaReuso(boolean usaReuso) {
        this.usaReuso = usaReuso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getEficiencia() {
        return eficiencia;
    }

    public double getConsumoBase() {
        // Calcula o consumo base considerando se usa água de reuso
        double fator = usaReuso ? 0.1 : 1.0;
        return consumoLitros * fator;
    }

    public double getConsumoOtimizado() {
        
        return getConsumoBase() * (1 - eficiencia / 100);
    }

    public void aplicarOtimizacao(double percentual) {
        if (percentual >= 0 && percentual <= 100) {
            this.eficiencia = percentual;
        }
    }

    @Override
    public String toString() {
        return "ConsumoAgua{" +
                "nomeUso='" + nomeUso + '\'' +
                ", consumoLitros=" + consumoLitros +
                ", usaReuso=" + usaReuso +
                ", categoria='" + categoria + '\'' +
                ", eficiencia=" + eficiencia +
                '}';
    }
}
