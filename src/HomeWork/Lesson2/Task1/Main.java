package HomeWork.Lesson2.Task1;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

class Main {
    public static boolean saveToFile(Trains trains, String fileName) {
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(trains.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(trains, file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Trains recoverFromFile(String fileName) {
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Trains.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Trains trains = (Trains) unmarshaller.unmarshal(file);
            return trains;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showTrainsByDeparture(Trains trains, String date, String startTime, String endTime) {
        LocalDate compareDate = Train.convertDate(date);
        LocalTime compareStartTime = Train.convertTime(startTime);
        LocalTime compareEndTime = Train.convertTime(endTime);
        for (Train train : trains.getTrains()) {
            if ( (train.getDate().equals(compareDate)) && (compareStartTime.compareTo(train.getDeparture()) <= 0)
                    && (compareEndTime.compareTo(train.getDeparture()) >= 0) ) {
                System.out.println(train);
            }
        }
    }

    public static void main(String[] args) {
        Trains trains = recoverFromFile("trains.xml");
//        trains.addTrain("Kiev", "Odessa", "2013-12-22", "16:23");     // uncomment to test
//        trains.addTrain("Kiev", "Moscow", "2013-12-19", "17:01");     // uncomment to test
        showTrainsByDeparture(trains, "2013-12-19", "15:00", "19:00");
//        saveToFile(trains, "trains.xml");     // uncomment to test

    }
}
