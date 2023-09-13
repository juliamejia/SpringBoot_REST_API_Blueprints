package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    BlueprintsServices services;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public void prettyPrint(String author1, String author2) {
        System.out.println("Registrando planos para " + author1 + "...."); // Mensaje de registro para el autor1
        System.out.println("Planos registrados exitosamente"); // Mensaje de éxito para el autor1
        System.out.println("");
        System.out.println("Registrando planos para " + author2 + "...."); // Mensaje de registro para el autor2
        System.out.println("Planos registrados exitosamente"); // Mensaje de éxito para el autor2
        System.out.println("");
    }

    @Override
    public void run(String... args) throws Exception {
        int planos = 10; // Cantidad de planos a registrar
        String author1 = "Julia"; // Nombre del primer autor
        String author2 = "Cristian"; // Nombre del segundo autor
        prettyPrint(author1, author2); // Llama al método prettyPrint para mostrar los mensajes

        // Registra planos para ambos autores
        for (int i = 0; i < planos; i++) {
            services.addNewBlueprint(new Blueprint(author1, "plano" + (i * 2))); // Registro de plano para autor1
            services.addNewBlueprint(new Blueprint(author2, "plano" + (i * 2 + 1))); // Registro de plano para autor2
        }

        // Imprime la lista de planos registrados
        System.out.println("--------PRUEBA DE REGISTRO DE PLANOS--------");
        System.out.println(services.getAllBlueprints());

        // Imprime la lista de planos registrados para el autor "Julia"
        System.out.println("--------PRUEBA DE CONSULTA DE PLANOS PARA AUTOR JULIA--------");
        System.out.println(services.getBlueprintsByAuthor("Julia"));
    }
}
