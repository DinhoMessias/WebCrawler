import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BuscadorProduto extends Thread {

	private String url;
	private ArrayList<String> idProdutos;

	public BuscadorProduto(ArrayList<String> idProdutos) {
		this.url = "https://carros.mercadolivre.com.br/acessorios/";
		this.idProdutos = idProdutos;
	}

	public BuscadorProduto(String url, ArrayList<String> idProdutos) {
		this.url = url;
		this.idProdutos = idProdutos;
	}

	@Override
	public void run() {
		try {
			Document doc = Jsoup.connect(this.url).ignoreContentType(true).get();
			Elements els = doc.select("div[id]");
			int k = 0;

			for (Element e : els) {
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

}
