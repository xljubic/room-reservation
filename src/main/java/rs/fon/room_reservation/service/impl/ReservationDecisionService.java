/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.fon.room_reservation.service.impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import rs.fon.room_reservation.entity.Reservation;
import rs.fon.room_reservation.entity.ReservationApproval;
import rs.fon.room_reservation.entity.User;
import rs.fon.room_reservation.enums.ApprovalDecision;
import rs.fon.room_reservation.enums.ReservationStatus;
import rs.fon.room_reservation.repository.ReservationApprovalRepository;
import rs.fon.room_reservation.repository.ReservationRepository;
import rs.fon.room_reservation.repository.UserRepository;

/**
 *
 * @author Aleksandar
 */
@Service
public class ReservationDecisionService {

    private final ReservationRepository reservationRepository;
    private final ReservationApprovalRepository approvalRepository;
    private final UserRepository userRepository;

    public ReservationDecisionService(
            ReservationRepository reservationRepository,
            ReservationApprovalRepository approvalRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.approvalRepository = approvalRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void approve(Long reservationId, Long decidedByUserId, String comment) {
        decide(reservationId, decidedByUserId, comment, ApprovalDecision.APPROVED);
    }

    @Transactional
    public void reject(Long reservationId, Long decidedByUserId, String comment) {
        decide(reservationId, decidedByUserId, comment, ApprovalDecision.REJECTED);
    }

//    private void decide(Long reservationId, Long decidedByUserId, String comment, ApprovalDecision decision) {
//        Reservation reservation = reservationRepository.findById(reservationId)
//                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));
//
////        if (approvalRepository.existsByReservation(reservation)) {
////            throw new IllegalStateException("Reservation already decided (approval exists).");
////        }
//
////        if (reservation.getStatus() != ReservationStatus.PENDING) {
////            throw new IllegalStateException("Only PENDING reservations can be decided.");
////        }
//
//        User decidedBy = null;
//        if (decidedByUserId != null) {
//            decidedBy = userRepository.findById(decidedByUserId)
//                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + decidedByUserId));
//        }
//
//        // 1) update reservation status
//        reservation.setStatus(decision == ApprovalDecision.APPROVED
//                ? ReservationStatus.APPROVED
//                : ReservationStatus.REJECTED);
//        reservationRepository.save(reservation);
//
//        // 2) insert approval row
//        ReservationApproval approval = new ReservationApproval();
//        approval.setReservation(reservation);
//        approval.setDecidedBy(decidedBy);
//        approval.setDecision(decision);
//        approval.setComment(comment);
//        approval.setDecidedAt(LocalDateTime.now());
//        approvalRepository.save(approval);
//    }
    private void decide(Long reservationId, Long decidedByUserId, String comment, ApprovalDecision decision) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));

        User decidedBy = null;
        if (decidedByUserId != null) {
            decidedBy = userRepository.findById(decidedByUserId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + decidedByUserId));
        }

        // 1️⃣ promena statusa rezervacije
        reservation.setStatus(
                decision == ApprovalDecision.APPROVED
                        ? ReservationStatus.APPROVED
                        : ReservationStatus.REJECTED
        );
        reservationRepository.save(reservation);

        // 2️⃣ UPIS NOVOG approval zapisa (HISTORY)
        ReservationApproval approval = new ReservationApproval();
        approval.setReservation(reservation);
        approval.setDecidedBy(decidedBy);
        approval.setDecision(decision);
        approval.setComment(comment);
        approval.setDecidedAt(LocalDateTime.now());

        approvalRepository.save(approval);
    }

    @Transactional
    public void cancel(Long reservationId, Long userId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (!reservation.getCreatedBy().getId().equals(userId)) {
            throw new IllegalStateException("You can cancel only your own reservations.");
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);
    }
}
