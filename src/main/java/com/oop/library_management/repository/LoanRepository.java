package com.oop.library_management.repository;

import com.oop.library_management.model.Book;
import com.oop.library_management.model.Loan;
import com.oop.library_management.model.LoanStatus;
import com.oop.library_management.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

	List<Loan> findByMember(Member member);

	List<Loan> findByBook(Book book);

	List<Loan> findByStatus(LoanStatus status);

	List<Loan> findByMemberAndStatus(Member member, LoanStatus status);
}

