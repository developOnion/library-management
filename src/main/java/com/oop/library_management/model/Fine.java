package com.oop.library_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "fines")
public class Fine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "loan_id", nullable = false)
	@NotNull(message = "Loan is required")
	private Loan loan;

	// total fine amount, calculated based on number of days overdue and fine rate
	@Column(nullable = false, precision = 10, scale = 2)
	@NotNull(message = "Fine amount is required")
	@Min(value = 0, message = "Fine amount cannot be negative")
	private BigDecimal amount;

	@Column(nullable = false)
	private boolean paid = false;

	protected Fine() {
		// Required by JPA
	}

	public Fine(Loan loan, BigDecimal amount) {
		this.loan = loan;
		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
	}

	public Long getId() { return id; }
	public Loan getLoan() { return loan; }
	public BigDecimal getAmount() { return amount; }
	public boolean isPaid() { return paid; }

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
	}

	public void setPaid(boolean paid) { this.paid = paid; }
}

