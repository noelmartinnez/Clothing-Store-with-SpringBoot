# Especificación de la API: Tienda de Ropa

## GET Obtener página de registro:

* Breve descripción: Obtiene la página de registro para que los usuarios puedan crear una cuenta en la tienda.
* Ruta: GET /tiendaropa/registro
* Método: GET
* Funcionalidad: Retorna la página de registro para que los usuarios puedan completar el formulario y crear una cuenta.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página de registro.
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página de registro no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/registro
    * Respuesta: HTML de la página de registro.
 
## POST Registrar usuario en la base de datos:

* Breve descripción: Registra un nuevo usuario en la base de datos.
* Ruta: POST /tiendaropa/registro
* Método: POST
* Funcionalidad: Registra un nuevo usuario con la información proporcionada en el formulario de registro.
* Estructura de la petición:
    * Body:
````json
{
  "nombre": string,
  "apellidos": string,
  "email": string,
  "telefono": string,
  "contrasena": string
}
````
* Estructura de la respuesta:  
201: Se crea correctamente.
````json
{
  "id": int
}
````  
400: Formato de datos incorrecto.
```json
{
  "errors": {
    string,
    ...
  }
}
```
* Parámetros: No aplica.
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
```json
POST localhost:8080/tiendaropa/registro

Body:
{
  "nombre": "John",
  "apellidos": "Doe",
  "email": "john.doe@example.com",
  "telefono": "123456789",
  "contrasena": "securepassword"
}
```  
Respuesta
```json
201 Created

{
  "id": 123
}
```

## GET Obtener página de login:

* Breve descripción: Obtiene la página de inicio de sesión para que los usuarios ingresen a sus cuentas.
* Ruta: GET /tiendaropa/login
* Método: GET
* Funcionalidad: Retorna la página de inicio de sesión para que los usuarios puedan ingresar.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página de inicio de sesión.
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página de inicio de sesión no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/login
    * Respuesta: HTML de la página de inicio de sesión.
 
## POST Comprobar en la base de datos que el usuario existe:

* Breve descripción: Verifica en la base de datos si las credenciales de inicio de sesión son válidas.
* Ruta: POST /tiendaropa/login
* Método: POST
* Funcionalidad: Comprueba las credenciales de inicio de sesión en la base de datos.
* Estructura de la petición:
    * Body:
````json
{
  "email": string,
  "contrasena": string
}
````
* Estructura de la respuesta:
    * 200: Credenciales válidas.
    * 401: Credenciales inválidas.
````json
{
  "error": "Credenciales inválidas"
}
````
* Parámetros: No aplica.
* Gestión de errores:
    * 401: Si las credenciales son inválidas.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
POST localhost:8080/tiendaropa/login

Body:
{
  "email": "john.doe@example.com",
  "contrasena": "securepassword"
}
````  
Respuesta:  
* 200 OK (Credenciales válidas)  
* 401 Unauthorized (Credenciales inválidas)

## GET Obtener página del catálogo de productos:

* Breve descripción: Obtiene la página del catálogo de productos con la lista de productos disponibles.
* Ruta: GET /tiendaropa/catalogo
* Método: GET
* Funcionalidad: Devuelve la página del catálogo de productos para que los usuarios vean los productos disponibles.
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
* Funcionalidad: Retorna la página del catálogo de productos con productos que cumplen un filtro específico.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página del catálogo de productos con productos filtrados.
* Parámetros:
    * Query Params:
        * filtro: string (Ej. "Ofertas")
* Gestión de errores:
    * 404: Si la página del catálogo con filtro no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/catalogo/filtro?filtro=Ofertas
    * Respuesta: HTML de la página del catálogo de productos con productos en oferta.

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
 
## POST Administrador crea nuevo producto en la base de datos:

* Breve descripción: Crea un nuevo producto en la base de datos.
* Ruta: POST /tiendaropa/productos
* Método: POST
* Funcionalidad: Crea un nuevo producto en la base de datos con la información proporcionada.
* Estructura de la petición:
    * Body:
