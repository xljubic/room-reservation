/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.fon.room_reservation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.room_reservation.dto.CreateReservationRequest;
import rs.fon.room_reservation.dto.ReservationResponse;
import rs.fon.room_reservation.entity.Reservation;
import rs.fon.room_reservation.entity.Room;
import rs.fon.room_reservation.entity.User;
import rs.fon.room_reservation.enums.ReservationStatus;
import rs.fon.room_reservation.repository.ReservationRepository;
import rs.fon.room_reservation.repository.RoomRepository;
import rs.fon.room_reservation.repository.UserRepository;

/**
 *
 * @author Aleksandar
 */
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationController(
            ReservationRepository reservationRepository,
            RoomRepository roomRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateReservationRequest req) {

        if (req.getRoomId() == null || req.getCreatedById() == null
                || req.getStartDateTime() == null || req.getEndDateTime() == null
                || req.getPurpose() == null || req.getName() == null) {
            return ResponseEntity.badRequest().body("Missing required fields.");
        }

        if (!req.getEndDateTime().isAfter(req.getStartDateTime())) {
            return ResponseEntity.badRequest().body("endDateTime must be after startDateTime.");
        }

        Room room = roomRepository.findById(req.getRoomId()).orElse(null);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found.");
        }

        User createdBy = userRepository.findById(req.getCreatedById()).orElse(null);
        if (createdBy == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        Reservation r = new Reservation();
        r.setRoom(room);
        r.setCreatedBy(createdBy);
        r.setStartDateTime(req.getStartDateTime());
        r.setEndDateTime(req.getEndDateTime());
        r.setPurpose(req.getPurpose());
        r.setName(req.getName());
        r.setDescription(req.getDescription()); // može null
        r.setStatus(ReservationStatus.PENDING);
        r.setCreatedAt(LocalDateTime.now());

        Reservation saved = reservationRepository.save(r);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping
    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ReservationResponse toResponse(Reservation r) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(r.getId());
        dto.setStartDateTime(r.getStartDateTime());
        dto.setEndDateTime(r.getEndDateTime());
        dto.setPurpose(r.getPurpose());
        dto.setStatus(r.getStatus());
        dto.setName(r.getName());
        dto.setDescription(r.getDescription());
        dto.setCreatedAt(r.getCreatedAt());

        dto.setRoomId(r.getRoom().getId());
        dto.setRoomCode(r.getRoom().getCode());

        dto.setCreatedById(r.getCreatedBy().getId());
        dto.setCreatedByEmail(r.getCreatedBy().getEmail());

        return dto;
    }

    @GetMapping("/user/{userId}")
    public List<ReservationResponse> getByUser(@PathVariable Long userId) {
        return reservationRepository.findByCreatedBy_Id(userId)
                .stream()
                .map(this::toResponse)
                .collect(java.util.stream.Collectors.toList());
    }
//    @GetMapping("/my")
//public List<ReservationResponse> myReservations(@RequestParam Long userId) {
//    return reservationService.findByUser(userId);
//}

}
