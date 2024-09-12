package uniandes.dpoo.aerolinea.modelo.tarifas;

import java.util.Random;


import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class CalculadoraTarifaTemporadaAlta extends CalculadoraTarifas{
	protected static final int COSTO_POR_KM= 1000;
    private Random random = new Random();

	
	@Override
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        Ruta ruta = vuelo.getRuta();

        int distancia = calcularDistanciaVuelo(ruta);

        return COSTO_POR_KM * distancia;	}

	@Override
    public double calcularPorcentajeDescuento(Cliente cliente) {
        return random.nextDouble();
    }
	

	
	
	
}
