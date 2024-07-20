package org.example;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        importFromJson();
                        break;
                    case 2:
                        displayData();
                        break;
                    case 3:
                        searchHours();
                        break;
                    case 4:
                        sortHours();
                        break;
                    case 5:
                        addRecord();
                        break;
                    case 6:
                        removeRecord();
                        break;
                    case 7:
                        exportToJson();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void showMenu() {
        System.out.println("1. Import from JSON");
        System.out.println("2. Display Data");
        System.out.println("3. Search Hours");
        System.out.println("4. Sort Hours");
        System.out.println("5. Add Record");
        System.out.println("6. Remove Record");
        System.out.println("7. Export to JSON");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void importFromJson() throws Exception {
        System.out.print("Enter JSON file path for import: ");
        String filePath = scanner.nextLine();
        new DataImporter().importFromJson(filePath);
        System.out.println("Data imported successfully.");
    }

    private static void displayData() throws SQLException {
        new DataViewer().displayData();
    }

    private static void searchHours() throws SQLException {
        System.out.print("Enter search regex for comments: ");
        String regex = scanner.nextLine();
        new DataSearcher().searchComments(regex).forEach(System.out::println);
    }

    private static void sortHours() throws SQLException {
        System.out.println("1. Sort by Passenger Count");
        System.out.println("2. Sort by Comment Length");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                new DataSorter().sortHoursByPassengerCount().forEach(System.out::println);
                System.out.println("Sorted by passenger count.");
                break;
            case 2:
                new DataSorter().sortHoursByCommentLength().forEach(System.out::println);
                System.out.println("Sorted by comment length.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addRecord() throws SQLException {
        System.out.print("Enter metro station ID: ");
        int metroStationId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter passenger count: ");
        int passengerCount = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();
        System.out.print("Enter date time (yyyy-MM-ddTHH:mm:ss): ");
        String dateTime = scanner.nextLine();

        Hour hour = new Hour(passengerCount, comment, LocalDateTime.parse(dateTime));
        new DataAdder().addHour(hour, metroStationId);
        System.out.println("Record added successfully.");
    }

    private static void removeRecord() throws SQLException {
        System.out.print("Enter Hour ID to remove: ");
        int hourId = scanner.nextInt();
        scanner.nextLine();
        new DataRemover().removeHour(hourId);
        System.out.println("Record removed successfully.");
    }

    private static void exportToJson() throws Exception {
        System.out.print("Enter JSON file path for export: ");
        String filePath = scanner.nextLine();
        new DataExporter().exportToJson(filePath);
        System.out.println("Data exported successfully.");
    }
}
