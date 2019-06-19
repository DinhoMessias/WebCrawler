import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Buscador extends Thread {
    private String url;
    ArrayList<String> links;

    public Buscador(ArrayList<String> links) {
        this.url = "https://www.mercadolivre.com.br/categories.html";
        this.links = links;
    }

    public Buscador(ArrayList<String> links, String url) {
        this.url = url;
        this.links = links;
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(this.url).ignoreContentType(true).get();
            Elements els = doc.select("a[href]");
            int k = 0;

            for (Element e : els) {
                String link = e.attr("href");
                if (link.indexOf("#") == -1) {
                    addLink(link);
                    System.out.println("Link " + k + " : " + link);
                }
                k++;
            }
            Thread.sleep(500);
        } catch (IOException |
                InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void addLink(String link) {
        if (!links.contains(link)) {
            this.links.add(link);
        }
    }
}
