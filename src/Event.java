import java.time.LocalDateTime;
import java.util.*;

public class Event {

    int id;

    String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Map<User, InvitationStatus> getInvitees() {
        return invitees;
    }

    public void setInvitees(Map<User, InvitationStatus> invitees) {
        this.invitees = invitees;
    }

    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Location location;
    User host;
    Frequency frequency;
    Map<User, InvitationStatus> invitees;

    public Event(int id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, Location location,
            User host, Frequency frequency) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.host = host;
        this.frequency = frequency; // Use the provided frequency
        this.invitees = new HashMap<>();
    }

    // Overloaded constructor for single-instance events (default to ONCE)
    public Event(int id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, Location location,
            User host) {
        this(id, title, description, startTime, endTime, location, host, Frequency.ONCE);
    }

    void invite(User user){
        invitees.put(user, InvitationStatus.INVITED);
        NotificationService.sendNotification(user, this, host);

    }

    void respondToInvite(User user, InvitationStatus invitationStatus){
        invitees.put(user, invitationStatus);
        NotificationService.sendNotificationToHost(user, this, invitationStatus);
        
        // If the invitation is accepted, add the event to the invitee's calendar
        if (invitationStatus == InvitationStatus.ACCEPTED) {
            // Check if the event is already in the user's calendar
            if (user.calendar.getEventById(this.id) == null) {
                user.calendar.events.add(this);
                System.out.println(user.name + " added event '" + this.title + "' to their calendar.");
            }
        }
    }

    void updateEventDetails( String title, String description, LocalDateTime startTime, LocalDateTime endTime, Location location){


                this.title = title;
                this.description = description;
                this.startTime = startTime;
                this.endTime = endTime;
                this.location = location;

                notifyAllInvitees();
            }


    void notifyAllInvitees(){
        for(User user : invitees.keySet()){
            if (invitees.get(user) == InvitationStatus.ACCEPTED) {
                NotificationService.sendNotificationForUpdation(user, this);
            }
        }
    }










    

    
}
