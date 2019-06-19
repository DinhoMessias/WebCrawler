import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BuscadorProduto extends Thread {

    private String url;
    private ArrayList<String> idProdutos;
    private ArrayList<String> produtosRelacionados;

    public BuscadorProduto(String url, ArrayList<String> idProdutos, ArrayList<String> produtosRelacionados) {
        this.url = url;
        this.idProdutos = idProdutos;
        this.produtosRelacionados = produtosRelacionados;
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(this.url).ignoreContentType(true).get();
            Elements elsID = doc.select("div[id]");
            Elements elsLink = doc.select("a[href]");
            int k = 0;

            for (Element e : elsLink) {
                String link = e.attr("href");
                if (link.indexOf("MLB") != -1 && link.indexOf("click") == -1 && link.indexOf("#") == -1) {
                    //TODO local para fazer busca sobre os produtos relacionados
                    /*
                    Document docProd = Jsoup.connect(link).ignoreContentType(true).get();
                    */
                    System.out.println(link);
                }
            }

            for (Element e : elsID) {
                String id = e.attr("id");
                if (id.indexOf("MLB") == 0) {
                    addID(id);
                    System.out.println("ID " + k + " : " + id);
                    k++;
                }
            }
            Thread.sleep(10);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void addID(String id) {
        if (!this.idProdutos.contains(id)) {
            this.idProdutos.add(id);
        }
    }

    public synchronized void addProdutoRelacionado(String produto) {
        if (!this.produtosRelacionados.contains(produto)) {
            this.produtosRelacionados.add(produto);
        }
    }

}
