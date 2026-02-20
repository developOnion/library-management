//package com.oop.library_management.cli;
//
//import com.oop.library_management.dto.UserDTO;
//import com.oop.library_management.exception.ValidationException;
//import com.oop.library_management.service.UserService;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Scanner;
//
//@Component
//public class LibraryManagementCLI implements CommandLineRunner {
//
//  private final UserService userService;
//  private final Scanner scanner;
//
//  public LibraryManagementCLI(UserService userService) {
//    this.userService = userService;
//    this.scanner = new Scanner(System.in);
//  }
//
//  @Override
//  public void run(String... args) {
//
////    System.out.println("Welcome to Library Management System");
////
////    while (true) {
////      displayMenu();
////      int choice = scanner.nextInt();
////      scanner.nextLine();
////
////      switch (choice) {
////        case 1 -> registerUser();
////        case 2 -> System.exit(0);
////        default -> System.out.println("Invalid choice");
////      }
////    }
//  }
//
//  private void displayMenu() {
//
//    System.out.println("\n1. Register");
//    System.out.println("2. Exit");
//    System.out.print("Enter choice: ");
//  }
//
//  private void registerUser() {
//
//    String username;
//    String password;
//    String firstName;
//    String lastName;
//
//    do {
//      System.out.print("Enter username: ");
//      username = scanner.nextLine().trim();
//    } while (username.isEmpty());
//
//    do {
//      System.out.print("Enter password: ");
//      password = scanner.nextLine().trim();
//    } while (password.isEmpty());
//
//    do {
//      System.out.print("Enter first name: ");
//      firstName = scanner.nextLine().trim();
//    } while (firstName.isEmpty());
//
//    do {
//      System.out.print("Enter last name: ");
//      lastName = scanner.nextLine().trim();
//    } while (lastName.isEmpty());
//
//    UserDTO userDTO = new UserDTO();
//    userDTO.setUsername(username);
//    userDTO.setPassword(password);
//    userDTO.setFirstName(firstName);
//    userDTO.setLastName(lastName);
//
//    try {
//
//      UserDTO registered = userService.registerMember(userDTO);
//      System.out.println("Registration successful! User ID: " + registered.getId());
//
//    } catch (ConstraintViolationException e) {
//
//      System.out.println("Registration failed:");
//      e.getConstraintViolations().forEach(violation -> System.out.println("  - " + violation.getMessage()));
//
//    } catch (ValidationException e) {
//      System.out.println("Registration failed: " + e.getMessage());
//    }
//  }
//}
