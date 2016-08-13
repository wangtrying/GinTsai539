package idv.terry.lotto.lib;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class WebParser {
    private static final String WEB_539_URI = "http://www.taiwanlottery.com.tw/Info/number/frequency.aspx?GAME=539#";
    private static final String LAST_539_URI = "http://www.taiwanlottery.com.tw/Lotto/Dailycash/history.aspx";

    private static WebParser mInstance = new WebParser();

    private ConcurrentHashMap<Integer, Integer> mResultHashMap = new ConcurrentHashMap<Integer, Integer>();

    public static synchronized WebParser getInstance() {
        return mInstance;
    }

    public ConcurrentHashMap<Integer, Integer> get539() {
        try {
            Connection connection = Jsoup.connect(WEB_539_URI);
            connection.timeout(30000);
            Document doc = (Document) connection.post();
            Elements e = doc.body().getElementsByTag("tbody");

            String[] stringList = e.text().split(" ");
            parseIntoMap(stringList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mResultHashMap;
    }

    private void parseIntoMap(String[] stringList) {

        try {
            for (int i = 6; i < stringList.length; i = i + 2) {
                int number = Integer.parseInt(stringList[i]);
                int appear = Integer.parseInt(stringList[i + 1]);
                mResultHashMap.putIfAbsent(number, appear);
            }
        } catch (NumberFormatException e) {
        }
    }

    public NumberIChi getLast539() {
        try {
            Connection connection = Jsoup.connect(LAST_539_URI);
            connection.timeout(30000);
            Document doc = (Document) connection.post();
            Elements body = doc.body().getElementsByTag("tbody");

            String[] stringList = body.text().split(" ");


            NumberIChi result = parseIntoNumberIChi(stringList);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new NumberIChi(1, 2, 3, 4, 5);
    }

    private NumberIChi parseIntoNumberIChi(String[] stringList) {
        int i = 0;
        for (; i <= stringList.length; i++) {
            if ("開出順序".equals(stringList[i])) {
                break;
            }
        }
        int a1 = Integer.parseInt(stringList[i + 1]);
        int a2 = Integer.parseInt(stringList[i + 2]);
        int a3 = Integer.parseInt(stringList[i + 3]);
        int a4 = Integer.parseInt(stringList[i + 4]);
        int a5 = Integer.parseInt(stringList[i + 5]);

        return new NumberIChi(a1, a2, a3, a4, a5);

    }

}
