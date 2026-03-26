
import java.util.*;
public class EventOrganizer {
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Event>events= new ArrayList<>();
        System.out.println("Enter events and enter Done when finished");

        while(true){

            String givenEvent = keyboard.nextLine();
            if (givenEvent.equalsIgnoreCase("DONE"))break;
            try{
                String[] eventProperties = givenEvent.split("\\s*,\\s* ");
            if (eventProperties.length!=6)
                throw new Exception ("Illegal event(6) entry Please try again ");
            String eventName = eventProperties[0];
            String startDateTimeString = eventProperties[1];
            String endDateTimeString = eventProperties[2];
            String hostName = eventProperties[3];
            String guestCount = eventProperties[4];
            String eventLocation = eventProperties[4];

                if (eventName.contains(",") || hostName.contains(",") || eventLocation.contains(",")) {
                    throw new Exception("Name, host, or location cannot contain commas.");
                }

                // Parse start and end DateTime
                DateTime startDateTime = parseDateTime(startDateTimeString);
                DateTime endDateTime = parseDateTime(endDateTimeString);

                // Parse invitees
                int numInvitees;
                try {
                    numInvitees = Integer.parseInt(guestCount);
                } catch (NumberFormatException e) {
                    throw new Exception("Number of invitees must be an integer.");
                }

                // Create event object
                Event event = new Event(eventName, startDateTime, endDateTime, hostName, numInvitees, eventLocation);
                events.add(event);

            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
                System.out.println("Please re-enter the event.");
            }
        }

        // command
        while (true) {
            System.out.print("Enter command: ");
            String command = keyboard.nextLine().trim();

            if (command.equalsIgnoreCase("quit")) {
                System.out.println("Program terminated.");
                break;
            }

            else if (command.equalsIgnoreCase("print")) {
                Collections.sort(events);
                for (Event e : events) System.out.println(e);
            }

            else if (command.toLowerCase().startsWith("hosted by")) {
                String host = command.substring(9).trim();
                events.stream()
                        .filter(e -> e.getHost().equalsIgnoreCase(host))
                        .sorted()
                        .forEach(System.out::println);
            }

            if (command.toLowerCase().startsWith("happening on")) {
                String datetimeString = command.substring(13).trim();
                try {
                    DateTime dt = parseDateTime(datetimeString);
                    Collections.sort(events);
                    for (Event e : events)
                        if (e.isHappeningOn(dt))
                            System.out.println(e);
                } catch (Exception e) {
                    System.out.println("Invalid date/time format in command.");
                }
                continue;
            }


            else {
                System.out.println("Unknown command. Try again.");
            }
        }
    }


    private static DateTime parseDateTime(String s) throws Exception {
        try {
            // input
            String[] parts = s.split("@");
            if (parts.length != 2)
                throw new Exception("Invalid date/time format.");

            String datePart = parts[0].trim();  // 03/15/2025
            String timePart = parts[1].trim();  // 10:30:00 am

            String[] datePieces = datePart.split("/");
            if (datePieces.length != 3)
                throw new Exception("Invalid date format.");

            int month = Integer.parseInt(datePieces[0]);
            int day = Integer.parseInt(datePieces[1]);
            int year = Integer.parseInt(datePieces[2]);
            Date date = new Date(day, month, year);

            String[] timePieces = timePart.split(" ");
            if (timePieces.length != 2)
                throw new Exception("Invalid time format.");

            String[] dateToken = timePieces[0].split(":");
            if (dateToken.length != 3)
                throw new Exception("Invalid time format.");

            int hour = Integer.parseInt(dateToken[0]);
            int minute = Integer.parseInt(dateToken[1]);
            int second = Integer.parseInt(dateToken[2]);
            boolean am = timePieces[1].equalsIgnoreCase("am");

            DateTime dt = new DateTime(date, hour, minute, second, am);
            if (!DateTime.isValidDateTime(dt))
                throw new Exception("Invalid date/time values.");

            return dt;
        } catch (Exception e) {
            throw new Exception("Invalid date/time format. Use MM/DD/YYYY @ hh:mm:ss am/pm");
        }
    }
}


