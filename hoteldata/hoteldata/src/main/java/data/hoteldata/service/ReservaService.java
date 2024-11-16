package data.hoteldata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import data.hoteldata.model.Reserva;
import data.hoteldata.repository.ReservaRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Crear una nueva reserva
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Obtener todas las reservas
    public List<Reserva> obtenerTodasReservas() {
        return reservaRepository.findAll();
    }

    // Obtener una reserva por su ID
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // Actualizar una reserva existente
    public Reserva actualizarReserva(Long id, Reserva reservaActualizada) {
        if (reservaRepository.existsById(id)) {
            reservaActualizada.setId(id);
            return reservaRepository.save(reservaActualizada);
        }
        return null;  // Si la reserva no existe, retorna null o puedes lanzar una excepción
    }

    // Eliminar una reserva por su ID
    public boolean eliminarReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false; // Si no existe, retorna false
    }
}
