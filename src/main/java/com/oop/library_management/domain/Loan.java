package com.oop.library_management.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(optional = false)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@Column(name = "loan_date", nullable = false)
	private LocalDate loanDate;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Column(name = "return_date")
	private LocalDate returnDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoanStatus status;

	protected Loan() {
		// Required by JPA
	}

	public Loan(Member member, Book book, LocalDate loanDate, LocalDate dueDate) {
		this.member = member;
		this.book = book;
		this.loanDate = loanDate;
		this.dueDate = dueDate;
		this.status = LoanStatus.BORROWED;
	}

	public Long getId() {
		return id;
	}

	public Member getMember() {
		return member;
	}

	public Book getBook() {
		return book;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}
}

