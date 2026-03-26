public class DateTime implements Comparable<DateTime>{
    private Date date;
    private int hour;
    private int minute;
    private int second;
    private boolean am;

    public DateTime(Date date, int hour, int minute, int second, boolean am) {
        this.date = date;
        this.hour = hour == 12? 0:hour;
        this.minute = minute;
        this.second = second;
        this.am = am;
    }

    @Override
    public String toString() {
        return String.format("%s @ %02d:%02d:%02d %s", date, hour == 0?12:hour,
                minute, second, am? "am": "pm");
    }
    public static boolean isValidDateTime(DateTime dateTime){
        Date date = dateTime.date;
        int hour = dateTime.hour, minute = dateTime.minute, second = dateTime.second;
        return Date.isValidDate(date) &&
                hour >= 0 && minute >= 0 && second >= 0 &&
                hour < 13 && minute < 60 && second < 60;
    }
    @Override
    public int compareTo(DateTime other) {
        if(this.date.compareTo(other.date) != 0)
            return this.date.compareTo(other.date);
        if(this.am != other.am)
            return this.am?-1:1;
        if(this.hour != other.hour)
            return this.hour - other.hour;
        if(this.minute != other.minute)
            return this.minute - other.minute;
        return this.second - other.second;
    }
    public long toSeconds() {
        int y = date.getYear();
        int m = date.getMonth();
        int d = date.getDay();

        // Days in months (February = 28, leap handled separately)
        int[] monthDays = {31,28,31,30,31,30,31,31,30,31,30,31};

        // Count total days in previous years (simplified, ignoring leap centuries for simplicity)
        long totalDays = (y - 1) * 365L + (y - 1) / 4L - (y - 1) / 100L + (y - 1) / 400L;

        // Add days of the months in current year
        for (int i = 0; i < m - 1; i++) totalDays += monthDays[i];

        // Handle leap year for current year
        if (m > 2 && (y % 4 == 0 && y % 100 != 0 || y % 400 == 0)) totalDays += 1;

        // Add current month day
        totalDays += d - 1;

        // Convert hours to 24h format
        int hour24 = hour % 12 + (am ? 0 : 12);

        // Return total seconds
        return totalDays * 24 * 3600L + hour24 * 3600L + minute * 60L + second;
    }

}
