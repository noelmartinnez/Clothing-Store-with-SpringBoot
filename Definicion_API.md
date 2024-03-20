# Especificación de la API: Tienda de Ropa

Esta API sirve como método para enviar productos concretos a las diferentes Tiendas de ropa y Gimnasios.

La URL base de la API es: /tiendaropa/

Las operaciones disponibles para las otras aplicaciones son:
* Mostrar catálogo de productos disponible. Con posibilidad de realizar busquedas y de realizar filtrados por categoría.
* Mostrar catálogo de productos en oferta.
* Mostrar detalles de un producto concreto.

## GET Obtener página del catálogo de productos:

* Breve descripción: Obtiene la página del catálogo de productos con la lista de productos disponibles.
* Ruta: GET /tiendaropa/catalogo
* Método: GET
* Funcionalidad: Devuelve la página del catálogo de productos para que la tienda de ropa pueda enseñarla también en su aplicación y así aumentar el catálogo.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos.
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página del catálogo de productos no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo
    * Respuesta: HTML de la página del catálogo de productos.
 
## GET Obtener página del catálogo de productos con cierto filtro concreto:

* Breve descripción: Obtiene la página del catálogo de productos aplicando un filtro específico.
* Ruta: GET /tiendaropa/catalogo/filtro
* Método: GET
* Funcionalidad: Devuelve la página del catálogo de productos con productos que cumplen un filtro específico. Útil para la tienda de ropa y el gimnasio (ropa de deporte/fitness).
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos con productos filtrados.
* Parámetros:
    * Query Params:
        * filtro: string (Ej. "Deporte")
* Gestión de errores:
    * 404: Si la página del catálogo con filtro no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo/filtro?filtro=Deporte
    * Respuesta: HTML de la página del catálogo de productos con productos de deporte.

## GET Obtener página del catálogo de productos con cierta búsqueda:

* Breve descripción: Obtiene la página del catálogo de productos con resultados específicos de búsqueda.
* Ruta: GET /tiendaropa/catalogo/busqueda
* Método: GET
* Funcionalidad: Retorna la página del catálogo de productos con productos que coinciden con la búsqueda.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos con resultados de búsqueda.
* Parámetros:
    * Query Params:
        * q: string (Ej. "Camiseta")
* Gestión de errores:
    * 404: Si la página del catálogo con búsqueda no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo/busqueda?q=Camiseta
    * Respuesta: HTML de la página del catálogo de productos con resultados de búsqueda para "Camiseta".

## GET Obtener página con los detalles del producto concreto:

* Breve descripción: Obtiene la página con los detalles de un producto específico.
* Ruta: GET /tiendaropa/productos/{id}
* Método: GET
* Funcionalidad: Devuelve la página con los detalles de un producto específico.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página con los detalles del producto.
* Parámetros:
    * Path Params:
        * id: int (ID del producto)
* Gestión de errores:
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/productos/456
    * Respuesta: HTML de la página con los detalles del producto.
