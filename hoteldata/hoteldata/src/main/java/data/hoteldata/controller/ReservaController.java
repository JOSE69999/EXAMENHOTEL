package data.hoteldata.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.hoteldata.model.Reserva;
import data.hoteldata.service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    // Constructor con inyección de dependencias
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Método para crear una nueva reserva
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        try {
            Reserva nuevaReserva = reservaService.crearReserva(reserva);
            return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
        } catch (Exception e) {
            // En caso de error, se retorna un 500 Internal Server Error con mensaje
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodasLasReservas() {
        try {
            List<Reserva> reservas = reservaService.obtenerTodasReservas();
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        } catch (Exception e) {
            // En caso de error, se retorna un 500 Internal Server Error con mensaje
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = reservaService.obtenerReservaPorId(id);

        if (reserva.isPresent()) {
            return new ResponseEntity<>(reserva.get(), HttpStatus.OK);
        } else {
            // En caso de no encontrar la reserva, retornamos un 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para actualizar una reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable("id") Long id, @RequestBody Reserva reserva) {
        try {
            // Obtener la reserva existente por su ID
            Optional<Reserva> reservaExistente = reservaService.obtenerReservaPorId(id);

            // Verificar si la reserva existe
            if (reservaExistente.isPresent()) {
                // Si existe, actualizamos los datos
                Reserva reservaActualizada = reservaExistente.get();
                reservaActualizada.setFechaInicio(reserva.getFechaInicio());
                reservaActualizada.setFechaFin(reserva.getFechaFin());
                reservaActualizada.setTipoHabitacion(reserva.getTipoHabitacion());

                // Guardamos la reserva actualizada
                return new ResponseEntity<>(reservaService.crearReserva(reservaActualizada), HttpStatus.OK);
            } else {
                // Si no existe la reserva, retornamos un 404 Not Found
                return new ResponseEntity<>("Reserva no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Si hay algún error inesperado, retornamos un 500 Internal Server Error con mensaje
            return new ResponseEntity<>("Error al actualizar la reserva: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para cancelar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable("id") Long id) {
        try {
            Optional<Reserva> reservaExistente = reservaService.obtenerReservaPorId(id);

            if (reservaExistente.isPresent()) {
                reservaService.eliminarReserva(id);
                return new ResponseEntity<>("Reserva eliminada con éxito", HttpStatus.OK);
            } else {
                // Si no existe la reserva, retornamos un 404 Not Found
                return new ResponseEntity<>("Reserva no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // En caso de error, retornamos un 500 Internal Server Error con mensaje
            return new ResponseEntity<>("Error al cancelar la reserva: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}