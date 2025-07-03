import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Calendar {
    
    List<Event> events;

    Calendar(){
        this.events = new ArrayList<>();
    }

    void addEvent(int id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, Location location, User user, Frequency frequency){
        Event event = new Event(id, title, description, startTime, endTime, location, user, frequency);
        this.events.add(event);
    }

    void updateEvent(int id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, Location location){
        for(Event event : events){
            if(event.id == id){
                event.updateEventDetails(title, description, startTime, endTime, location);
                break;
            }
        }
    }

    public Event getEventById(int id) {
        for (Event event : events) {
            if (event.id == id) {
                return event;
            }
        }
        return null;
    }

    void deleteEvent(int id){
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (event.id == id) {
                iterator.remove();
                break;
            }
        }
    }

    public List<Event> getEventsForDay(LocalDateTime day) {
        return events.stream()
                .filter(event -> event.getStartTime().toLocalDate().equals(day.toLocalDate()))
                .collect(Collectors.toList());
    }

    public List<Event> getEventsForWeek(LocalDateTime weekStart) {
        LocalDateTime weekEnd = weekStart.plusDays(6);
        return events.stream()
                .filter(event -> (event.getStartTime().isAfter(weekStart.minusDays(1)) && event.getStartTime().isBefore(weekEnd.plusDays(1))))
                .collect(Collectors.toList());
    }

    public List<Event> getEventsForMonth(LocalDateTime monthStart) {
        LocalDateTime monthEnd = monthStart.plusMonths(1).minusDays(1);
        return events.stream()
                .filter(event -> (event.getStartTime().isAfter(monthStart.minusDays(1)) && event.getStartTime().isBefore(monthEnd.plusDays(1))))
                .collect(Collectors.toList());
    }
}


//sayanr@zeta.tech
