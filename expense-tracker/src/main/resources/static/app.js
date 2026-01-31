const API_URL = "/expenses";

const form = document.getElementById("expense-form");
const tableBody = document.getElementById("expenses-table");
const totalEl = document.getElementById("total");
const filterInput = document.getElementById("filter-category");
const sortBtn = document.getElementById("sort-btn");

let sortDesc = false;

function generateRequestId() {
  return crypto.randomUUID();
}

async function fetchExpenses() {
  let url = API_URL;

  const category = filterInput.value;
  const params = [];

  if (category) params.push(`category=${encodeURIComponent(category)}`);
  if (sortDesc) params.push(`sort=date_desc`);

  if (params.length) url += "?" + params.join("&");

  const res = await fetch(url);
  const data = await res.json();
  renderExpenses(data);
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

  const expense = {
    amount: Number(document.getElementById("amount").value),
    category: document.getElementById("category").value,
    description: document.getElementById("description").value,
    date: document.getElementById("date").value,
    requestId: generateRequestId()
  };

  await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(expense)
  });

  form.reset();
  fetchExpenses();
});

filterInput.addEventListener("input", fetchExpenses);

sortBtn.addEventListener("click", () => {
  sortDesc = !sortDesc;
  fetchExpenses();
});

fetchExpenses();