````json
{
  "nombre": string,
  "precio": float,
  "stock": int,
  "numRef": string,
  "destacado": boolean,
  "categoriaId": int
}
````
* Estructura de la respuesta:  
201: Se crea correctamente.
````json
{
  "id": int
}
````  
400: Formato de datos incorrecto.
````json
{
  "errors": {
    string,
    ...
  }
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
POST localhost:8080/tiendaropa/productos

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "nombre": "Camiseta Deportiva",
  "precio": 29.99,
  "stock": 50,
  "numRef": "CD123",
  "destacado": true,
  "categoriaId": 1
}
````  
Respuesta:
````json
201 Created

{
  "id": 456
}
````

## DELETE Administrador elimina producto de la base de datos:

* Breve descripción: Elimina un producto de la base de datos.
* Ruta: DELETE /tiendaropa/productos/{id}
* Método: DELETE
* Funcionalidad: Elimina un producto de la base de datos según su ID.
* Estructura de la petición: No aplica (DELETE request).
* Estructura de la respuesta:
     * 204: Eliminado correctamente.
     * 404: Producto no encontrado.
* Parámetros:
    * Path Params:
        * id: int (ID del producto a eliminar)
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
DELETE localhost:8080/tiendaropa/productos/456

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
* 204 No Content (Eliminado correctamente)
* 404 Not Found (Producto no encontrado)

## PUT Administrador modifica el producto de la base de datos:

* Breve descripción: Modifica un producto en la base de datos.
* Ruta: PUT /tiendaropa/productos/{id}
* Método: PUT
* Funcionalidad: Modifica un producto en la base de datos según su ID con la información proporcionada.
* Estructura de la petición:
    * Body:
