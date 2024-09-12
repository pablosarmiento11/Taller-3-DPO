package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {

    private static final String NOMBRE_CLIENTE = "nombre";
    private static final String TIPO_CLIENTE = "tipoCliente";
    private static final String CLIENTE = "cliente";
    private static final String CODIGO_RUTA = "codigoRuta";
    private static final String FECHA = "fecha";
    private static final String VUELOS = "vuelos";
    private static final String CLIENTES = "clientes";

    @Override
    public void cargarAerolinea(String archivo, Aerolinea aerolinea) throws IOException, InformacionInconsistenteException {
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONObject raiz = new JSONObject(jsonCompleto);

        cargarVuelos(aerolinea, raiz.getJSONArray(VUELOS));
        cargarClientes(aerolinea, raiz.getJSONArray(CLIENTES));
    }

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {
        JSONObject jobject = new JSONObject();

        salvarVuelos(aerolinea, jobject);

        salvarClientes(aerolinea, jobject);

        try (PrintWriter pw = new PrintWriter(archivo)) {
            pw.write(jobject.toString(4));
        }
    }

    private void cargarVuelos(Aerolinea aerolinea, JSONArray jVuelos) throws InformacionInconsistenteException {
        for (int i = 0; i < jVuelos.length(); i++) {
            JSONObject vueloJson = jVuelos.getJSONObject(i);

            String codigoRuta = vueloJson.getString(CODIGO_RUTA);
            String fecha = vueloJson.getString(FECHA);
            String nombreAvion = vueloJson.getString("nombreAvion");
            int capacidadAvion = vueloJson.getInt("capacidadAvion"); 

            Ruta ruta = aerolinea.getRuta(codigoRuta);
            if (ruta == null) {
                throw new InformacionInconsistenteException("Ruta no encontrada: " + codigoRuta);
            }

            Avion avion = new Avion(nombreAvion, capacidadAvion);

            Vuelo vuelo = new Vuelo(fecha, avion, ruta, new HashMap<>());

 
        }
    }

    }





    private void salvarVuelos(Aerolinea aerolinea, JSONObject jobject) {
        JSONArray jVuelos = new JSONArray();
        for (Vuelo vuelo : aerolinea.getVuelos()) {
            JSONObject jVuelo = new JSONObject();
            jVuelo.put(CODIGO_RUTA, vuelo.getRuta().getCodigoRuta());
            jVuelo.put(FECHA, vuelo.getFecha());

            jVuelos.put(jVuelo);
        }
        jobject.put(VUELOS, jVuelos);
    }

    private void cargarClientes(Aerolinea aerolinea, JSONArray jClientes) throws InformacionInconsistenteException {
        for (int i = 0; i < jClientes.length(); i++) {
            JSONObject clienteJson = jClientes.getJSONObject(i);
            String tipoCliente = clienteJson.getString(TIPO_CLIENTE);
            Cliente cliente;

            if (ClienteNatural.NATURAL.equals(tipoCliente)) {
                String nombre = clienteJson.getString(NOMBRE_CLIENTE);
                cliente = new ClienteNatural(nombre);
            } else {
                cliente = ClienteCorporativo.cargarDesdeJSON(clienteJson);
            }

            if (!aerolinea.existeCliente(cliente.getIdentificador())) {
                aerolinea.agregarCliente(cliente);
            } else {
                throw new InformacionInconsistenteException("Cliente repetido: " + cliente);
            }
        }
    }

    private void salvarClientes(Aerolinea aerolinea, JSONObject jobject) {
        JSONArray jClientes = new JSONArray();
        for (Cliente cliente : aerolinea.getClientes()) {
            JSONObject jCliente;
            if (cliente instanceof ClienteNatural) {
                jCliente = new JSONObject();
                jCliente.put(NOMBRE_CLIENTE, cliente.getIdentificador());
                jCliente.put(TIPO_CLIENTE, ClienteNatural.NATURAL);
            } else {
                jCliente = ((ClienteCorporativo) cliente).salvarEnJSON();
            }
            jClientes.put(jCliente);
        }
        jobject.put(CLIENTES, jClientes);
    }
}
