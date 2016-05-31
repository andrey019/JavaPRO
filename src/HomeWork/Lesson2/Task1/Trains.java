package HomeWork.Lesson2.Task1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
class Trains {
    @XmlElement(name = "train")
    private ArrayList<Train> trains;

    Trains() {
        trains = new ArrayList<>();
    }

    public boolean addTrain(String from, String to, String date, String time) {
        if (date.split("-").length != 3) {
            return false;
        }
        if (time.split(":").length != 2) {
            return false;
        }
        trains.add(new Train(trains.size() + 1, from, to, date, time));
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Train train : trains) {
            stringBuilder.append(train + "\n");
        }
        return stringBuilder.toString();
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }
}
