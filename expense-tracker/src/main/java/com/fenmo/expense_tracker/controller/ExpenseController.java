package com.fenmo.expense_tracker.controller;

import com.fenmo.expense_tracker.dto.CreateExpenseRequest;
import com.fenmo.expense_tracker.model.Expense;
import com.fenmo.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense createExpense(@Valid @RequestBody CreateExpenseRequest request) {
        return service.createExpense(request);
    }


    @GetMapping
    public List<Expense> getExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false, name = "sort") String sort
    ) {
        boolean sortByDateDesc = "date_desc".equalsIgnoreCase(sort);
        return service.getExpenses(category, sortByDateDesc);
    }
}