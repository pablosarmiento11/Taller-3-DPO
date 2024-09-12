package uniandes.dpoo.aerolinea.modelo.tarifas;

import java.util.Random;

import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {

    private static final int COSTO_POR_KM_NATURAL = 600;
    private static final int COSTO_POR_KM_CORPORATIVA = 900;
    private Random random = new Random();

    
    public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        Ruta ruta = vuelo.getRuta();
        int distancia = calcularDistanciaVuelo(ruta); 

        int costoPorKm = cliente.getTipoCliente().equals("Corporativo") ? 
                         COSTO_POR_KM_CORPORATIVA : 
                         COSTO_POR_KM_NATURAL;

        int costoBase = costoPorKm * distancia;
        double porcentajeDescuento = calcularPorcentajeDescuento(cliente);
        
        return (int) (costoBase * (1 - porcentajeDescuento));
    }

    
    public double calcularPorcentajeDescuento(Cliente cliente) {
        return random.nextDouble();
    }
}
