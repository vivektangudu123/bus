package com.example.bus;

import com.example.bus.Bus.BusService;
import com.example.bus.User.AdminController;
import com.example.bus.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BusApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private BusService busService;
	@Autowired
	private AdminController adminService;

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

		if (role == 1) { // User role
			int loggedUserId = -1;
			while (true) {
				System.out.println("Welcome, User! What would you like to do?");
				if (loggedUserId == -1) {
					System.out.println("1. Register");
					System.out.println("2. Login");
				} else {
					System.out.println("3. Book a bus seat");
					System.out.println("4. View Bus seat plan");
					System.out.println("5. Cancel a booking");
					System.out.println("6. Logout");
				}
				System.out.println("7. Exit");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character

				switch (choice) {
					case 1:
						loggedUserId = userService.registerUser();
						break;
					case 2:
						loggedUserId = userService.loginUser();
						break;
					case 3:
						if (loggedUserId != -1) {
							userService.bookBusSeat(userService.findByUserId(loggedUserId));
						} else {
							System.out.println("You must log in first.");
						}
						break;
					case 4:
						userService.viewBuses();
						break;
					case 5:
						if (loggedUserId != -1) {
							userService.cancelBusSeat(userService.findByUserId(loggedUserId));
						} else {
							System.out.println("You must log in first.");
						}
						break;
					case 6:
						loggedUserId = -1; // Log out the user
						System.out.println("You have been logged out.");
						break;
					case 7:
						System.out.println("Exiting...");
						return;
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
				System.out.println("4. Exit");

				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character

				if (!isLoggedIn) {
					if (choice == 0) {
						isLoggedIn = adminService.login();
					} else {
						System.out.println("You must log in first.");
					}
				} else {
					switch (choice) {
						case 1:
							busService.addBus();
							break;
						case 2:
//							adminService.modifyBus();
							break;
						case 3:
							isLoggedIn = false;
							System.out.println("You have been logged out.");
							break;
						case 4:
							System.out.println("Exiting the application. Goodbye!");
							return;
						default:
							System.out.println("Invalid choice.");
					}
				}
			}
		}
	}
}
