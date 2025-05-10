let currentAccount = null;
let accounts = JSON.parse(localStorage.getItem('accounts')) || {};

function showCreateAccountForm() {
    document.getElementById('accountMenu').style.display = 'none';
    document.getElementById('createAccountForm').style.display = 'block';
}

function showAccessAccountForm() {
    document.getElementById('accountMenu').style.display = 'none';
    document.getElementById('accessAccountForm').style.display = 'block';
}

function goBack() {
    document.getElementById('createAccountForm').style.display = 'none';
    document.getElementById('accessAccountForm').style.display = 'none';
    document.getElementById('accountMenu').style.display = 'block';
}

function createAccount() {
    const accountHolder = document.getElementById('accountHolder').value;
    const accountNumber = document.getElementById('accountNumber').value;

    if (!accountHolder || !accountNumber) {
        alert("Please fill in all fields");
        return;
    }

    if (accounts[accountNumber]) {
        alert("Account with this number already exists!");
        return;
    }

    accounts[accountNumber] = {
        accountHolder: accountHolder,
        balance: 0,
        transactionHistory: ["Account created."]
    };

    localStorage.setItem('accounts', JSON.stringify(accounts));

    alert("Account created successfully!");
    goBack();
}

function accessAccount() {
    const accountNumber = document.getElementById('accessAccountNumber').value;

    if (!accounts[accountNumber]) {
        alert("Account not found!");
        return;
    }

    currentAccount = accounts[accountNumber];
    document.getElementById('accountInfo').innerHTML = `
        <p>Account Holder: ${currentAccount.accountHolder}</p>
        <p>Account Number: ${accountNumber}</p>
    `;

    document.getElementById('accountMenu').style.display = 'none';
    document.getElementById('accountDetails').style.display = 'block';
}

function viewBalance() {
    alert(`Current balance: $${currentAccount.balance}`);
}

function deposit() {
    const amount = parseFloat(prompt("Enter deposit amount:"));

    if (isNaN(amount) || amount <= 0) {
        alert("Invalid amount!");
        return;
    }

    currentAccount.balance += amount;
    currentAccount.transactionHistory.push(`Deposited: $${amount}`);
    localStorage.setItem('accounts', JSON.stringify(accounts));

    alert(`Deposited: $${amount}`);
}

function withdraw() {
    const amount = parseFloat(prompt("Enter withdrawal amount:"));

    if (isNaN(amount) || amount <= 0) {
        alert("Invalid amount!");
        return;
    }

    if (amount > currentAccount.balance) {
        alert("Insufficient funds!");
        return;
    }

    currentAccount.balance -= amount;
    currentAccount.transactionHistory.push(`Withdrew: $${amount}`);
    localStorage.setItem('accounts', JSON.stringify(accounts));

    alert(`Withdrew: $${amount}`);
}

function viewTransactionHistory() {
    alert("Transaction History:\n" + currentAccount.transactionHistory.join("\n"));
}

function logout() {
    currentAccount = null;
    document.getElementById('accountDetails').style.display = 'none';
    document.getElementById('accountMenu').style.display = 'block';
}

function exitApp() {
    alert("Thank you for using the Banking Console App!");
    window.close();
}
