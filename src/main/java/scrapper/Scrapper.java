package scrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import util.CsvFileUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrapper {

    private static final String BASE_URL = "https://www.tokopedia.com/p/handphone-tablet/handphone?ob=5&page=1";

    public static void main(String[] args) throws IOException {
        WebClient client = configureClient();
        HtmlPage page = client.getPage(BASE_URL);

        List<HtmlElement> items = page.getByXPath("//div[@class='css-bk6tzz e1nlzfl3']");

        if (items.isEmpty()) {
            System.out.println("No item found.");
        } else {
            ArrayList<Item> itemList = new ArrayList<>();
            for (HtmlElement htmlItem : items) {
                HtmlAnchor itemAnchor = htmlItem.getFirstByXPath("//div[@class='css-bk6tzz e1nlzfl3']/a");
                HtmlElement spanTitle = htmlItem.getFirstByXPath(".//span[@class='css-1bjwylw']");
                HtmlElement spanPrice = htmlItem.getFirstByXPath(".//span[@class='css-o5uqvq']");
                HtmlElement spanStoreName = (HtmlElement) htmlItem.getByXPath(".//span[@class='css-1kr22w3']").get(1);
                List<HtmlElement> spanStar = htmlItem.getByXPath(".//img[@class='css-177n1u3']");

                Item item = new Item();
                item.setTitle(spanTitle == null ? "-" : spanTitle.getTextContent());
                item.setImageUrl(itemAnchor == null ? "-" : itemAnchor.getHrefAttribute());
                item.setPrice(spanPrice == null ? "0.0" : spanPrice.getTextContent());
                item.setStoreName(spanStoreName == null ? "-" : spanStoreName.getTextContent());
                item.setRating(spanStar == null ? 0 : spanStar.size());

                itemList.add(item);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(itemList);
            CsvFileUtils.generateCsvFile(jsonString);
        }
    }

    private static WebClient configureClient() {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        return client;
    }

}
