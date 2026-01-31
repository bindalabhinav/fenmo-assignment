package com.fenmo.expense_tracker.service;



import com.fenmo.expense_tracker.dto.CreateExpenseRequest;
import com.fenmo.expense_tracker.model.Expense;
import com.fenmo.expense_tracker.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Expense createExpense(CreateExpenseRequest request) {

        return repository.findByRequestId(request.getRequestId())
                .orElseGet(() -> {
                    Expense expense = new Expense();
                    expense.setAmount(request.getAmount());
                    expense.setCategory(request.getCategory());
                    expense.setDescription(request.getDescription());
                    expense.setDate(request.getDate());
                    expense.setRequestId(request.getRequestId());
                    return repository.save(expense);
                });
    }

    public List<Expense> getExpenses(String category, boolean sortByDateDesc) {

        if (category != null && !category.isBlank()) {
            return repository.findByCategoryOrderByDateDesc(category);
        }

        if (sortByDateDesc) {
            return repository.findAllByOrderByDateDesc();
        }

        return repository.findAll();
    }
}
