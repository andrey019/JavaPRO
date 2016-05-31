package HomeWork.Lesson2.Task3;

import javax.xml.bind.annotation.XmlElement;

class Results {
    @XmlElement
    private Rate[] rate;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < rate.length - 1; i++) {
            stringBuilder.append(rate[i] + ", ");
        }
        stringBuilder.append(rate[rate.length - 1] + "]");
        return stringBuilder.toString();
    }
}
