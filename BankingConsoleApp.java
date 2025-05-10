import java.io.*;
import java.util.*;

public class BankingConsoleApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, BankAccount> accounts = new HashMap<>();
    private static final String FILE_NAME = "accounts.dat";

    public static void main(String[] args) {
        System.out.println("Welcome to the Banking Console App!");
        loadAccounts();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Account");
            System.out.println("2. Access Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    accessAccount();
                    break;
                case 3:
                    saveAccounts();
                    System.out.println("Thank you for using the Banking Console App!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        String accountHolder = scanner.nextLine();
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account with this number already exists.");
        } else {
            BankAccount account = new BankAccount(accountHolder, accountNumber);
            accounts.put(accountNumber, account);
            System.out.println("Account created successfully!");
        }
    }

    private static void accessAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        while (true) {
            System.out.println("\nAccount Menu for " + account.getAccountHolder() + " (" + account.getAccountNumber() + "):");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    account.viewBalance();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 4:
                    account.viewTransactionHistory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(accounts);
            System.out.println("Accounts data saved.");
        } catch (IOException e) {
            System.out.println("Error saving accounts data.");
            e.printStackTrace();
        }
    }

    private static void loadAccounts() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = in.readObject();
            if (obj instanceof Map) {
                accounts = (Map<String, BankAccount>) obj;
                System.out.println("Accounts data loaded.");
            } else {
                System.out.println("Data format is incorrect.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting with a fresh state.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts data.");
            e.printStackTrace();
        }
    }
}
