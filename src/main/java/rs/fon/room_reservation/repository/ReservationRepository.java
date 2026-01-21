/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.fon.room_reservation.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.room_reservation.entity.Reservation;
import rs.fon.room_reservation.enums.ReservationStatus;

/**
 *
 * @author Aleksandar
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
        List<Reservation> findByStatusAndStartDateTimeBetween(
            ReservationStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Reservation> findByCreatedBy_Id(Long userId);
}
