package controller;

import business.Flowers;
import business.Orders;
import java.text.ParseException;
import ui.Menu;
import util.Validation;

public class FlowerStoreSystem {

    public static void main(String[] args) throws ParseException, Exception {
        Menu menu = new Menu("Flower Store System");
        menu.addNewOption("1. Add a flower");
        menu.addNewOption("2. Find a flower");
        menu.addNewOption("3. Update a flower");
        menu.addNewOption("4. Delete a flower");
        menu.addNewOption("5. Add an order");
        menu.addNewOption("6. Display orders");
        menu.addNewOption("7. Sort orders");
        menu.addNewOption("8. Save data");
        menu.addNewOption("9. Load data");
        menu.addNewOption("10. Quit");
        
        Flowers flowers = new Flowers();
        Orders orders = new Orders();
        
        int choice;
        boolean flag = false;
        do {
            menu.printMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:                    
                    flowers.addAFlower();
                    break;
                case 2:
                    flowers.findAFlower();
                    break;
                case 3:
                    flowers.updateAFlower();
                    break;
                case 4:
                    flowers.deleteAFLower(orders);
                    break;
                case 5:
                    orders.addAnOrder(flowers);
                    break;
                case 6:
                    orders.displayOrders(flowers);
                    break;
                case 7:
                    orders.sortOrders(flowers);
                    break;
                case 8:
                    flowers.saveData();
                    orders.saveData();
                    break;
                case 9:
                    flowers.loadData();
                    orders.loadData();
                    break;
                case 10:
                    System.out.println("Do you want to exit the program? (1: yes; 2: no)");
                    System.out.print("Your choice: ");
                    int confirm = Validation.getChoice(1, 2);
                    if (confirm == 1) {
                        flag = true;
                        System.out.println("Do you want to save data? (1: yes; 2: no)");
                        System.out.print("Your choice: ");
                        confirm = Validation.getChoice(1, 2);
                        if (confirm == 1) {
                            flowers.saveData();
                            orders.saveData();
                        }
                        System.out.println("Bye bye, see you next time!");
                    }
                    break;
            }
            } while (!flag);
    }
}
