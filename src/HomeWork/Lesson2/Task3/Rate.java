package HomeWork.Lesson2.Task3;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

class Rate {
    @XmlAttribute
    private String id;
    @XmlElement
    private String Name;
    @XmlElement
    private double Rate;
    @XmlElement
    private String Date;
    @XmlElement
    private String Time;
    @XmlElement
    private String Ask;
    @XmlElement
    private String Bid;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\tid=" + id + "\n");
        stringBuilder.append("\tName=" + Name + "\n");
        stringBuilder.append("\tRate=" + Rate + "\n");
        stringBuilder.append("\tDate=" + Date + "\n");
        stringBuilder.append("\tTime=" + Time + "\n");
        stringBuilder.append("\tAsk=" + Ask + "\n");
        stringBuilder.append("\tBid=" + Bid + "\n");
        return stringBuilder.toString();
    }
}
