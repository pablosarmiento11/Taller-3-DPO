package uniandes.dpoo.aerolinea.tiquetes;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class Tiquete {

		private String codigo;
		private int tarifa;
		private boolean usado = false;
		private Vuelo vuelo; 
		private Cliente cliente;
		
		public Tiquete(String codigo, int tarifa, Vuelo vuelo, Cliente clienteComprador) {  
			this.codigo= codigo;
			this.tarifa= tarifa;
			this.vuelo=vuelo;
			this.cliente= clienteComprador; 
		}
		
		
		public void marcarComoUsado() {
			this.usado= true;					
		}
		
		public Cliente getCliente() {
			return this.cliente;			
		}
		
		public String getCodigo() {
			return this.codigo;
		}
		
		public Vuelo getVuelo() {
			return this.vuelo;
		}
		
		public int getTarifa() {
			return this.tarifa;
		}
		
		public boolean esUsado() {
			return this.usado;
		}
		
}
