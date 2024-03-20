package tiendaropa.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.controller.exception.ProductoNotFoundException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.*;
import tiendaropa.model.*;
import tiendaropa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class EnviosController{
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private LineaCarritoService lineaCarritoService;
    @Autowired
    private ManagerUserSession managerUserSession;
    @Autowired
    UsuarioService usuarioService;

    //private final String API_URL = "https://ebisu.firstrow2.com/api/transactions";
    private final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTN9.R5LwbZjBrobQHgxSLf4qovDKEeHmlJJoUDXXEt6OWpc";

    @GetMapping("/tiendaropa/envios")
    public String hacerPeticionGetALaAPIEnvios(Model model) {
        try {
            // URL de la API
            String API_URL = "http://localhost:8081/api/envios";

            // Datos del cuerpo JSON
            String cuerpoJson = "{ \"fechaInicio\": null, \"fechaFin\": null }";

            // Crear la configuración de la petición HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);  // Tipo de medio JSON
            headers.add("Authorization", "123456789");  // API Key

            // Configurar el cuerpo y las cabeceras de la petición
            HttpEntity<String> requestEntity = new HttpEntity<>(cuerpoJson, headers);

            // Crear la instancia de RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Hacer la petición POST y obtener la respuesta
            ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.GET, requestEntity, String.class);

            // Verificar si la petición fue exitosa (código de estado 2xx)
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String respuestaApi = responseEntity.getBody();

                // Agregar la respuesta al modelo
                model.addAttribute("respuestaAPI", respuestaApi);

                // Devolver el nombre de la plantilla que deseas mostrar (envios.html)
                return "envios";
            } else {
                // Si la petición no fue exitosa, manejar el error según tus necesidades
                System.err.println("Error en la petición POST a la API. Código de estado: " + responseEntity.getStatusCodeValue());
                return "Error en la petición POST a la API.";
            }

        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la petición
            System.err.println("Error al hacer la petición POST a la API.");
            e.printStackTrace();
            return "Error al hacer la petición POST a la API.";
        }
    }

    @PostMapping("/tiendaropa/envios")
    public String procesarEnvio(Model model) {
        try {
            Long usuarioId = managerUserSession.usuarioLogeado();
            UsuarioData user = usuarioService.findById(usuarioId);

            List<Usuario> usuarios = usuarioService.listadoCompleto();
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

            System.out.println(usuario);

            // Obtener el carrito del usuario
            Carrito carrito = carritoService.obtenerCarritoUsuario(usuario);
            //System.out.println("Carrito: " + carrito);
            //System.out.println("lineas en procesar: " + carrito.getLineascarrito());
            // Realizar la petición POST
            String respuestaAPI = hacerPeticionPostALaAPIEnvios(carritoService.obtenerTotalProductosCarrito(carrito)); // Implementa este método

            // Aquí puedes manipular la respuesta de la API según tus necesidades
            System.out.println("Respuesta de la API: " + respuestaAPI);

            // Eliminar todas las líneas del carrito
            carritoService.eliminarTodasLasLineasCarrito(carrito);

            // Agregar la respuesta de la API al modelo para mostrarla en la vista
            model.addAttribute("respuestaAPI", respuestaAPI);
            model.addAttribute("usuario", user);

        } catch (Exception e) {
            System.err.println("Error durante el procesamiento del checkout.");
            e.printStackTrace();
        }

        // Mostrar la vista checkout con la respuesta de la API
        return "respuestaenvio";
    }

    public String hacerPeticionPostALaAPIEnvios(int totalProductos) {
        //System.out.println("Total productos en envio: " + totalProductos);
        try {
            // URL de la API
            String API_URL = "http://localhost:8081/api/envios";
            String jsonBody = "{\n" +
                    "  \"peso\": 10,\n" +
                    "  \"observaciones\": \"productos tienda de ropa\",\n" +
                    "  \"bultos\": " + 4 + ",\n" +
                    "  \"destino\": {\n" +
                    "    \"codigoPostal\": \"12345\",\n" +
                    "    \"localidad\": \"almoradi\",\n" +
                    "    \"provincia\": \"alicante\",\n" +
                    "    \"numero\": 10,\n" +
                    "    \"planta\": 1,\n" +
                    "    \"calle\": \"calle mayor\",\n" +
                    "    \"nombre\": \"paco\",\n" +
                    "    \"telefono\": 666777888\n" +
                    "  }\n" +
                    "}";

            // Crear la configuración de la petición HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "123456789");  // Reemplazar con tu nueva API Key

            // Configurar el cuerpo y las cabeceras de la petición
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            // Crear la instancia de RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Hacer la petición POST y obtener la respuesta
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, String.class);

            // Verificar si la petición fue exitosa (código de estado 2xx)
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                // Si la petición no fue exitosa, manejar el error según tus necesidades
                System.err.println("Error en la petición POST a la API. Código de estado: " + responseEntity.getStatusCodeValue());
                return "Error en la petición POST a la API.";
            }

        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la petición
            System.err.println("Error al hacer la petición POST a la API.");
            e.printStackTrace();
            return "Error al hacer la petición POST a la API.";
        }
    }
}
