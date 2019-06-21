import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BuscadorReviews extends Thread {
    String url;
    ArrayList<String> comentarios;

    public BuscadorReviews(ArrayList<String> comentarios) {
        this.url = "https://produto.mercadolivre.com.br/noindex/catalog/reviews/MLB872157483?noIndex=true&itemId=MLB872157483&contextual=true&access=view_all";
        this.comentarios = comentarios;
    }

    public BuscadorReviews(String url, ArrayList<String> comentarios) {
        this.url = "https://produto.mercadolivre.com.br" + url;
        this.comentarios = comentarios;
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(this.url).ignoreContentType(true).get();
            Elements els = doc.getElementsByClass("reviews-container");
            for (Element e : els) {
                Elements el = doc.getElementsByClass("review-element");
                Elements el2 = doc.getElementsByClass("review-listing");

                int filtroTitulo = e.getElementsByTag("label").toString().indexOf(">");
                int filtroTitulo2 = e.getElementsByTag("label").toString().indexOf("</");
                int filtroTexto = e.getElementsByTag("p").toString().indexOf(">");
                int filtroTexto2 = e.getElementsByTag("p").toString().indexOf("<s");
                String comentario = e.getElementsByTag("p").toString().substring(filtroTexto + 1, filtroTexto2);
                String titulo = e.getElementsByTag("label").toString().substring(filtroTitulo + 1, filtroTitulo2);
                System.out.println("Titulo: " + titulo + "\nComentario:" + comentario + "\n");
            }
            Thread.sleep(500);
        } catch (IOException |
                InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void addReview(String review) {
        if (!this.comentarios.contains(review)) {
            this.comentarios.add(review);
        }
    }


}
