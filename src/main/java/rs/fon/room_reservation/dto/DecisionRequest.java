/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.fon.room_reservation.dto;

/**
 *
 * @author Aleksandar
 */
public class DecisionRequest {

    private Long decidedByUserId;
    private String comment;

    public DecisionRequest() {
    }

    public Long getDecidedByUserId() {
        return decidedByUserId;
    }

    public void setDecidedByUserId(Long decidedByUserId) {
        this.decidedByUserId = decidedByUserId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
