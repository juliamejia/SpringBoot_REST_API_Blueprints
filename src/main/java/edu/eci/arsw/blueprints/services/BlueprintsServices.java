/*
 * Este paquete pertenece al proyecto de servicios para blueprints.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Esta clase representa los servicios relacionados con los blueprints.
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    @Qualifier("Memory")
    BlueprintsPersistence bpp = null;

    /**
     * Agrega un nuevo blueprint.
     *
     * @param bp El blueprint a agregar.
     * @throws BlueprintPersistenceException Si ocurre un error al guardar el blueprint.
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    /**
     * Obtiene todos los blueprints disponibles.
     *
     * @return Un conjunto de todos los blueprints.
     * @throws BlueprintNotFoundException Si no se encuentran blueprints.
     * @throws BlueprintPersistenceException Si ocurre un error en la persistencia de los blueprints.
     */
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException, BlueprintPersistenceException {
        return bpp.getBluePrints();
    }


    /**
     * Obtiene un blueprint específico por autor y nombre.
     *
     * @param author El autor del blueprint.
     * @param name El nombre del blueprint.
     * @return El blueprint correspondiente al autor y nombre especificados.
     * @throws BlueprintNotFoundException Si no existe el blueprint especificado.
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

    /**
     * Obtiene todos los blueprints de un autor específico.
     *
     * @param author El autor de los blueprints.
     * @return Un conjunto de blueprints del autor especificado.
     * @throws BlueprintNotFoundException Si el autor especificado no existe.
     * @throws BlueprintPersistenceException Si ocurre un error en la persistencia de los blueprints.
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException, BlueprintPersistenceException {
        return bpp.getBlueprintsByAuthor(author);
    }
}
