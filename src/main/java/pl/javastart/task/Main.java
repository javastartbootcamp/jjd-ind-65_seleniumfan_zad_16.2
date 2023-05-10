package pl.javastart.task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "dd.MM.yyyy HH:mm:ss"};

        System.out.println("Podaj datę:");
        String inputDate = scanner.nextLine();
        for (String form : patterns) {
            LocalDateTime dateTime;
            try {
                if (form.equals("yyyy-MM-dd")) {
                    dateTime = LocalDateTime.parse(inputDate + " 00:00:00", FORMATTER);
                } else {
                    dateTime = LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern(form));
                }
            } catch (DateTimeParseException e) {
                continue;
            }
            System.out.println("Czas lokalny: " + dateTime.format(FORMATTER));

            ZonedDateTime utcDateTime = dateTime.atZone(ZoneId.of("Europe/Warsaw")).withZoneSameInstant(ZoneId.of("UTC"));
            System.out.println("UTC: " + utcDateTime.format(FORMATTER));

            getTimeIn("Europe/London", "Londyn", utcDateTime);
            getTimeIn("America/Los_Angeles", "Los Angeles", utcDateTime);
            getTimeIn("Australia/Sydney", "Sydney", utcDateTime);
        }
    }

    private void getTimeIn(String zoneId, String city, ZonedDateTime utcDateTime) {
        ZoneId sydneyZone = ZoneId.of(zoneId);
        ZonedDateTime sydneyDateTime = utcDateTime.withZoneSameInstant(sydneyZone);
        System.out.println(city + ": " + sydneyDateTime.format(FORMATTER));
    }
}
