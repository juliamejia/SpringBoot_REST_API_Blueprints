package edu.eci.arsw.blueprints.test.type.impl;

import edu.eci.arsw.blueprints.filter.types.impl.FilterSub;
import edu.eci.arsw.blueprints.filter.types.impl.FilterRedundancy;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.junit.Test;
import org.junit.Assert;

import java.util.*;

public class FilterServiceTest {


    @Test
    public void dadoUnBlueprintCuandoSeFiltraPorRedundanciaEntoncesDeberiaEliminarPuntosRedundantes() throws BlueprintNotFoundException, BlueprintPersistenceException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        FilterRedundancy fr = new FilterRedundancy();
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15,15),new Point(40,40), new Point(40,40),new Point(15, 15),new Point(15,15)};
        Point[] pts1 = new Point[]{new Point(15,14)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        Blueprint bp1 = new Blueprint("prueba","2",pts1);
        Blueprint bp2 = new Blueprint("prueba","3",new Point[]{});
        ibpp.saveBlueprint(bp0);
        fr.filterBlueprint(bp0);
        Assert.assertEquals(2,bp0.getPoints().size());
        ibpp.saveBlueprint(bp1);
        fr.filterBlueprint(bp1);
        Assert.assertEquals(1,bp1.getPoints().size());
        ibpp.saveBlueprint(bp2);
        fr.filterBlueprint(bp2);
        Assert.assertEquals(0,bp2.getPoints().size());
    }

    @Test
    public void dadoUnConjuntoDeBlueprintsCuandoSeFiltraPorRedundanciaEntoncesDeberianEliminarseLosPuntosRedundantes(){
        FilterRedundancy fr = new FilterRedundancy();
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15,15),new Point(40,40), new Point(40,40),new Point(15, 15),new Point(15,15)};
        Point[] pts1 = new Point[]{new Point(15,14)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        Blueprint bp1 = new Blueprint("prueba","2",pts1);
        Blueprint bp2 = new Blueprint("prueba","3",new Point[]{});
        Set<Blueprint> sbp = new HashSet<Blueprint>();
        sbp.add(bp0);sbp.add(bp1);sbp.add(bp2);
        try {
            fr.filterBlueprints(sbp);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Object[] sbpA = sbp.toArray();
        Assert.assertEquals(2,((Blueprint)sbpA[0]).getPoints().size());
        Assert.assertEquals(1,((Blueprint)sbpA[1]).getPoints().size());
        Assert.assertEquals(0,((Blueprint)sbpA[2]).getPoints().size());

    }

    @Test
    public void dadoUnAutorCuandoSeFiltraPorRedundanciaEntoncesDeberianEliminarPuntosRedundantes(){
        FilterRedundancy fr = new FilterRedundancy();
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15,15),new Point(40,40), new Point(40,40),new Point(15, 15),new Point(15,15)};
        Point[] pts1 = new Point[]{new Point(15,14)};
        Blueprint bp0=new Blueprint("Cristian", "obra",pts0);
        Blueprint bp1 = new Blueprint("prueba","2",pts1);
        Blueprint bp2 = new Blueprint("prueba","3",new Point[]{});
        Set<Blueprint> sbp = new HashSet<Blueprint>();
        sbp.add(bp0);sbp.add(bp1);sbp.add(bp2);
        try {
            fr.filterPrintsByAuthor("prueba",sbp);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Object[] sbpA = sbp.toArray();
        Assert.assertEquals(6,((Blueprint)sbpA[0]).getPoints().size());
        Assert.assertEquals(1,((Blueprint)sbpA[1]).getPoints().size());
        Assert.assertEquals(0,((Blueprint)sbpA[2]).getPoints().size());
    }

    @Test
    public void dadoUnBlueprintCuandoSeAplicaFiltroDeSubmuestreoEntoncesDeberiaFiltrarCorrectamente(){
        FilterSub fs = new FilterSub();
        Point[] pts0 = new Point[]{new Point(10,10),new Point(14,12),new Point(19,20),new Point(34,25),new Point(1,4),
                new Point(14,18),new Point(1,4),new Point(9,80),new Point(8,7), new Point(14,25)};
        Point[] pts1 = new Point[]{new Point(1,2),new Point(2,6)};
        Point[] pts2 = new Point[]{};
        Blueprint bp0=new Blueprint("cristian", "obra",pts0);
        Blueprint bp1 = new Blueprint("prueba","2",pts1);
        Blueprint bp2 = new Blueprint("prueba","3",new Point[]{});

        try {
            fs.filterBlueprint(bp0);
            fs.filterBlueprint(bp1);
            fs.filterBlueprint(bp2);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(5,bp0.getPoints().size());
        Point[] pA0 = new Point[]{new Point(14,12),new Point(34,25),new Point(14,18),new Point(9,80),
                new Point(14,25)};
        Point[] pA1 = new Point[]{new Point(2,6)};
        Assert.assertEquals(Arrays.asList(pA0).toString(),bp0.getPoints().toString());
        Assert.assertEquals(1,bp1.getPoints().size());
        Assert.assertEquals(Arrays.asList(pA1).toString(),bp1.getPoints().toString());
        Assert.assertEquals(0,bp2.getPoints().size());
        Assert.assertEquals(Arrays.asList(new Point[]{}).toString(),bp2.getPoints().toString());

    }

    @Test
    public void dadoUnConjuntoDeBlueprintsCuandoSeAplicaFiltroDeSubmuestreoEntoncesDeberianFiltrarseCorrectamente(){
        FilterSub fs = new FilterSub();
        Point[] pts0 = new Point[]{new Point(10,10),new Point(14,12),new Point(19,20),new Point(34,25),new Point(1,4),
                new Point(14,18),new Point(1,4),new Point(9,80),new Point(8,7), new Point(14,25)};
        Point[] pts1 = new Point[]{new Point(1,2),new Point(2,6)};
        Point[] pts2 = new Point[]{};
        Blueprint bp0=new Blueprint("cristian", "obra",pts0);
        Blueprint bp1 = new Blueprint("prueba","2",pts1);
        Blueprint bp2 = new Blueprint("prueba","3",new Point[]{});
        Set<Blueprint> sbp = new HashSet<Blueprint>();
        sbp.add(bp0);sbp.add(bp1);sbp.add(bp2);
        try {
            fs.filterBlueprints(sbp);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Object[] sbpA = sbp.toArray();
        Assert.assertEquals(5,((Blueprint)sbpA[0]).getPoints().size());
        Point[] pA0 = new Point[]{new Point(14,12),new Point(34,25),new Point(14,18),new Point(9,80),
                new Point(14,25)};
        Point[] pA1 = new Point[]{new Point(2,6)};
        Assert.assertEquals(Arrays.asList(pA0).toString(),((Blueprint)sbpA[0]).getPoints().toString());
        Assert.assertEquals(1,((Blueprint)sbpA[1]).getPoints().size());
        Assert.assertEquals(Arrays.asList(pA1).toString(),((Blueprint)sbpA[1]).getPoints().toString());
        Assert.assertEquals(0,((Blueprint)sbpA[2]).getPoints().size());
        Assert.assertEquals(Arrays.asList(new Point[]{}).toString(),((Blueprint)sbpA[2]).getPoints().toString());

    }

    @Test
    public void dadoUnAutorCuandoSeFiltraSubmuestreoEntoncesDeberiaObtenerPlanosFiltrados(){
        FilterSub fs = new FilterSub();
        Point[] pts0 = new Point[]{new Point(10,10),new Point(14,12),new Point(19,20),new Point(34,25),new Point(1,4),
                new Point(14,18),new Point(1,4),new Point(9,80),new Point(8,7), new Point(14,25)};
        Point[] pts1 = new Point[]{new Point(1,2),new Point(2,6)};
        Point[] pts2 = new Point[]{};
        Blueprint bp0=new Blueprint("cristian", "obra",pts0);
        Blueprint bp1 = new Blueprint("prueba","2",pts1);
        Blueprint bp2 = new Blueprint("prueba","3",new Point[]{});
        Set<Blueprint> sbp = new HashSet<Blueprint>();
        sbp.add(bp0);sbp.add(bp1);sbp.add(bp2);
        try {
            fs.filterPrintsByAuthor("prueba",sbp);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        Object[] sbpA = sbp.toArray();
        Assert.assertEquals(10,((Blueprint)sbpA[0]).getPoints().size());
        Point[] pA1 = new Point[]{new Point(2,6)};
        Assert.assertEquals(Arrays.asList(pts0).toString(),((Blueprint)sbpA[0]).getPoints().toString());
        Assert.assertEquals(1,((Blueprint)sbpA[1]).getPoints().size());
        Assert.assertEquals(Arrays.asList(pA1).toString(),((Blueprint)sbpA[1]).getPoints().toString());
        Assert.assertEquals(0,((Blueprint)sbpA[2]).getPoints().size());
        Assert.assertEquals(Arrays.asList(new Point[]{}).toString(),((Blueprint)sbpA[2]).getPoints().toString());


    }
}