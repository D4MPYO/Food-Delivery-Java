import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class KathFoodDelivery {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<CustomerOrder> orders = new ArrayList<>();

        while (true) {
            System.out.println(" ");
            System.out.println("\nWELCOME TO KATHRYN'S FOOD DELIVERY");
            System.out.println("\nChoose an option:");
            System.out.println("1. Place an order");
            System.out.println("2. Admin Panel");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int option = 0;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
                continue;
            }

            switch (option) {
                case 1:
                    placeOrder(scanner, orders);
                    break;
                case 2:
                    adminPanel(scanner, orders);
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public static void placeOrder(Scanner scanner, List<CustomerOrder> orders) {
        System.out.println("\nENTER YOUR DETAILS");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        System.out.print("Enter your contact number (e.g., 09123456789): ");
        String contact = getContact(scanner);

        String restaurant;
        while (true) {
            System.out.println("Choose a restaurant:");
            System.out.println("1. Jollibee");
            System.out.println("2. KFC");
            System.out.println("3. Shakey's");
            System.out.print("Enter your choice: ");
            int restaurantChoice = 0;
            try {
                restaurantChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (restaurantChoice) {
                case 1:
                    restaurant = "Jollibee";
                    break;
                case 2:
                    restaurant = "KFC";
                    break;
                case 3:
                    restaurant = "Shakey's";
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    continue;
            }
            break;
        }

        displayMenu(restaurant);

        System.out.print("\nEnter your order (comma-separated item numbers): ");
        String orderInput = scanner.nextLine();
        String[] orderItems = orderInput.split(",");
        StringBuilder selectedItems = new StringBuilder();
        double totalCost = 0.0;

        for (String item : orderItems) {
            int itemNumber = Integer.parseInt(item.trim());
            String itemName = getItemName(restaurant, itemNumber);
            double itemCost = getItemCost(restaurant, item.trim());
            totalCost += itemCost;
            selectedItems.append(itemName).append(" - Php ").append(itemCost).append("\n");
        }

        System.out.println("\nYour Order (to " + restaurant + "):");
        System.out.println(selectedItems.toString());

        double shippingCost = 0.0;
        switch (restaurant.toLowerCase()) {
            case "jollibee":
                shippingCost = 50.0;
                break;
            case "kfc":
                shippingCost = 55.0;
                break;
            case "shakey's":
                shippingCost = 60.0;
                break;
            default:
                System.out.println("Shipping cost not available for selected restaurant.");
        }

        System.out.println("Shipping Cost: Php " + shippingCost);

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00"); // Define decimal format

        double totalPrice = totalCost + shippingCost;
        String formattedTotalPrice = decimalFormat.format(totalPrice); // Format total price with comma

        System.out.println("Total Price: Php " + formattedTotalPrice); // Print formatted total price

        // Prompt user to confirm order
        System.out.println("\nDo you want to Place your Order?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter your choice: ");
        int confirmChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (confirmChoice == 1) {
            CustomerOrder customerOrder = new CustomerOrder(name, address, contact, restaurant, orderInput, totalPrice);
            orders.add(customerOrder);
            System.out.println("\nOrder placed successfully!");
        } else {
            System.out.println("\nOrder not placed. Returning to main menu.");
        }
    }

    public static String getContact(Scanner scanner) {
        while (true) {
            String contact = scanner.nextLine();
            if (contact.matches("\\d{11}")) {
                return contact;
            } else {
                System.out.println("Invalid contact number. Please enter a valid 11-digit number.");
            }
        }
    }

    public static void viewOrders(List<CustomerOrder> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }

        DecimalFormat df = new DecimalFormat("#,###.0");

        System.out.println("\nKathryn's Food Delivery - Orders");
        for (int i = 0; i < orders.size(); i++) {
            CustomerOrder order = orders.get(i);
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│                                                         │");
            System.out.println("│                       Customer " + (i + 1) + ":" + "                       " + "│");
            System.out.println("│─────────────────────────────────────────────────────────│");
            System.out.println("│   Name: " + order.getName());
            System.out.println("│   Address: " + order.getAddress());
            System.out.println("│   Contact: " + order.getContact());
            System.out.println("│   Restaurant/Branch: " + order.getRestaurant());
            System.out.println("│   Order: " + order.getOrder());
            System.out.println("│   Total Cost: Php" + df.format(order.getTotalCost()));
            System.out.println("└─────────────────────────────────────────────────────────┘");
            System.out.println();
        }
    }

    public static void adminPanel(Scanner scanner, List<CustomerOrder> orders) {
        System.out.println("\nADMIN PANEL : Strictly for Admins only");
        System.out.println("Enter admin password:");
        String password = scanner.nextLine();

        if (password.equals("admin") || password.equals("ADMIN")) {
            while (true) {
                System.out.println("1. View all orders");
                System.out.println("2. Delete an order");
                System.out.println("3. Return to main menu");
                int adminOption = scanner.nextInt();
                scanner.nextLine();
                switch (adminOption) {
                    case 1:
                        viewOrders(orders);
                        break;
                    case 2:
                        deleteOrder(scanner, orders);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("Incorrect password. Access denied.");
        }
    }

    public static void deleteOrder(Scanner scanner, List<CustomerOrder> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }

        System.out.println("\nDELETE ORDER");
        System.out.print("Enter the order number to delete: ");
        int orderNumber = scanner.nextInt();
        scanner.nextLine();

        if (orderNumber >= 1 && orderNumber <= orders.size()) {
            orders.remove(orderNumber - 1);
            System.out.println("Order deleted successfully.");
        } else {
            System.out.println("Invalid order number.");
        }
    }

    public static void displayMenu(String restaurant) {
        switch (restaurant.toLowerCase()) {
            case "jollibee":
                System.out.println("\nMenu for Jollibee:");
                System.out.println("\nBURGERS");
                System.out.println("1. Yum Burger - Php 40");
                System.out.println("2. Cheesy Yumburger - Php 69");
                System.out.println("\nCHICKEN JOY");
                System.out.println("3. 1pc Chickenjoy - Php 82");
                System.out.println("4. 4pcs Chickenjoy Family Box Solo - Php 326");
                System.out.println("5. 1pc Chickenjoy w/Coke Float - Php143");
                System.out.println("\nJOLLY SPAGHETTI");
                System.out.println("6. Jolly Spaghetti - Php 60");
                System.out.println("\nBURGER STEAK");
                System.out.println("7. 1pc Burger Steak - Php 60");
                System.out.println("\nBEVERAGES");
                System.out.println("8. Soda Float - Php 57");
                System.out.println("9. Pineapple Juice - Php 64");
                System.out.println("\nDESSERT");
                System.out.println("10. Peach Mango Pie - Php 48");
                break;
            case "kfc":
                System.out.println("\nMenu for KFC:");
                System.out.println("\nSNACKS");
                System.out.println("1.Large Shot Combo - Php 190");
                System.out.println("2. Shots Combo - Php 130");
                System.out.println("\nCHICKEN MEALS");
                System.out.println("3. 1pc Chicken meal Ala Carte - Php 105");
                System.out.println("4. 1pc Chicken Meal with Mashed Potato - Php 175");
                System.out.println("5. 1pc Chicken Spaghetti Meal - Php 195");
                System.out.println("\nSANDWICHES");
                System.out.println("6. KFC Chizza Ala Carte  -  Php 210");
                System.out.println("\nFIXINS AND EXTRAS");
                System.out.println("7. Mashed Potato - Php 60");
                System.out.println("8. Buttered Corn - Php 60");
                System.out.println("\nFULLY LOAD MEALS");
                System.out.println("9. KFC Chizza Fully Loaded - Php 345");
                System.out.println("\n DRINKS");
                System.out.println("10. Unsweetened Icead Tea - Php 80");
                break;
            case "shakey's":
                System.out.println("\nMenu for Shakey's:");
                System.out.println("\nPASTA");
                System.out.println("1. Spinach Roll Ups - Php 334");
                System.out.println("2. Carbonara Supreme - Php 299");
                System.out.println("\nPIZZAS");
                System.out.println("3. Hawaiian Deligh Pizza Americana - Php 1,179");
                System.out.println("\nCHICKEN 'N MOJOS");
                System.out.println("4. Basket of Flavored Mojos - Php 429");
                System.out.println("5. Bucket of Flavored Mojos Supreme - Php 585");
                System.out.println("6. Mojos Dip - Php 199");
                System.out.println("\nSOUPS and SALAD");
                System.out.println("7. Chicken N Corn Soup - Php 135");
                System.out.println("8. Greek Salad - Php 279");
                System.out.println("\nDRINKS");
                System.out.println("9. Bottled House Blend Iced Tea - Php 130");
                System.out.println("10. Mineral Water - Php 70");
                break;
            default:
                System.out.println("Menu not available for selected restaurant.");
        }
    }

    public static double getItemCost(String restaurant, String item) {
        switch (restaurant.toLowerCase()) {
            case "jollibee":
                switch (item) {
                    case "1":
                        return 40;
                    case "2":
                        return 69;
                    case "3":
                        return 82;
                    case "4":
                        return 326;
                    case "5":
                        return 143;
                    case "6":
                        return 60;
                    case "7":
                        return 60;
                    case "8":
                        return 57;
                    case "9":
                        return 64;
                    case "10":
                        return 48;

                    default:
                        System.out.println("Invalid item number: " + item);
                        return 0.0;
                }
            case "kfc":
                switch (item) {
                    case "1":
                        return 190;
                    case "2":
                        return 130;
                    case "3":
                        return 105;
                    case "4":
                        return 175;
                    case "5":
                        return 195;
                    case "6":
                        return 210;
                    case "7":
                        return 60;
                    case "8":
                        return 60;
                    case "9":
                        return 345;
                    case "10":
                        return 80;
                    default:
                        System.out.println("Invalid item number: " + item);
                        return 0.0;
                }
            case "shakey's":
                switch (item) {
                    case "1":
                        return 334;
                    case "2":
                        return 299;
                    case "3":
                        return 1179;
                    case "4":
                        return 429;
                    case "5":
                        return 585;
                    case "6":
                        return 199;
                    case "7":
                        return 135;
                    case "8":
                        return 279;
                    case "9":
                        return 130;
                    case "10":
                        return 70;

                    default:
                        System.out.println("Invalid item number: " + item);
                        return 0.0;
                }
            default:
                System.out.println("Menu not available for selected restaurant.");
                return 0.0;
        }
    }

    public static String getItemName(String restaurant, int itemNumber) {
        switch (restaurant.toLowerCase()) {
            case "jollibee":
                switch (itemNumber) {
                    case 1:
                        return "Yum Burger";
                    case 2:
                        return "Cheesy Yumburger";
                    case 3:
                        return "1pc Chickenjoy";
                    case 4:
                        return "4pcs Chickenjoy Family Box Solo";
                    case 5:
                        return "1pc Chickenjoy w/Coke Float";
                    case 6:
                        return "Jolly Spaghetti";
                    case 7:
                        return "1pc Burger Steak";
                    case 8:
                        return "Soda Float";
                    case 9:
                        return "Pineapple Juice";
                    case 10:
                        return "Peach Mango Pie";
                    default:
                        return "Invalid item number";
                }
            case "kfc":
                switch (itemNumber) {
                    case 1:
                        return "Large Shot Combo";
                    case 2:
                        return "Shots Combo";
                    case 3:
                        return "1pc Chicken meal Ala Carte";
                    case 4:
                        return "1pc Chicken Meal with Mashed Potato";
                    case 5:
                        return "1pc Chicken Spaghetti Meal";
                    case 6:
                        return "KFC Chizza Ala Carte";
                    case 7:
                        return "Mashed Potato";
                    case 8:
                        return "Buttered Corn";
                    case 9:
                        return "KFC Chizza Fully Loaded";
                    case 10:
                        return "Unsweetened Icead Tea";
                    default:
                        return "Invalid item number";
                }
            case "shakey's":
                switch (itemNumber) {
                    case 1:
                        return "Spinach Roll Ups";
                    case 2:
                        return "Carbonara Supreme";
                    case 3:
                        return "Hawaiian Deligh Pizza Americana";
                    case 4:
                        return "Basket of Flavored Mojos";
                    case 5:
                        return "Bucket of Flavored Mojos Supreme";
                    case 6:
                        return "Mojos Dip";
                    case 7:
                        return "Chicken N Corn Soup";
                    case 8:
                        return "Greek Salad";
                    case 9:
                        return "Bottled House Blend Iced Tea";
                    case 10:
                        return "Mineral Water";
                    default:
                        return "Invalid item number";
                }
            default:
                return "Menu not available for selected restaurant.";
        }
    }

    static class CustomerOrder {
        private String name;
        private String address;
        private String contact;
        private String restaurant;
        private String order;
        private double totalCost;

        public CustomerOrder(String name, String address, String contact, String restaurant, String order,
                double totalCost) {
            this.name = name;
            this.address = address;
            this.contact = contact;
            this.restaurant = restaurant;
            this.order = order;
            this.totalCost = totalCost;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getContact() {
            return contact;
        }

        public String getRestaurant() {
            return restaurant;
        }

        public String getOrder() {
            return order;
        }

        public double getTotalCost() {
            return totalCost;
        }
    }
}
