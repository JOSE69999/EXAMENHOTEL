package data.hoteldata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.hoteldata.model.Reserva;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}