/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.fon.room_reservation.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.room_reservation.entity.Reservation;
import rs.fon.room_reservation.entity.ReservationApproval;

/**
 *
 * @author Aleksandar
 */
public interface ReservationApprovalRepository extends JpaRepository<ReservationApproval, Long> {

    Optional<ReservationApproval> findByReservation(Reservation reservation);

    //boolean existsByReservation(Reservation reservation);
}
