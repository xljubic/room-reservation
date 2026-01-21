/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.fon.room_reservation.dto;

import java.time.LocalDateTime;

/**
 *
 * @author Aleksandar
 */
public class CalendarReservationDTO {

    private Long reservationId;
    private Long roomId;
    private String roomCode;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public CalendarReservationDTO() {
    }

    public CalendarReservationDTO(
            Long reservationId,
            Long roomId,
            String roomCode,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
