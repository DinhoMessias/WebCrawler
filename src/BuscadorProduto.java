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


            for (Element e : elsID) {
                String produto = e.getElementsByClass("main-title").text();
                String precoReal = e.getElementsByClass("price__fraction").text();
                String precoCentavos = e.getElementsByClass("price__decimals").text();
                String url = e.getElementsByClass("item__info-link item__js-link ").attr("href");
                Elements imagem = e.getElementsByClass("lazy-load");
                String linkImagem = imagem.attr("src");
                String id = e.attr("id");
                String valorProduto;

                if(url.isEmpty()){
                    url = e.getElementsByClass("item__info-title").attr("href");

                }

                if (!precoCentavos.isEmpty()) {
                    valorProduto = precoReal.concat(",").concat(precoCentavos);
                } else {
                    valorProduto = precoReal;
                }

                if (produto.isEmpty()) {
                    produto = e.getElementsByClass("item_subtitle").text();
                }

                if (id.indexOf("MLB") == 0) {
                    addID(id);
                    System.out.println("Produto: " + produto + "\nPreço: R$" + valorProduto + "\nID: "
                            + id + "\nImagem: " + linkImagem + "\n" + "Link Produto: " + url + "\n");
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
            //System.out.println(linkComentario);
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