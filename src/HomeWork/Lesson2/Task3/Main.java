package HomeWork.Lesson2.Task3;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Main {
    static String makeRequest(String request) {
        try {
            URL url = new URL(request);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            char[] buffer = new char[1024];
            StringBuilder stringBuilder = new StringBuilder();
            while (inputStreamReader.read(buffer) > 0) {
                stringBuilder.append(buffer);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String request = "http://query.yahooapis.com/v1/public/yql?format=xml&q=select%20*%20from%20" +
                "yahoo.finance.xchange%20where%20pair%20in%20(\"USDEUR\",%20\"USDUAH\")&env=store://datatables.org/alltableswithkeys";

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Query.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            URL url = new URL(request);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            Query query = (Query) unmarshaller.unmarshal(url);
            System.out.println(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
