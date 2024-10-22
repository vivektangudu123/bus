package com.example.bus;

import com.example.bus.Bus.BusController;
import com.example.bus.User.AdminController;
import com.example.bus.User.UserController;
import com.example.bus.authentication.AuthenticationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BusApplication implements CommandLineRunner {

	@Autowired
	private final UserController userController;

	@Autowired
	private final AuthenticationController authenticationController;

	@Autowired
	private final BusController busController;

	@Autowired
	private final AdminController adminController;

	public BusApplication(UserController userController, AuthenticationController authenticationController, BusController busController, AdminController adminController) {
		this.userController = userController;
		this.authenticationController = authenticationController;
		this.busController = busController;
        this.adminController = adminController;
    }

	public static void main(String[] args) {
		SpringApplication.run(BusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to the Bus Booking System.");
		System.out.print("Are you a user or an admin? (Enter 1 for User or 2 for Admin): ");
		int role = scanner.nextInt();

		if (role != 1 && role != 2) {
			System.out.println("Invalid role. Exiting...");
			return;
		}

		// Consume newline character left by nextInt()
		scanner.nextLine();

		// Actions based on role
		if (role == 1) { // User role
			int loggedUserId = -1;
			while (true) {
				System.out.println("Welcome, User! What would you like to do?");
				if (loggedUserId == -1) {
					System.out.println("1. Register");
					System.out.println("2. Login");
				} else {
					System.out.println("3. Book a bus seat");
					System.out.println("4. Cancel a booking");
					System.out.println("5. Logout");
				}
				System.out.println("6. Exit"); // Add exit option

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character

				switch (choice) {
					case 1:
						loggedUserId = userController.registerUser();
						break;
					case 2:
						loggedUserId = userController.loginUser();
						break;
					case 3:
						if (loggedUserId != -1) {
							userController.bookBusSeat();
						} else {
							System.out.println("You must log in first.");
						}
						break;
					case 4:
						if (loggedUserId != -1) {
							userController.cancelBusSeat();
						} else {
							System.out.println("You must log in first.");
						}
						break;
					case 5:
						loggedUserId = -1; // Log out the user
						System.out.println("You have been logged out.");
						break;
					case 6: // Exit option for users
						System.out.println("Exiting the application. Goodbye!");
						scanner.close();
						return; // Exit the method to stop the program
					default:
						System.out.println("Invalid choice.");
				}
			}
		} else if (role == 2) { // Admin role
			boolean isLoggedIn = false;
			while (true) {
				System.out.println("Welcome, Admin! What would you like to do?");
				if (!isLoggedIn) {
					System.out.println("0. Login");
				} else {
					System.out.println("1. Add a bus");
					System.out.println("2. Modify bus details");
					System.out.println("3. Logout");
				}
				System.out.println("4. Exit"); // Add exit option for admin

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character

				if (!isLoggedIn) {
					if (choice == 0) {
						isLoggedIn = adminController.login(); // Assume this method exists
					} else {
						System.out.println("You must log in first.");
					}
				} else {
					switch (choice) {
						case 1:
							adminController.addBus();
							break;
						case 2:
							adminController.modifyBus();
							break;
						case 3:
							isLoggedIn = false; // Log out the admin
							System.out.println("You have been logged out.");
							break;
						case 4: // Exit option for admins
							System.out.println("Exiting the application. Goodbye!");
							scanner.close();
							return; // Exit the method to stop the program
						default:
							System.out.println("Invalid choice.");
					}
				}
			}
		}
	}

}
