package src.boundaries;

import src.controllers.CinemaController;
import src.controllers.HolidayController;
import src.entities.Cinema;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SystemConfig {
    public static void configTicketPrices(Cinema cinema) throws Exception {
        System.out.println("Enter Movie title for which you would like to update the prices");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String title = reader.readLine();
        System.out.println("Enter the price you want to set for this ticket");
        double price = Double.parseDouble(reader.readLine());
        cinema.setTicketPrice(title, price);
        ArrayList<Cinema> cinemaDB = CinemaController.getCinemaDB();
        for (Cinema c : cinemaDB) {
            if (c.getName() == cinema.getName()) {
                c = cinema;
                break;
            }
        }
        CinemaController.setCinemaDB(cinemaDB);
    }

    public static void configHolidays() throws Exception {
        int choice = 0;
        String date_input;
        LocalDate localDate = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean quit = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        while (choice != 3) {
            System.out.println("Print choice: " +
                    "1. Add Holiday" +
                    "2. Remove holiday" +
                    "3. Exit");
            choice = Integer.parseInt(reader.readLine());
            if (choice == 1) {
                while (!quit) {
                    try {
                        System.out.println("Enter the holiday date to add in this pattern: d/MM/yyyy:");
                        date_input = reader.readLine();
                        localDate = LocalDate.parse(date_input, formatter);
                        quit = true;
                    } catch (Exception e) {
                        System.out.println("Invalid date format, Please try again");
                    }
                }
                HolidayController.setHolidays(localDate);
            } else if (choice == 2) {
                while (!quit) {
                    try {
                        System.out.println("Enter the holiday date to remove in this pattern: d/MM/yyyy:");
                        date_input = reader.readLine();
                        localDate = LocalDate.parse(date_input, formatter);
                        quit = true;
                    } catch (Exception e) {
                        System.out.println("Invalid date format, Please try again");
                    }
                }
                HolidayController.removeHolidays(localDate);
            }
        }
    }
}