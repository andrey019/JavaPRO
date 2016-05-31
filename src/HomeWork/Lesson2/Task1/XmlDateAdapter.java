package HomeWork.Lesson2.Task1;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class XmlDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(v, formatter);
        return localDate;
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
