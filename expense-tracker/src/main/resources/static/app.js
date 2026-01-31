const API_URL = "/expenses";

const form = document.getElementById("expense-form");
const tableBody = document.getElementById("expenses-table");
const totalEl = document.getElementById("total");
const filterInput = document.getElementById("filter-category");
const sortBtn = document.getElementById("sort-btn");
const statusEl = document.getElementById("status");
const submitBtn = document.getElementById("submit-btn");


let sortDesc = false;

function generateRequestId() {
  return crypto.randomUUID();
}

async function fetchExpenses() {
  try {
    let url = API_URL;

    const category = filterInput.value;
    const params = [];

    if (category) params.push(`category=${encodeURIComponent(category)}`);
    if (sortDesc) params.push(`sort=date_desc`);

    if (params.length) url += "?" + params.join("&");

    const res = await fetch(url);
    if (!res.ok) {
      throw new Error("Failed to load expenses");
    }

    const data = await res.json();
    renderExpenses(data);
  } catch (err) {
    statusEl.textContent = "Unable to load expenses.";
  }
}


function renderExpenses(expenses) {
  tableBody.innerHTML = "";
  let total = 0;

  expenses.forEach(e => {
    total += Number(e.amount);

    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${e.date}</td>
      <td>${e.category}</td>
      <td>${e.description || ""}</td>
      <td>${e.amount}</td>
    `;
    tableBody.appendChild(row);
  });

  totalEl.textContent = `Total: ₹${total.toFixed(2)}`;
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  statusEl.textContent = "";
  submitBtn.disabled = true;
  submitBtn.textContent = "Saving...";

  const expense = {
    amount: Number(document.getElementById("amount").value),
    category: document.getElementById("category").value,
    description: document.getElementById("description").value,
    date: document.getElementById("date").value,
    requestId: generateRequestId()
  };

  try {
    const res = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(expense)
    });

    if (!res.ok) {
      throw new Error("Failed to save expense");
    }

    form.reset();
    await fetchExpenses();
  } catch (err) {
    statusEl.textContent = "Something went wrong. Please try again.";
  } finally {
    submitBtn.disabled = false;
    submitBtn.textContent = "Add";
  }
});

filterInput.addEventListener("input", fetchExpenses);

sortBtn.addEventListener("click", () => {
  sortDesc = !sortDesc;
  fetchExpenses();
});

fetchExpenses();
