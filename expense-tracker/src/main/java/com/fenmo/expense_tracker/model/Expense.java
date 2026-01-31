package com.fenmo.expense_tracker.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;


    @Entity
    @Table(
            name = "expenses",
            indexes = {
                    @Index(name = "idx_category", columnList = "category"),
                    @Index(name = "idx_date", columnList = "date"),
                    @Index(name = "idx_request_id", columnList = "requestId", unique = true)
            }
    )
    public class Expense {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, precision = 19, scale = 2)
        private BigDecimal amount;

        @Column(nullable = false)
        private String category;

        @Column(length = 255)
        private String description;

        @Column(nullable = false)
        private LocalDate date;

        @Column(nullable = false, updatable = false)
        private Instant createdAt;

        @Column(nullable = false, unique = true)
        private String requestId;

        @PrePersist
        public void prePersist() {
            this.createdAt = Instant.now();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }



    }

