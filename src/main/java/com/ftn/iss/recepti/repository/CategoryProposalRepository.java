package com.ftn.iss.recepti.repository;

import com.ftn.iss.recepti.model.CategoryProposal;
import com.ftn.iss.recepti.model.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryProposalRepository extends JpaRepository<CategoryProposal, Long> {
    List<CategoryProposal> findByStatus(ProposalStatus status);
}