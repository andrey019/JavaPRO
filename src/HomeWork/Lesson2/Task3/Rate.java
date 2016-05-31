package HomeWork.Lesson2.Task3;

import javax.xml.bind.annotation.XmlElement;

class Rate {
    @XmlElement
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
        return Name + "=" + Rate;
    }
}
