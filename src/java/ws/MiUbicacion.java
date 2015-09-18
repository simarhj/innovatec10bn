/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;

/**
 *
 * @author ESTACION
 */
@WebService(serviceName = "MiUbicacion")
public class MiUbicacion {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dondeEstoy")
    public String dondeEstoy() {
        String respuesta = "";
        URL paginalocalizacion;
        BufferedReader entrada;
        String codigo = "";

        try {
            paginalocalizacion = new URL("http://es.geoipview.com/");
            entrada = new BufferedReader(new InputStreamReader(paginalocalizacion.openStream()));
            System.out.println("Aca pasa 3");
            String c = entrada.readLine();
            while (c != null) {
                codigo = codigo + c;
                c = entrada.readLine();
            }
            String ciudad = buscarCiudad(codigo);
            String pais = buscarPais(codigo);
            respuesta = "Usted esta ubicado en la ciudad de "+ciudad+" en "+pais;
        } catch (Exception ex) {
            respuesta = "Hubo un error y no lo pude calcular " + ex;
        }
        return respuesta;
    }

    private String buscarCiudad(String codigo) {
        String subcadena = codigo.substring(codigo.indexOf("Dirección de IP:")+17);
        return subcadena.substring(0, subcadena.indexOf("<"));
    }
    
    private String buscarPais(String codigo) {
        String subcadena = codigo.substring(codigo.indexOf("Código de País:")+14);
        return subcadena.substring(0, subcadena.indexOf("<"));
    }
}
