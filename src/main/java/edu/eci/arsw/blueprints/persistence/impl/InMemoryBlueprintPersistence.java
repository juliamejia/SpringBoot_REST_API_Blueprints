/*
 * Este es un componente de persistencia de Blueprints en memoria que implementa la interfaz BlueprintsPersistence.
 * Utiliza un mapa para almacenar los Blueprints en memoria.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementación de la interfaz BlueprintsPersistence que almacena Blueprints en memoria.
 */
@Component
@Qualifier("Memory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    // Constructor que carga datos de prueba al inicializar la instancia
    public InMemoryBlueprintPersistence() {
        Point[] pts = new Point[]{new Point(140, 140), new Point(115, 115)};
        Blueprint bp = new Blueprint("_authorname_", "_bpname_ ", pts);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
    }

    // Método para guardar un Blueprint en memoria
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("El Blueprint proporcionado ya existe: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    // Método para obtener un Blueprint por autor y nombre
    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    // Método para obtener todos los Blueprints almacenados en memoria
    @Override
    public Set<Blueprint> getBluePrints() throws BlueprintPersistenceException, BlueprintNotFoundException {
        Set<Blueprint> prints = new HashSet<>();
        for (Tuple<String, String> tuple : this.blueprints.keySet()) {
            prints.add(blueprints.get(tuple));
        }
        return prints;
    }

    // Método para obtener Blueprints por autor
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> prints = new HashSet<>();
        for (Tuple<String, String> tuple : this.blueprints.keySet()) {
            if (tuple.o1.equals(author)) {
                prints.add(blueprints.get(tuple));
            }
        }
        return prints;
    }
}
