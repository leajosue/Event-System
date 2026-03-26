public class Event implements Comparable<Event> {
    private String eventName;
    private DateTime startDateTime;
    private  DateTime endDateTime;
    private String host;
    private int numberOfInvitees;
    private String location;
    public Event(String eventName,DateTime startDateTime, DateTime endDateTime,
                 String host, int numberOfInvitees, String location) {
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.host = host;
        this.numberOfInvitees = numberOfInvitees;
        this.location = location;
    }
    //getters
    public String getEventName() {
        return eventName;
    }
    public DateTime getStartDateTime() {
        return startDateTime;
    }
    public DateTime getEndDateTime() {
        return endDateTime;
    }
    public String getHost() {
        return host;
    }
    public int getNumberOfInvitees() {
        return numberOfInvitees;
    }
    public String getLocation() {
        return location;
    }
    private long getDurationInSeconds() {
        long start =startDateTime.toSeconds();
        long end = endDateTime.toSeconds();
        return end - start;
    }
  @Override
    public int compareTo(Event other){
         int compare =this.startDateTime.compareTo(other.startDateTime);
         if(compare!=0)return compare;

         //longest to shortest event
      long thisDuration= this.getDurationInSeconds();
      long otherDuration=other.getDurationInSeconds();
      if (thisDuration!=otherDuration)
          return Long.compare(otherDuration,thisDuration);

      return this.eventName.compareToIgnoreCase(other.eventName);//if start and duration are the same , sort alphabetically
  }


    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%d,%s",
                eventName, startDateTime, endDateTime, host, numberOfInvitees, location);
    }

    public boolean isHappeningOn(DateTime dt) {
        return dt.compareTo(startDateTime) >= 0 && dt.compareTo(endDateTime) <= 0;
    }
}
