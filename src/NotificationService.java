public class NotificationService {
    
    public static void sendNotification(User user, Event event, User host){
        System.out.println("Notification for " + user.name + ": A new meeting invite has been received for event: " + event.getTitle() + " from " + host.name);
    }

    public static void sendNotificationForUpdation(User user, Event event){
        System.out.println("Notification for " + user.name + ": Event details have been updated for: " + event.getTitle() + 
                           " (Start: " + event.getStartTime() + ", End: " + event.getEndTime() + 
                           ", Location: " + event.getLocation().getName() + ")");
    }

    public static void sendNotificationToHost(User user, Event event, InvitationStatus invitationStatus){
        System.out.println("Notification for " + event.getHost().name + ": " + user.name + 
                           " has " + invitationStatus.toString().toLowerCase() + " your invitation to " + event.getTitle());
    }
    
    public static void sendDeletionNotification(User user, String eventTitle, User host) {
        System.out.println("Notification for " + user.name + ": Event '" + eventTitle + "' has been deleted by " + host.name);
    }
}
