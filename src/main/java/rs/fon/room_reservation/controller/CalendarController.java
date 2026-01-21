/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.fon.room_reservation.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import rs.fon.room_reservation.dto.CalendarReservationDTO;
import rs.fon.room_reservation.entity.Reservation;
import rs.fon.room_reservation.enums.ReservationStatus;
import rs.fon.room_reservation.repository.ReservationRepository;

/**
 *
 * @author Aleksandar
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private final ReservationRepository reservationRepository;

    public CalendarController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // npr: /calendar/approved?date=2026-01-20
    @GetMapping("/approved")
    public List<CalendarReservationDTO> getApprovedForDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDateTime start = date.atTime(0, 0);
        LocalDateTime end = date.atTime(23, 59, 59);

        List<Reservation> reservations
                = reservationRepository.findByStatusAndStartDateTimeBetween(
                        ReservationStatus.APPROVED, start, end
                );

        return reservations.stream()
                .map(r -> new CalendarReservationDTO(
                r.getId(),
                r.getRoom().getId(),
                r.getRoom().getCode(),
                r.getStartDateTime(),
                r.getEndDateTime()
        ))
                .collect(Collectors.toList());
    }

    @GetMapping("/approved/range")
    public List<CalendarReservationDTO> getApprovedInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        return reservationRepository
                .findByStatusAndStartDateTimeBetween(
                        ReservationStatus.APPROVED, start, end
                )
                .stream()
                .map(r -> new CalendarReservationDTO(
                r.getId(),
                r.getRoom().getId(),
                r.getRoom().getCode(),
                r.getStartDateTime(),
                r.getEndDateTime()
        ))
                .toList();
    }

}
