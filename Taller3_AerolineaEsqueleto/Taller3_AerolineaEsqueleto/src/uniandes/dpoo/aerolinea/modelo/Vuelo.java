package uniandes.dpoo.aerolinea.modelo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo {

	private String fecha;
	private Avion avion;
	private Ruta ruta;
	private Map<String, Tiquete> tiquetes;
	
	public Vuelo(String fecha, Avion avion, Ruta ruta, Map<String, Tiquete> tiquetes) {
		this.fecha=fecha;
		this.avion=avion;
		this.ruta=ruta;
	}
	
	public Ruta getRuta() {
		return this.ruta;
	}
	
	public String getFecha() {
		return this.fecha;
	}
	
	public Avion getAvion() {
		return this.avion;
	}
	
	public Map<String, Tiquete> getTiquetes() {
		return this.tiquetes;
	}
	
    public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException {
        
        Avion avion= this.getAvion();
        List<String> codigosTiquetesAEliminar = new ArrayList<>();

        
        if (cantidad > avion.getCapacidad()) {
            throw new VueloSobrevendidoException(this); 
        }
        
        int costoPorTiquete = calculadora.calcularTarifa(this, cliente);
        int costoTotal = costoPorTiquete * cantidad;

        for (int i = 0; i < cantidad; i++) {
        	
            Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, cantidad);
            tiquetes.put(tiquete.getCodigo(), tiquete); 
            cliente.agregarTiquete(tiquete); 
            
        }
        
        for (String codigo : codigosTiquetesAEliminar) {
            tiquetes.remove(codigo);
        }
        

        return costoTotal;
    }
	
	
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vuelo vuelo = (Vuelo) obj;
        return Objects.equals(fecha, vuelo.fecha) &&
               Objects.equals(avion, vuelo.avion) &&
               Objects.equals(ruta, vuelo.ruta) &&
               Objects.equals(tiquetes, vuelo.tiquetes);
    }
	
}


	
	


