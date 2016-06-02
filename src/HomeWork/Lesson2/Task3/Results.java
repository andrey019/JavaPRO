package HomeWork.Lesson2.Task3;

import javax.xml.bind.annotation.XmlElement;

class Results {
    @XmlElement
    private Rate[] rate;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (int i = 0; i < rate.length; i++) {
            stringBuilder.append(rate[i] + "\n");
        }
        return stringBuilder.toString();
    }
}
