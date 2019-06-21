# MARVEL TV - DEMO PRUEBA RAPPI

Se realizo dicha aplicación para demostrar conocimientos en Android de mi persona en solicitud de la empresa RAPPI para poder formar parte de su gran equipo de trabajo. El lenguaje de Programación que se utilizo fue Kotlin. 


# Files

A continuación se describen las clases y archivos involucrados en el funcionamiento de la APP  

## Class BackendVolley 

    class BackendVolley : Application()

Esta clase hereda de la clase Application() es la encargada de monitorear el comportamiento de las peticiones HTTP que se realizan a lo largo de la aplicación, para realizar dichas peticiones se utilizo la librería Volley de Google.

## Capa de Red

Para la capa de red como ya se menciono antes se hizo uso de la librería Volley de Google siendo conformada esta capa por por un Controlador APIController siendo el encargado de invocar al servicio al API por medio de la función :

    private val service: ServiceInterface = serviceInjection 
    override fun get(path: String, completionHandler: (response: JSONObject?) -> Unit) {
	    service.get(path, completionHandler)
    } 
  
  Se realizo de esta manera para aprovechar el polimorfismo de la programación orientada a objetos a través de una Interfaz. 
  La Clase ServiceVolley es la encarga de realizar las peticiones HTTP, para efecto de este demo solo se utilizo un método HTTP "GET", las funciones declaradas en esta clases se recomiendan manejar un tipo de dato CompletionHandler para manejar el objeto llamado "Response" por defecto en la libreria Volley para manejar el objeto respuesta del API.
  


 

## Patron de Diseño

Se utilizo el Patrón de diseño MVC, donde constan de dos modelos clases tipo Data de Kotlin: 

    data class Movie( val id:Int,  
     val title:String,  
     val original_language:String,  
     val vote_average:Double,  
     val media_type:String,  
     val release_date:String,  
     val overview:String,  
     val poster_path:String,  
     val backdrop_path:String,  
     val popularity:Float,  
     val otra_cosa:Int,  
     var trailers:ArrayList<Trailer>  
                    ) : Serializable

    data class Trailer(val id : String,  
     val key: String,  
     val name: String,  
     val site: String,  
     val size: Int,  
     val type: String  
                ) : Serializable
Ambos modelos implementan la Interfaz Serializable para poder ser guardas en algún objeto tipo Intent de Android como un Extra.

También consta de 5 vistas con sus respectivos controladores y un layout para el item que se mostrar en los RecyclerView para manejar las listas recibidas por parte del API.
Estas Vistas - Controladores son:

 - MainActivity - activity_main
 - PopularActivity - activity_popular
 - TopRatedActivity - activity_top_rated
 - UpcomingActivity - activity_upcoming
 - MovieActivity - activity_movie

# Principio de Responsabilidad Única

El principio de responsabilidad única dentro de la programación orientada a objetos define que cada clase debe tener una única responsabilidad, es decir un solo motivo de su existencia. Su propósito es optimizar la programación orientada a objetos.

# Buen Código Limpio

Un buen código limpio debe cumplir en principio con de responsabilidad única, debe tener una buena sintaxis y no un código espaguetis que dificultes a otros desarrolladores o programadores entender la sintaxis del código. También debe hacer buena praxis del lenguaje de programación utilizado evitando asi código innecesario con funciones que ya existan dentro del lenguaje base o simplificando el código con métodos, herencia, o polimorfismos en lo mayo posible a su vez haciendo buen uso del encapsulamiento de las clases.


