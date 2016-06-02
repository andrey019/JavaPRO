package HomeWork.Lesson2.Task3;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;

class Main {
    public static void main(String[] args) {
        String request = "http://query.yahooapis.com/v1/public/yql?format=xml&q=select%20*%20from%20" +
        "yahoo.finance.xchange%20where%20pair%20in%20(\"USDEUR\",%20\"USDUAH\")&env=store://datatables.org/alltableswithkeys";

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Query.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            URL url = new URL(request);
            Query query = (Query) unmarshaller.unmarshal(url);
            System.out.println(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