````json
{
  "nombre": string,
  "precio": float,
  "stock": int,
  "numRef": string,
  "destacado": boolean,
  "categoriaId": int

}
````
* Estructura de la respuesta:  
200: Modificado correctamente.
````json
{
  "id": int
}
```` 
400: Formato de datos incorrecto.
````json
{
  "errors": {
    string,
    ...
  }
}
````` 
404: Producto no encontrado.
* Parámetros:
    * Path Params:
        * id: int (ID del producto a modificar)
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
PUT localhost:8080/tiendaropa/productos/456

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "nombre": "Camiseta Deportiva",
  "precio": 34.99,
  "stock": 40,
  "numRef": "CD123",
  "destacado": true,
  "categoriaId": 1
}
````  
Respuesta:
````json
200 OK

{
  "id": 456
}
````
````json
404 Not Found

{
  "error": "Producto no encontrado"
}
````

## GET Obtener página con los detalles del producto concreto:

* Breve descripción: Obtiene la página con los detalles de un producto específico.
* Ruta: GET /tiendaropa/productos/{id}
* Método: GET
* Funcionalidad: Retorna la página con los detalles de un producto específico.
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

## POST Mandar el producto concreto al carrito de la compra:

* Breve descripción: Agrega un producto específico al carrito de la compra.
* Ruta: POST /tiendaropa/carrito
* Método: POST
* Funcionalidad: Agrega un producto al carrito de la compra.
* Estructura de la petición:
    * Body:
````json
{
  "usuarioId": int,
  "productoId": int,
  "cantidad": int
}
````
* Estructura de la respuesta:  
201: Agregado al carrito correctamente.
````json
{
  "id": int
}
````  
400: Formato de datos incorrecto o cantidad no disponible.
````json
{
  "errors": {
    string,
    ...
  }
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto o la cantidad no está disponible.
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
POST localhost:8080/tiendaropa/carrito

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "usuarioId": 123,
  "productoId": 456,
  "cantidad": 2
}
````  
Respuesta:
````json
201 Created

{
  "id": 789
}
````
````json
400 Bad Request

{
  "errors": {
    "cantidad": "La cantidad no está disponible"
  }
}
````

## DELETE Administrador elimina producto de la base de datos:

* Breve descripción: Elimina un producto específico de la base de datos.
* Ruta: DELETE /tiendaropa/productos/{id}
* Método: DELETE
* Funcionalidad: Elimina un producto específico de la base de datos según su ID.
* Estructura de la petición: No aplica (DELETE request).
* Estructura de la respuesta:
    * 204: Eliminado correctamente.
    * 404: Producto no encontrado.
* Parámetros:
    * Path Params:
        * id: int (ID del producto a eliminar)
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
DELETE localhost:8080/tiendaropa/productos/456

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
* 204 No Content (Eliminado correctamente)
* 404 Not Found (Producto no encontrado)

## PUT Administrador modifica el producto de la base de datos:

* Breve descripción: Modifica un producto específico en la base de datos.
* Ruta: PUT /tiendaropa/productos/{id}
* Método: PUT
* Funcionalidad: Modifica un producto en la base de datos según su ID con la información proporcionada.
* Estructura de la petición:
    * Body:
````json
{
  "nombre": string,
  "precio": float,
  "stock": int,
  "numRef": string,
  "destacado": boolean,
  "categoriaId": int
}
````
* Estructura de la respuesta:  
200: Modificado correctamente.
````json
{
  "id": int
}
````  
400: Formato de datos incorrecto.
````json
{
  "errors": {
    string,
    ...
  }
}
````  
404: Producto no encontrado.
* Parámetros:
    * Path Params:
        * id: int (ID del producto a modificar)
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
PUT localhost:8080/tiendaropa/productos/456

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "nombre": "Camiseta Deportiva",
  "precio": 34.99,
  "stock": 40,
  "numRef": "CD123",
  "destacado": true,
  "categoriaId": 1
}
````  
Respuesta:
````json
200 OK

{
  "id": 456
}
````
````json
404 Not Found

{
  "error": "Producto no encontrado"
}
````

## GET Obtener carrito de la compra:

* Breve descripción: Obtiene el contenido actual del carrito de la compra de un usuario.
* Ruta: GET /tiendaropa/carrito
* Método: GET
* Funcionalidad: Retorna la información del carrito de la compra de un usuario.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta:
    * 200: Carrito obtenido correctamente.
````json
{
  "productos": [
    {
      "id": int,
      "nombre": string,
      "precio": float,
      "cantidad": int
    },
    ...
  ],
  "total": float
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
GET localhost:8080/tiendaropa/carrito

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
````json
200 OK

{
  "productos": [
    {
      "id": 456,
      "nombre": "Camiseta Deportiva",
      "precio": 34.99,
      "cantidad": 2
    }
  ],
  "total": 69.98
}
````

## GET Continuar con la compra y obtener método de pago:

* Breve descripción: Obtiene el método de pago disponible para continuar con la compra.
* Ruta: GET /tiendaropa/compra/metodopago
* Método: GET
* Funcionalidad: Retorna el método de pago disponible para continuar con la compra.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta:
    * 200: Método de pago obtenido correctamente.
````json
{
  "metodo": string
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
GET localhost:8080/tiendaropa/compra/metodopago

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
````json
200 OK

{
  "metodo": "Tarjeta de crédito"
}
````

## POST Pagar el pedido del carrito de la compra:

* Breve descripción: Realiza el pago del pedido del carrito de la compra.
* Ruta: POST /tiendaropa/compra/pagar
* Método: POST
* Funcionalidad: Realiza el pago del pedido del carrito de la compra.
* Estructura de la petición:
    * Body:
````json
{
  "metodo": string,
  "detalles": {
    // Detalles específicos del método de pago
  }
}
````
* Estructura de la respuesta:  
200: Pago realizado correctamente.
````json
{
  "idPedido": int
}
````  
400: Datos de pago incorrectos.
````json
{
  "error": "Datos de pago incorrectos"
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 400: Si los datos de pago son incorrectos.
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
POST localhost:8080/tiendaropa/compra/pagar

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "metodo": "Tarjeta de crédito",
  "detalles": {
    "numeroTarjeta": "1234567890123456",
    "nombreTitular": "John Doe",
    "fechaExpiracion": "12/24",
    "codigoSeguridad": "123"
  }
}
````  
Respuesta:
````json
200 OK

{
  "idPedido": 789
}
````
````json
400 Bad Request

{
  "error": "Datos de pago incorrectos"
}
````

## DELETE Eliminar producto del carrito de la compra:

* Breve descripción: Elimina un producto específico del carrito de la compra.
* Ruta: DELETE /tiendaropa/carrito/{idProducto}
* Método: DELETE
* Funcionalidad: Elimina un producto específico del carrito de la compra.
* Estructura de la petición: No aplica (DELETE request).
* Estructura de la respuesta:
    * 204: Producto eliminado correctamente.
    * 404: Producto no encontrado en el carrito.
* Parámetros:
    * Path Params:
        * idProducto: int (ID del producto a eliminar)
    * Headers:
        * Authorization: string (Token de usuario)
* Gestión de errores:
    * 401: Si la autorización es incorrecta.
    * 404: Si el producto no se encuentra en el carrito.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
DELETE localhost:8080/tiendaropa/carrito/456

Headers:
{
  "Authorization": "Bearer <token>"
}
````  
Respuesta:
* 204 No Content (Eliminado correctamente)
* 404 Not Found (Producto no encontrado en el carrito)

## GET Obtener página About:

* Breve descripción: Obtiene la página "About" de la tienda.
* Ruta: GET /tiendaropa/about
* Método: GET
* Funcionalidad: Retorna la página "About" de la tienda.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página "About".
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página "About" no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/about
    * Respuesta: HTML de la página "About".
 
## GET Obtener página HOME:

* Breve descripción: Obtiene la página de inicio (HOME) de la tienda.
* Ruta: GET /tiendaropa/
* Método: GET
* Funcionalidad: Retorna la página de inicio (HOME) de la tienda.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta: HTML de la página de inicio (HOME).
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si la página de inicio (HOME) no se encuentra.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/
    * Respuesta: HTML de la página de inicio (HOME).

## GET Obtener página con el listado de productos ofertados/destacados:

* Breve descripción: Obtiene la página con el listado de productos ofertados o destacados.
* Ruta: GET /tiendaropa/productos/ofertados
* Método: GET
* Funcionalidad: Retorna la página con el listado de productos ofertados o destacados.
* Estructura de la petición: No aplica (GET request).
* Estructura de la respuesta:
    * 200: Listado de productos ofertados o destacados.
````json
{
  "productos": [
    {
      "id": int,
      "nombre": string,
      "precio": float,
      "ofertado": boolean,
      "destacado": boolean
    },
    ...
  ]
}
````
* Parámetros: No aplica.
* Gestión de errores:
    * 404: Si no hay productos ofertados o destacados.
    * 500: Error interno del servidor.
* Ejemplo de uso:
    * Petición: GET localhost:8080/tiendaropa/productos/ofertados
    * Respuesta:
````json
200 OK

{
  "productos": [
    {
      "id": 456,
      "nombre": "Camiseta Deportiva",
      "precio": 34.99,
      "ofertado": true,
      "destacado": false
    },
    {
      "id": 789,
      "nombre": "Zapatillas Running",
      "precio": 79.99,
      "ofertado": false,
      "destacado": true
    }
  ]
}
````

## PUT Administrador modifica los estados ofertado/destacado de los productos:

* Breve descripción: Modifica el estado ofertado o destacado de los productos en la base de datos.
* Ruta: PUT /tiendaropa/productos/ofertados
* Método: PUT
* Funcionalidad: Modifica el estado ofertado o destacado de los productos en la base de datos.
* Estructura de la petición:
    * Body:
````json
{
  "productos": [
    {
      "id": int,
      "ofertado": boolean,
      "destacado": boolean
    },
    ...
  ]
}
````
* Estructura de la respuesta:  
200: Modificado correctamente.
````json
{
  "productosModificados": [
    {
      "id": int
    },
    ...
  ]
}
````  
400: Formato de datos incorrecto.
````json
{
  "error": "Formato de datos incorrecto"
}
````
* Parámetros:
    * Headers:
        * Authorization: string (Token de administrador)
* Gestión de errores:
    * 400: Si el formato de datos es incorrecto.
    * 401: Si la autorización es incorrecta.
    * 500: Error interno del servidor.
* Ejemplo de uso:  
Petición:
````json
PUT localhost:8080/tiendaropa/productos/ofertados

Headers:
{
  "Authorization": "Bearer <token>"
}

Body:
{
  "productos": [
    {
      "id": 456,
      "ofertado": true,
      "destacado": false
    },
    {
      "id": 789,
      "ofertado": false,
      "destacado": true
    }
  ]
}
````  
Respuesta:
````json
200 OK

{
  "productosModificados": [
    {
      "id": 456
    },
    {
      "id": 789
    }
  ]
}
````
````json
400 Bad Request

{
  "error": "Formato de datos incorrecto"
