import java.time.LocalDateTime;
import java.util.Map;

public class User {
    
    int userId;
    String name;
    String emailId;
    Calendar calendar;

    public User(int userId, String name, String emailId) {
        this.userId = userId;
        this.name = name;
        this.emailId = emailId;
        this.calendar = new Calendar();
    }

    void addEventToCalendar(int id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, Location location, User user, Frequency frequency){
        calendar.addEvent(id, title, description, startTime, endTime, location, user, frequency);
    }

    void updateEventInCalendar(int id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, Location location){
        calendar.updateEvent(id, title, description, startTime, endTime, location);
    }

    void deleteEventFromCalendar(int id, UserController userController){
        Event eventToDelete = this.calendar.getEventById(id);
        if (eventToDelete != null) {
            String eventTitle = eventToDelete.getTitle();
            
            // If the current user is the host of the event, delete it from all invitees' calendars
            if (eventToDelete.getHost().userId == this.userId) {
                System.out.println("Host " + this.name + " is deleting event: " + eventTitle);
                
                // Notify all accepted invitees about the deletion
                for (Map.Entry<User, InvitationStatus> entry : eventToDelete.getInvitees().entrySet()) {
                    User invitee = entry.getKey();
                    InvitationStatus status = entry.getValue();
                    
                    if (status == InvitationStatus.ACCEPTED) {
                        NotificationService.sendDeletionNotification(invitee, eventTitle, this);
                    }
                }
                
                // Delete the event from all users' calendars
                for (User user : userController.users) {
                    // Check if the user has this event in their calendar
                    if (user.calendar.getEventById(id) != null) {
                        user.calendar.deleteEvent(id);
                        System.out.println("Event " + eventTitle + " deleted from " + user.name + "'s calendar.");
                    }
                }
            } else {
                // If not the host, just delete from this user's calendar
                this.calendar.deleteEvent(id);
                System.out.println("Event " + eventTitle + " deleted from " + this.name + "'s calendar.");
            }
        }
    }






    

}
