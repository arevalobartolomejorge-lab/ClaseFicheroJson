package es.ciudadescolar.util;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.ciudadescolar.modelo.Alumno;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

public class JsonManager {

    private static final Logger LOG = LoggerFactory.getLogger(JsonManager.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Alumno> parsearJsonAlumnos (File jsonFich){
        List<Alumno> alumnos = null;
        Alumno alumno = null;
        
        JsonNode raizNodo = null;
        JsonNode alumnosNodo = null;
        try{
            raizNodo = mapper.readTree(jsonFich);

            if(raizNodo.isObject()){
                LOG.info("El curso es " + raizNodo.get("curso").asText());
                LOG.info("El código es: " + raizNodo.get("codigo").asInt());
                LOG.info("El centro es " + raizNodo.get("centro").asText());

                alumnosNodo = raizNodo.get("alumnos");
                if(alumnosNodo.isArray()){
                    alumnos = new ArrayList <>();
                    for(JsonNode alumnoNodo: alumnosNodo){
                        alumno = new Alumno();
                        alumno.setEdad(alumnoNodo.get("edad").asInt());
                        alumno.setExpediente(String.valueOf(alumnoNodo.get("expediente").asInt()));
                        alumno.setNombre(alumnoNodo.get("nombre").asText());
                        alumnos.add(alumno);
                        LOG.info("Nuevo alumno" + alumno.toString());
                    }
                    
                }
            }
        }catch(IOException e){
            LOG.error("Error durante el parseo del Json [" + jsonFich.getName() + "]" + e.getMessage());
        }
        
        return alumnos;

    }

    public static void crearJsonAlumnosMay20(List<Alumno> alumnos, File fichero){
        ArrayNode arrayAlumnos = mapper.createArrayNode();
        ObjectNode nodoAlumno= null;
        for(Alumno al : alumnos){
            if(al.getEdad() > 20){
                nodoAlumno = mapper.createObjectNode();
                nodoAlumno.put("exp", al.getExpediente());
                nodoAlumno.put("edad", al.getEdad());
                nodoAlumno.put("nom", al.getNombre());
                arrayAlumnos.add(nodoAlumno);
                LOG.info("Alumo añadido al Json" + al.toString());
            }
        }
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, arrayAlumnos);
        }catch(IOException e){
            LOG.error("Error durante la creación del fichero" + e.getMessage());
        }
    }

    public static void crearJsonAlumnos(List<Alumno> alumnos, File fichero){
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, alumnos);
        }catch(IOException e){
            LOG.error("Error durante la creación del fichero" + e.getMessage());
        }
    }

}
