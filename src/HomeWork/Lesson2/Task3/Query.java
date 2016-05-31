package HomeWork.Lesson2.Task3;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "query")
class Query {
    @XmlElement
    private Results results;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Results=" + results);
        return stringBuilder.toString();
    }
}
