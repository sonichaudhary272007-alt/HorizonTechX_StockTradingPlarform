package StockTradingPlatform;
		import java.util.*;

		// ---------------- STOCK CLASS ----------------
		class Stock {
		    private String symbol;
		    private double price;

		    public Stock(String symbol, double price) {
		        this.symbol = symbol;
		        this.price = price;
		    }

		    public String getSymbol() {
		        return symbol;
		    }

		    public double getPrice() {
		        return price;
		    }

		    public void updatePrice() {
		        // Randomly increase/decrease price
		        double change = (Math.random() * 20) - 10;
		        price += change;

		        if (price < 50) {
		            price = 50;
		        }
		    }
		}

		// ---------------- TRANSACTION CLASS ----------------
		class Transaction {
		    private String type;
		    private String stockName;
		    private int quantity;
		    private double totalAmount;

		    public Transaction(String type, String stockName, int quantity, double totalAmount) {
		        this.type = type;
		        this.stockName = stockName;
		        this.quantity = quantity;
		        this.totalAmount = totalAmount;
		    }

		    @Override
		    public String toString() {
		        return type + " | " + stockName + " | Qty: " + quantity +
		                " | Amount: $" + totalAmount;
		    }
		}

		// ---------------- PORTFOLIO CLASS ----------------
		class Portfolio {
		    private HashMap<String, Integer> holdings;

		    public Portfolio() {
		        holdings = new HashMap<>();
		    }

		    public void buyStock(String stockName, int quantity) {
		        holdings.put(stockName,
		                holdings.getOrDefault(stockName, 0) + quantity);
		    }

		    public void sellStock(String stockName, int quantity) {
		        if (holdings.containsKey(stockName)) {
		            int currentQty = holdings.get(stockName);

		            if (currentQty <= quantity) {
		                holdings.remove(stockName);
		            } else {
		                holdings.put(stockName, currentQty - quantity);
		            }
		        }
		    }

		    public int getQuantity(String stockName) {
		        return holdings.getOrDefault(stockName, 0);
		    }

		    public void showPortfolio() {
		        System.out.println("\n------ PORTFOLIO ------");

		        if (holdings.isEmpty()) {
		            System.out.println("No stocks purchased.");
		            return;
		        }

		        for (String stock : holdings.keySet()) {
		            System.out.println(stock + " -> " + holdings.get(stock) + " shares");
		        }
		    }
		}

		// ---------------- USER CLASS ----------------
		class User {
		    private String name;
		    private double balance;
		    private Portfolio portfolio;
		    private ArrayList<Transaction> transactions;

		    public User(String name, double balance) {
		        this.name = name;
		        this.balance = balance;
		        portfolio = new Portfolio();
		        transactions = new ArrayList<>();
		    }

		    public double getBalance() {
		        return balance;
		    }

		    public void buyStock(Stock stock, int quantity) {
		        double totalCost = stock.getPrice() * quantity;

		        if (totalCost > balance) {
		            System.out.println("Insufficient balance!");
		            return;
		        }

		        balance -= totalCost;
		        portfolio.buyStock(stock.getSymbol(), quantity);

		        transactions.add(new Transaction(
		                "BUY",
		                stock.getSymbol(),
		                quantity,
		                totalCost
		        ));

		        System.out.println("Stock purchased successfully!");
		    }

		    public void sellStock(Stock stock, int quantity) {
		        int ownedQty = portfolio.getQuantity(stock.getSymbol());

		        if (ownedQty < quantity) {
		            System.out.println("Not enough shares to sell!");
		            return;
		        }

		        double totalAmount = stock.getPrice() * quantity;

		        balance += totalAmount;
		        portfolio.sellStock(stock.getSymbol(), quantity);

		        transactions.add(new Transaction(
		                "SELL",
		                stock.getSymbol(),
		                quantity,
		                totalAmount
		        ));

		        System.out.println("Stock sold successfully!");
		    }

		    public void showPortfolio() {
		        portfolio.showPortfolio();
		    }

		    public void showTransactions() {
		        System.out.println("\n------ TRANSACTION HISTORY ------");

		        if (transactions.isEmpty()) {
		            System.out.println("No transactions yet.");
		            return;
		        }

		        for (Transaction t : transactions) {
		            System.out.println(t);
		        }
		    }
		}

		// ---------------- MAIN CLASS ----------------
		public class StockTradingPlatform {

		    public static void main(String[] args) {

		        Scanner sc = new Scanner(System.in);

		        // Market Stocks
		        ArrayList<Stock> market = new ArrayList<>();

		        market.add(new Stock("APPLE", 200));
		        market.add(new Stock("TESLA", 180));
		        market.add(new Stock("AMAZON", 150));
		        market.add(new Stock("TCS", 120));
		        market.add(new Stock("RELIANCE", 170));

		        // Create User
		        User user = new User("Soni", 10000);

		        int choice;

		        do {
		            System.out.println("\n========== STOCK TRADING PLATFORM ==========");
		            System.out.println("Balance: $" + user.getBalance());

		            System.out.println("\n------ MARKET DATA ------");

		            for (int i = 0; i < market.size(); i++) {
		                market.get(i).updatePrice();

		                System.out.println(
		                        (i + 1) + ". " +
		                                market.get(i).getSymbol() +
		                                " - $" +
		                                String.format("%.2f", market.get(i).getPrice())
		                );
		            }

		            System.out.println("\n1. Buy Stock");
		            System.out.println("2. Sell Stock");
		            System.out.println("3. View Portfolio");
		            System.out.println("4. Transaction History");
		            System.out.println("5. Exit");

		            System.out.print("Enter choice: ");
		            choice = sc.nextInt();

		            switch (choice) {

		                case 1:
		                    System.out.print("Select stock number: ");
		                    int buyChoice = sc.nextInt();

		                    System.out.print("Enter quantity: ");
		                    int buyQty = sc.nextInt();

		                    user.buyStock(market.get(buyChoice - 1), buyQty);
		                    break;

		                case 2:
		                    System.out.print("Select stock number: ");
		                    int sellChoice = sc.nextInt();

		                    System.out.print("Enter quantity: ");
		                    int sellQty = sc.nextInt();

		                    user.sellStock(market.get(sellChoice - 1), sellQty);
		                    break;

		                case 3:
		                    user.showPortfolio();
		                    break;

		                case 4:
		                    user.showTransactions();
		                    break;

		                case 5:
		                    System.out.println("Thank You for using platform!");
		                    break;

		                default:
		                    System.out.println("Invalid choice!");
		            }

		        } while (choice != 5);

		        sc.close();
		    }
		}

