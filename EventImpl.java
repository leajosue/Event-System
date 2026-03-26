public class EventImpl extends Event {
    public EventImpl(String eventName, DateTime startDateTime, DateTime endDateTime, String host, int numberOfInvitees, String location) {
        super(eventName, startDateTime, endDateTime, host, numberOfInvitees, location);
    }

    @Override
    public int compareTo(Event o) {
        return 0;
    }
}
