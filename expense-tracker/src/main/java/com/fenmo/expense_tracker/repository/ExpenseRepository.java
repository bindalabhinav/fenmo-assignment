package com.fenmo.expense_tracker.repository;

import com.fenmo.expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByRequestId(String requestId);

    List<Expense> findByCategoryOrderByDateDesc(String category);

    List<Expense> findAllByOrderByDateDesc();
}
