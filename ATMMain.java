import java.util.*;

class UserAccount {
    private final int pin;
    private double balance;
    private String lastTransaction;

    public UserAccount(int pin, double balance) {
        this.pin = pin;
        this.balance = balance;
        this.lastTransaction = "No transactions yet.";
    }

    public int getPin() {
        return pin;
    }

    public boolean verifyPin(int inputPin) {
        return this.pin == inputPin;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        balance += amount;
        lastTransaction = "Deposited: Rs." + amount;
        System.out.println("Successfully deposited Rs." + amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            lastTransaction = "Withdrew: Rs." + amount;
            System.out.println("Successfully withdrew Rs." + amount);
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getLastTransaction() {
        return lastTransaction;
    }
}

class AIHelpBox {
    public void provideHelp(String question) {
        String lower = question.toLowerCase().trim();
        if (lower.contains("balance")) {
            System.out.println("Use the 'Check Balance' option to view your account balance.");
        } else if (lower.contains("deposit")) {
            System.out.println("Deposit money using the 'Deposit Cash' option.");
        } else if (lower.contains("withdraw")) {
            System.out.println("Withdraw cash with the 'Withdraw Cash' option.");
        } else if (lower.contains("pin")) {
            System.out.println("Your PIN keeps your account secure. Never share it with anyone.");
        } else if (lower.contains("account")) {
            System.out.println("Create, access, or manage your account easily through this ATM system.");
        } else {
            System.out.println("Sorry, I can only answer questions about balance, deposit, withdraw, PIN, or account.");
        }
    }
}

public class ATMMain {
    private static final Map<Integer, UserAccount> accounts = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        accounts.put(1234, new UserAccount(1234, 10000.0)); // demo account

        System.out.println("=== Welcome to the Smart ATM Machine ===");

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int option = getIntInput();

            switch (option) {
                case 1 -> login();
                case 2 -> createAccount();
                case 3 -> {
                    System.out.println("Thank you for using Smart ATM. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter your 4-digit PIN: ");
        int inputPin = getIntInput();

        UserAccount user = accounts.get(inputPin);
        if (user == null) {
            System.out.println("Account not found. Please create an account first.");
            return;
        }

        System.out.println("Login successful!");
        showUserMenu(user);
    }

    private static void showUserMenu(UserAccount user) {
        boolean exit = false;
        while (!exit) {
            System.out.println("--- ATM Main Menu ---");
            System.out.println("1. Withdraw Cash");
            System.out.println("2. Deposit Cash");
            System.out.println("3. Check Balance");
            System.out.println("4. View Last Transaction");
            System.out.println("5. AI Help Box");
            System.out.println("6. Logout");
            System.out.print("Select an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter amount to withdraw: ");
                    user.withdraw(getDoubleInput());
                }
                case 2 -> {
                    System.out.print("Enter amount to deposit: ");
                    user.deposit(getDoubleInput());
                }
                case 3 -> System.out.println("Your current balance: Rs." + user.getBalance());
                case 4 -> System.out.println("Last Transaction: " + user.getLastTransaction());
                case 5 -> startAIHelpBox();
                case 6 -> {
                    exit = true;
                    System.out.println("Logged out successfully.");
                }
                default -> System.out.println("Invalid selection. Try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter a new 4-digit PIN: ");
        int newPin = getIntInput();

        if (accounts.containsKey(newPin)) {
            System.out.println("This PIN is already taken. Try another.");
            return;
        }

        System.out.print("Enter your initial balance: ");
        double initDeposit = getDoubleInput();

        accounts.put(newPin, new UserAccount(newPin, initDeposit));
        System.out.println("Account created successfully with PIN: " + newPin);
    }

    private static void startAIHelpBox() {
        AIHelpBox helpBox = new AIHelpBox();
        System.out.println("=== ATM AI Help Box ===");
        System.out.println("Ask me anything (type 'exit' to leave):");
        sc.nextLine(); // consume pending newline
        while (true) {
            System.out.print("You: ");
            String question = sc.nextLine();
            if (question.equalsIgnoreCase("exit")) break;
            helpBox.provideHelp(question);
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                sc.nextLine();
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid amount: ");
                sc.nextLine();
            }
        }
    }
}