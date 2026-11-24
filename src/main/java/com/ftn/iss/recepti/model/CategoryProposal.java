package com.ftn.iss.recepti.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "category_proposals")
public class CategoryProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String proposedName;

    @Column(nullable = false)
    private String proposedByUsername;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status = ProposalStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public CategoryProposal() {}
    public CategoryProposal(String proposedName, String proposedByUsername) {
        this.proposedName = proposedName;
        this.proposedByUsername = proposedByUsername;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProposedName() { return proposedName; }
    public void setProposedName(String proposedName) { this.proposedName = proposedName; }
    public String getProposedByUsername() { return proposedByUsername; }
    public void setProposedByUsername(String proposedByUsername) { this.proposedByUsername = proposedByUsername; }
    public ProposalStatus getStatus() { return status; }
    public void setStatus(ProposalStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}