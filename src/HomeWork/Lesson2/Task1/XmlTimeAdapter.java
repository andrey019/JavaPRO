package HomeWork.Lesson2.Task1;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class XmlTimeAdapter extends XmlAdapter<String, LocalTime> {
    @Override
    public LocalTime unmarshal(String v) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(v, formatter);
        return localTime;
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v.toString();
    }
}
