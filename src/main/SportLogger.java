import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SportLogger {
    private List<SportActivity> activities;
    private static final String FILE_NAME = "activities.txt";

    public SportLogger() {
        activities = new ArrayList<>();
        loadActivities();
    }

    public void logActivity(String name, int duration) {
        for (SportActivity activity : activities) {
            if (activity.getName().equals(name)) {
                System.out.println("Activity with this name already exists.");
                return;
            }
        }
        activities.add(new SportActivity(name, duration));
        saveActivities();
    }

    public void viewActivities() {
        if (activities.isEmpty()) {
            System.out.println("No activities logged.");
        } else {
            for (SportActivity activity : activities) {
                System.out.println(activity);
            }
        }
    }

    public void removeActivity(String name) {
        activities.removeIf(activity -> activity.getName().equals(name));
        saveActivities();
    }

    public int calculateTotalTime() {
        int totalTime = 0;
        for (SportActivity activity : activities) {
            totalTime += activity.getDuration();
        }
        return totalTime;
    }

    private void saveActivities() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (SportActivity activity : activities) {
                writer.write(activity.getName() + "," + activity.getDuration());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving activities: " + e.getMessage());
        }
    }

    private void loadActivities() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int duration = Integer.parseInt(parts[1]);
                    activities.add(new SportActivity(name, duration));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading activities: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SportLogger logger = new SportLogger();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Log Activity");
            System.out.println("2. View Activities");
            System.out.println("3. Calculate Total Time");
            System.out.println("4. Remove Activity");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter activity name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter duration in minutes: ");
                    int duration = scanner.nextInt();
                    logger.logActivity(name, duration);
                    break;
                case 2:
                    logger.viewActivities();
                    break;
                case 3:
                    int totalTime = logger.calculateTotalTime();
                    System.out.println("Total time spent on sports: " + totalTime + " minutes");
                    break;
                case 4:
                    System.out.print("Enter activity name to remove: ");
                    String removeName = scanner.nextLine();
                    logger.removeActivity(removeName);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private class SportActivity {
        private String name;
        private int duration; // in minutes

        public SportActivity(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public int getDuration() {
            return duration;
        }

        @Override
        public String toString() {
            return "Activity: " + name + ", Duration: " + duration + " minutes";
        }
    }
}