import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BuscadorProduto extends Thread {

    private String url;
    private ArrayList<String> idProdutos;
    private ArrayList<String> linkComentarios;

    public BuscadorProduto(String url, ArrayList<String> idProdutos, ArrayList<String> linkComentarios) {
        this.url = url;
        this.idProdutos = idProdutos;
        this.linkComentarios = linkComentarios;
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(this.url).ignoreContentType(true).get();
            Elements elsID = doc.select("div[id]");
            Elements elsLink = doc.getElementsByClass("item__info-link item__js-link ");
            int k = 0;
            for (Element e : elsID) {
                String id = e.attr("id");
                if (id.indexOf("MLB") == 0) {
                    addID(id);
                    System.out.println("ID " + k + " : " + id);
                    k++;
                }
            }

            for (Element e : elsLink) {
                String link = e.attr("href");
                if (link.indexOf("MLB") != -1 && link.indexOf("click") == -1 && link.indexOf("#") == -1) {
                    linkComentarios(link);
                }
            }

            Thread.sleep(10);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void linkComentarios(String link) throws IOException {
        Document doc = Jsoup.connect(link).ignoreContentType(true).get();
        int filtro = doc.getElementById("reviewsCard").toString().indexOf("/noindex");
        int filtro2 = doc.getElementById("reviewsCard").toString().indexOf("\" data-modal:dinamic=\"true\"");
        if (filtro > 0 && filtro2 > filtro) {
            String linkComentario = doc.getElementById("reviewsCard").toString().substring(filtro, filtro2);
            System.out.println(linkComentario);
            addLinkComentario(linkComentario);
        }
    }

    public synchronized void addID(String id) {
        if (!this.idProdutos.contains(id)) {
            this.idProdutos.add(id);
        }
    }

    public synchronized void addLinkComentario(String link) {
        if (!this.linkComentarios.contains(link)) {
            this.linkComentarios.add(link);
        }
    }
}