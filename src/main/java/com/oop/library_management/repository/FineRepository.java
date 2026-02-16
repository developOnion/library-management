package com.oop.library_management.repository;

import com.oop.library_management.domain.Fine;
import com.oop.library_management.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

	List<Fine> findByLoan(Loan loan);

	List<Fine> findByLoan_Member_Id(Long loanMemberId);

	List<Fine> findByPaidFalse();

	List<Fine> findByLoan_Member_IdAndPaidFalse(Long loanMemberId);
}
