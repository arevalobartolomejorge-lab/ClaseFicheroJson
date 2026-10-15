import org.slf4j.LoggerFactory;

import es.ciudadescolar.util.JsonManager;
import es.ciudadescolar.modelo.Alumno;
import java.io.File;
import java.util.List;

import org.slf4j.Logger;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws Exception {
        File ficheroJson = new File("alumnos.json");
        File ficheroSalida = new File("alumnos2.json");
        File ficheroSalida2 = new File("alumnos3.json");
        LOG.info("Inicio del parseo del fichero" + ficheroJson.getAbsolutePath());
        List<Alumno> alumnosEntrada = JsonManager.parsearJsonAlumnos(ficheroJson);
        LOG.info("fin del parseo del fichero" + ficheroJson.getAbsolutePath());
        if(alumnosEntrada.isEmpty() || alumnosEntrada == null){
            LOG.warn("No se han encontrado alumnos");
        }
        else{
            JsonManager.crearJsonAlumnosMay20(alumnosEntrada, ficheroSalida);
            JsonManager.crearJsonAlumnos(alumnosEntrada, ficheroSalida2);
        }
        
    }
}
