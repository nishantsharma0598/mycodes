import java.time.LocalDateTime;
import java.util.List;

public class App {
    
    public static void main(String[] args) {

        UserController userController = new UserController();
        userController.addUser(1, "Nishant", "nishant@gmail.com");
        userController.addUser(2, "Second", "second@gmail.com");
        userController.addUser(3, "Third", "third@gmail.com");

        // Retrieve users
        User nishant = userController.users.get(0);
        User second = userController.users.get(1);
        User third = userController.users.get(2);

        System.out.println("Creating Events");

        // nishant creates a single-instance event
        LocalDateTime event1StartTime = LocalDateTime.of(2025, 7, 10, 10, 0);
        LocalDateTime event1EndTime = LocalDateTime.of(2025, 7, 10, 11, 0);
        Location location1 = new Location("Meeting Room A", "1234, Bangalore");
        nishant.addEventToCalendar(101, "Project Scrum", "Discuss goals", event1StartTime, event1EndTime, location1, nishant, Frequency.ONCE);
        

        // nishant invites second and third
        Event eventScrum = nishant.calendar.events.get(0); // Assuming it's the first event
        eventScrum.invite(second);
        eventScrum.invite(third);


        // second accepts the invitation
        eventScrum.respondToInvite(second, InvitationStatus.ACCEPTED);
        System.out.println("second accepted 'Project Scrum' invitation.");

        // third declines the invitation
        eventScrum.respondToInvite(third, InvitationStatus.DECLINED);
        // Do NOT add to third's calendar if declined
        System.out.println("third declined 'Project Scrum' invitation.");

        // nishant creates a weekly recurring event
        LocalDateTime event2StartTime = LocalDateTime.of(2025, 7, 15, 14, 0);
        LocalDateTime event2EndTime = LocalDateTime.of(2025, 7, 15, 15, 0);
        Location location2 = new Location("Online", "Zoom Link");
        nishant.addEventToCalendar(102, "Team Standup", "Daily team sync", event2StartTime, event2EndTime, location2, nishant, Frequency.WEEKLY);
        System.out.println("nishant created 'Team Standup' (weekly).");
        
        // Get the Team Standup event and invite second
        Event teamStandup = nishant.calendar.getEventById(102);
        teamStandup.invite(second);
        
        // second accepts the invitation
        teamStandup.respondToInvite(second, InvitationStatus.ACCEPTED);
        System.out.println("second accepted 'Team Standup' invitation.");


        // viewing calendar for a day
        System.out.println("nishant's events on 2025-07-10:");
        List<Event> nishantEventsDay = nishant.calendar.getEventsForDay(LocalDateTime.of(2025, 7, 10, 0, 0));
        nishantEventsDay.forEach(event -> System.out.println("- " + event.getTitle() + " at " + event.getStartTime()));

        // second views his events for a specific week
        System.out.println("second's events for the week of 2025-07-07:");
        List<Event> secondEventsWeek = second.calendar.getEventsForWeek(LocalDateTime.of(2025, 7, 7, 0, 0));
        secondEventsWeek.forEach(event -> System.out.println("- " + event.getTitle() + " at " + event.getStartTime()));

        // third views his events for a specific month (should be empty as he declined)
        System.out.println("third's events for July 2025:");
        List<Event> thirdEventsMonth = third.calendar.getEventsForMonth(LocalDateTime.of(2025, 7, 1, 0, 0));
        if (thirdEventsMonth.isEmpty()) {
            System.out.println("No events for third in July.");
        } else {
            thirdEventsMonth.forEach(event -> System.out.println("- " + event.getTitle() + " at " + event.getStartTime()));
        }

        System.out.println("--- Updating Event ---");
        // nishant updates an event
        nishant.updateEventInCalendar(101, "Project Scrum (Updated)", "Discuss Q3 goals and next steps", event1StartTime, event1EndTime.plusHours(1), location1);
        System.out.println("nishant updated 'Project Scrum'.");

        // Verify update for second (who accepted)
        System.out.println("second's updated 'Project Scrum' event:");
        secondEventsWeek = second.calendar.getEventsForWeek(LocalDateTime.of(2025, 7, 7, 0, 0));
        secondEventsWeek.forEach(event -> System.out.println("- " + event.getTitle() + " from " + event.getStartTime() + " to " + event.getEndTime()));

        System.out.println("--- Deleting Event ---");
        // nishant deletes an event
        nishant.deleteEventFromCalendar(102, userController);
        System.out.println("nishant deleted 'Team Standup'.");

        // Verify deletion for nishant
        System.out.println("nishant's events after deletion:");
        nishantEventsDay = nishant.calendar.getEventsForDay(LocalDateTime.of(2025, 7, 10, 0, 0)); // Still show Project Scrum
        List<Event> nishantEventsMonth = nishant.calendar.getEventsForMonth(LocalDateTime.of(2025, 7, 1, 0, 0)); // Check for Team Standup
        if (nishantEventsMonth.stream().noneMatch(event -> event.getTitle().equals("Team Standup"))) {
            System.out.println("'Team Standup' successfully deleted from nishant's calendar.");
        }
        
        // Verify deletion for second (who accepted the invitation)
        System.out.println("second's events after deletion:");
        List<Event> secondEventsMonth = second.calendar.getEventsForMonth(LocalDateTime.of(2025, 7, 1, 0, 0));
        if (secondEventsMonth.stream().noneMatch(event -> event.getTitle().equals("Team Standup"))) {
            System.out.println("'Team Standup' successfully deleted from second's calendar as well.");
        } else {
            System.out.println("ERROR: 'Team Standup' still exists in second's calendar!");
        }
    }
}
