package HomeWork.Lesson2.Task3;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
class Query {
    @XmlElement
    private String yahoo;
    @XmlElement
    private int count;
    @XmlElement
    private String created;
    @XmlElement
    private String lang;
    @XmlElement
    private Results results;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Results=" + results);
        return stringBuilder.toString();
    }
}
