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
public class CarritoController {
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private LineaCarritoService lineaCarritoService;
    @Autowired
    private ManagerUserSession managerUserSession;
    @Autowired
    UsuarioService usuarioService;

    private final String API_URL = "https://ebisu.firstrow2.com/api/transactions";
    private final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTN9.R5LwbZjBrobQHgxSLf4qovDKEeHmlJJoUDXXEt6OWpc";



    @GetMapping("/tiendaropa/carrito")
    public String verCarrito(Model model) {
        // Obtener el usuario actual (puedes modificar esto según tu lógica de autenticación)
        Long usuarioId = managerUserSession.usuarioLogeado();
        UsuarioData user = usuarioService.findById(usuarioId);

        if(usuarioId == null){

        }

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

        // Obtener el carrito del usuario
        Carrito carrito = carritoService.obtenerCarritoUsuario(usuario);

        // Obtener todas las líneas de ese carrito
        List<LineaCarrito> lineasCarrito = carritoService.allLineasCarrito(carrito);

        float total = carritoService.obtenerTotalCarrito(carrito);

        // Pasar las líneas del carrito al modelo para que la vista pueda mostrarlas
        model.addAttribute("totalCarrito", total);
        model.addAttribute("lineasCarrito", lineasCarrito);
        model.addAttribute("usuario", user);

        return "carrito";
    }

    @PostMapping("/tiendaropa/carrito")
    public String añadirProductosCarrito(@RequestParam String productoId,
                                         @RequestParam int cantidad,
                                         RedirectAttributes redirectAttributes) {

        Long prodId = Long.parseLong(productoId);
        // Obtener el usuario actual (puedes modificar esto según tu lógica de autenticación)
        Long usuarioId = managerUserSession.usuarioLogeado();

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

        // Obtener el carrito del usuario
        Carrito carrito = carritoService.obtenerCarritoUsuario(usuario);

        // Añadir productos al carrito
        try {
            lineaCarritoService.añadirProductos(carrito, prodId, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Producto añadido al carrito correctamente.");
        } catch (ProductoNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
        } catch (UsuarioNoLogeadoException e) {
            redirectAttributes.addFlashAttribute("error", "Usuario no logeado.");
        }

        return "redirect:/tiendaropa/catalogo";
    }

    @PostMapping("/tiendaropa/compra/checkout")
    public String procesarCheckout(Model model) {
        try {
            Long usuarioId = managerUserSession.usuarioLogeado();
            UsuarioData user = usuarioService.findById(usuarioId);

            List<Usuario> usuarios = usuarioService.listadoCompleto();
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

            System.out.println(usuario);

            // Obtener el carrito del usuario
            Carrito carrito = carritoService.obtenerCarritoUsuario(usuario);

            // Realizar la petición POST
            String respuestaAPI = hacerPeticionPostALaAPI(carritoService.obtenerTotalCarrito(carrito)); // Implementa este método

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
        return "checkout";
    }

    public String hacerPeticionPostALaAPI(float totalProductos) {
        System.out.println("totalProductos: " + totalProductos);
        try {

            // Crear el objeto de datos en el formato JSON
            String jsonBody = "{\n" +
                    "  \"concept\": \"compra\",\n" +
                    "  \"amount\": " + totalProductos + ",\n" +  // Ajustar el campo 'amount'
                    "  \"receipt_number\": \"string\",\n" +
                    "  \"payment\": {\n" +
                    "    \"type\": \"paypal\",\n" +
                    "    \"values\": {\n" +
                    "      \"paypal_user\": \"string\",\n" +
                    "      \"credit_card_number\": \"string\",\n" +
                    "      \"credit_card_expiration_month\": 12,\n" +
                    "      \"credit_card_expiration_year\": 0,\n" +
                    "      \"credit_card_csv\": 999\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            // Crear la configuración de la petición HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_TOKEN);
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

    @GetMapping("/tiendaropa/compra/checkout")
    public String hacerPeticionGetALaAPI(Model model) {
        try {
            // Crear la configuración de la petición HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + API_TOKEN);

            // Crear la instancia de RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Configurar las cabeceras
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            // Hacer la petición GET y obtener la respuesta
            ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.GET, requestEntity, String.class);

            // Verificar si la petición fue exitosa (código de estado 2xx)
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String respuestaApi = responseEntity.getBody();

                // Agregar la respuesta al modelo
                model.addAttribute("respuestaAPI", respuestaApi);

                // Devolver el nombre de la plantilla que deseas mostrar (checkout.html)
                return "transacciones";
            } else {
                // Si la petición no fue exitosa, manejar el error según tus necesidades
                System.err.println("Error en la petición GET a la API. Código de estado: " + responseEntity.getStatusCodeValue());
                return "Error en la petición GET a la API.";
            }

        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante la petición
            System.err.println("Error al hacer la petición GET a la API.");
            e.printStackTrace();
            return "Error al hacer la petición GET a la API.";
        }
    }


    /*
    @GetMapping("/tiendaropa/compra/checkout")
    public String checkout(Model model) {
        try {
            Long usuarioId = managerUserSession.usuarioLogeado();
            UsuarioData user = usuarioService.findById(usuarioId);

            List<Usuario> usuarios = usuarioService.listadoCompleto();
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, usuarioId);

            System.out.println(usuario);

            // Obtener el carrito del usuario
            Carrito carrito = carritoService.obtenerCarritoUsuario(usuario);

            System.out.println(carrito.getId());

            // Eliminar todas las líneas del carrito
            carritoService.eliminarTodasLasLineasCarrito(carrito);

            model.addAttribute("usuario", user);

        } catch (Exception e) {
            System.err.println("Error durante el proceso de checkout.");
            e.printStackTrace();
        }

        //en la pagina de checkout mostrar la respuesta del servidor
        return "checkout";
    }
    */

}
