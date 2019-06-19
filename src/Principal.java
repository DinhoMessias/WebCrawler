import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) throws InterruptedException {
		ArrayList<String> categorias = new ArrayList<String>();
		ArrayList<String> idProdutos = new ArrayList<String>();
		Buscador b1 = new Buscador(categorias);
		b1.start();
		b1.join();
		while (!categorias.isEmpty()) {
			for (int i = 0; i < 5; i++) {
				if (i < categorias.size()) {
					BuscadorProduto b2 = new BuscadorProduto(categorias.remove(i), idProdutos);
				//	b2.start();
				}
			}
		}
	}
}
