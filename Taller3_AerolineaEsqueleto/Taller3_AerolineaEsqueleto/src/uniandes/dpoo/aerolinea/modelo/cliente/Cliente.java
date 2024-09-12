package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.ArrayList;
import java.util.List;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente {
	
	public List<Tiquete> tiquetesSinUsar = new ArrayList<>();	
	public List<Tiquete> tiquetesUsados = new ArrayList<>();	
    
	
	public Cliente() {}
	
	public abstract String getTipoCliente();				
    
    public abstract String getIdentificador();
    
    public void agregarTiquete(Tiquete tiquete) {
    	tiquetesSinUsar.add(tiquete);
    }
    
    public int calcularValorTotalTiquetes() {
        int total = 0;
        for (Tiquete tiquete : tiquetesSinUsar) {
            if (!tiquete.esUsado()) {
                total += tiquete.getTarifa();
            }
        }
        return total;
    }
    
    public  void usarTiquetes(Vuelo vuelo) {
    	List<Tiquete> tiquetesAUsar = new ArrayList<>();
        for (Tiquete tiquete : tiquetesSinUsar) {
            if (tiquete.getVuelo().equals(vuelo)) {
                tiquete.esUsado();
                tiquetesAUsar.add(tiquete);
            }
        }
        tiquetesSinUsar.removeAll(tiquetesAUsar);
        tiquetesUsados.addAll(tiquetesAUsar);
    }
}
