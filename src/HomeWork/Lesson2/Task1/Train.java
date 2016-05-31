package HomeWork.Lesson2.Task1;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Train {
    @XmlAttribute
    private int id;
    @XmlElement
    private String from;
    @XmlElement
    private String to;
    @XmlElement
    @XmlJavaTypeAdapter(XmlDateAdapter.class)
    private LocalDate date;
    @XmlElement
    @XmlJavaTypeAdapter(XmlTimeAdapter.class)
    private LocalTime departure;

    Train() {
        // required by JAXB
    }

    Train(int id, String from, String to, String date, String departure) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = convertDate(date);
        this.departure = convertTime(departure);
    }

    static LocalDate convertDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    static LocalTime convertTime(String departure) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(departure, formatter);
        return localTime;
    }

    @Override
    public String toString() {
        return "id=" + id + ": from " + from + ", to " + to + ", date " + date + ", time " + departure;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getDeparture() {
        return departure;
    }
}
