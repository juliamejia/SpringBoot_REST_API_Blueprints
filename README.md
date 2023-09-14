## Escuela Colombiana de Ingeniería

## Arquitecturas de Software
### Integrantes: Cristian Rodriguez y Julia Mejia 

# Componentes y conectores - Parte I.

El ejercicio se debe traer terminado para el siguiente laboratorio (Parte II).

#### Middleware- gestión de planos.


## Antes de hacer este ejercicio, realice [el ejercicio introductorio al manejo de Spring y la configuración basada en anotaciones](https://github.com/ARSW-ECI/Spring_LightweightCont_Annotation-DI_Example).

* Ejercicio Realizado en el siguiente repositorio: [Parte1](https://github.com/juliamejia/Spring_LightweightCont_Annotation-DI_Example.git)  

En este ejercicio se va a construír un modelo de clases para la capa lógica de una aplicación que permita gestionar planos arquitectónicos de una prestigiosa compañia de diseño. 

![](img/ClassDiagram1.png)

1. Configure la aplicación para que funcione bajo un esquema de inyección de dependencias, tal como se muestra en el diagrama anterior.


	Lo anterior requiere:

	* Agregar las dependencias de Spring.
	* Agregar la configuración de Spring.
	* Configurar la aplicación -mediante anotaciones- para que el esquema de persistencia sea inyectado al momento de ser creado el bean 'BlueprintServices'.  
		En la clase BlueprintsServices  

		```java
		@Service
		public class BlueprintsServices {
    			@Autowired
    			@Qualifier("Memory")        
   			 BlueprintsPersistence bpp=null;
		```
  
  		En la clase InMemoryBlueprintsPersistence:
   		* @Component marca esta clase como un componente administrado por Spring
     	* @Qualifier especifica que esta clase es la que va usar las funciones de BlueprintsPersistence que es la clase inyectada por BlueprintServices

		```java
		@Component
		@Qualifier("Memory")
		public class InMemoryBlueprintPersistence implements BlueprintsPersistence{
		```

2. Complete los operaciones getBluePrint() y getBlueprintsByAuthor(). Implemente todo lo requerido de las capas inferiores (por ahora, el esquema de persistencia disponible 'InMemoryBlueprintPersistence') agregando las pruebas correspondientes en 'InMemoryPersistenceTest'.  
   2.1 getBluePrint()
   * En InMmoeryBlueprintPersistence
  
     ```java
     @Override
		public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
    			System.out.println(blueprints.size());
    			return blueprints.get(new Tuple<>(author, bprintname));
		}
     ```

   *  En BlueprintsServices
     
	     ```java
	     public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
	    	return bpp.getBlueprint(author,name);
		}
	     ```

   2.2 getBlueprintsByAuthor()
   * En InMmoeryBlueprintPersistence

		```java
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
		```

   * En BlueprintsServices

     ```java
     public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException, BlueprintPersistenceException {
		return bpp.getBlueprintsByAuthor(author);
		}
     ```

   2.3 TEST  
   * En InMemoryPersistenceTest

     ```java
     		@Test
		public void dado_un_autor_cuando_se_buscan_blueprints_entonces_deberia_encontrar_lista_de_blueprints() throws BlueprintPersistenceException, BlueprintNotFoundException{
    			InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
    			Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
    			Blueprint bp0=new Blueprint("Julia", "Pintura1",pts0);
    			ibpp.saveBlueprint(bp0);

			Point[] pts1=new Point[]{new Point(41, 41),new Point(16, 16)};
			Blueprint bp1=new Blueprint("Cristian", "Pintura2",pts1);
			ibpp.saveBlueprint(bp1);
			
			Point[] pts2=new Point[]{new Point(42, 42),new Point(17, 17)};
			Blueprint bp2=new Blueprint("Julia", "Pintura3",pts2);
			ibpp.saveBlueprint(bp2);
			
			assertEquals(2,ibpp.getBlueprintsByAuthor("Julia").size());
			}
     ```
     
		<img width="549" alt="image" src="https://github.com/juliamejia/SpringBoot_REST_API_Blueprints/assets/98657146/c9b44f66-2580-4cd5-af25-cd1a66181ca1">


4. Haga un programa en el que cree (mediante Spring) una instancia de BlueprintServices, y rectifique la funcionalidad del mismo: registrar planos, consultar planos, registrar planos específicos, etc.

5. Se quiere que las operaciones de consulta de planos realicen un proceso de filtrado, antes de retornar los planos consultados. Dichos filtros lo que buscan es reducir el tamaño de los planos, removiendo datos redundantes o simplemente submuestrando, antes de retornarlos. Ajuste la aplicación (agregando las abstracciones e implementaciones que considere) para que a la clase BlueprintServices se le inyecte uno de dos posibles 'filtros' (o eventuales futuros filtros). No se contempla el uso de más de uno a la vez:
	* (A) Filtrado de redundancias: suprime del plano los puntos consecutivos que sean repetidos.
	* (B) Filtrado de submuestreo: suprime 1 de cada 2 puntos del plano, de manera intercalada.

6. Agrege las pruebas correspondientes a cada uno de estos filtros, y pruebe su funcionamiento en el programa de prueba, comprobando que sólo cambiando la posición de las anotaciones -sin cambiar nada más-, el programa retorne los planos filtrados de la manera (A) o de la manera (B). 
