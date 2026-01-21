/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.fon.room_reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.room_reservation.dto.DecisionRequest;
import rs.fon.room_reservation.service.impl.ReservationDecisionService;

/**
 *
 * @author Aleksandar
 */
@RestController
@RequestMapping("/reservations")
public class ReservationDecisionController {

    private final ReservationDecisionService decisionService;

    public ReservationDecisionController(ReservationDecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id, @RequestBody DecisionRequest req) {
        decisionService.approve(id, req.getDecidedByUserId(), req.getComment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id, @RequestBody DecisionRequest req) {
        decisionService.reject(id, req.getDecidedByUserId(), req.getComment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        decisionService.cancel(id, userId);
        return ResponseEntity.ok().build();
    }
}
