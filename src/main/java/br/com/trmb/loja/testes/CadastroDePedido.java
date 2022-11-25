package br.com.trmb.loja.testes;

import br.com.trmb.loja.dao.CategoriaDAO;
import br.com.trmb.loja.dao.ClienteDAO;
import br.com.trmb.loja.dao.PedidoDAO;
import br.com.trmb.loja.dao.ProdutoDAO;
import br.com.trmb.loja.modelo.*;
import br.com.trmb.loja.util.JPAUtil;
import br.com.trmb.loja.vo.RelatorioVendasVO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBD();
        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDao = new ProdutoDAO(entityManager);
        ClienteDAO clienteDAO = new ClienteDAO(entityManager);

        Produto produto = produtoDao.buscarPorId(1l);
        Produto produto2 = produtoDao.buscarPorId(2l);
        Produto produto3 = produtoDao.buscarPorId(3l);
        Cliente cliente = clienteDAO.buscarPorId(1l);

        entityManager.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));
        pedido.adicionarItem(new ItemPedido(40, pedido, produto2));

        Pedido pedido2 = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(2, pedido, produto3));

        PedidoDAO pedidoDAO = new PedidoDAO(entityManager);
        pedidoDAO.cadastrar(pedido);
        pedidoDAO.cadastrar(pedido2);

        entityManager.getTransaction().commit();

        BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
        System.out.println("TOTAL VENDIDO: " + totalVendido);

        List<RelatorioVendasVO> relatorio = pedidoDAO.relatorioVendas();
        relatorio.forEach(System.out::println);
    }

    private static void popularBD() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videogames = new Categoria("VIDEOGAMES");
        Categoria informatica = new Categoria("INFORMATICA");
        Produto celular = new Produto("Xiaomi Redmi", "Muito Legal", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("PS5", "Playstation", new BigDecimal("1800"), videogames);
        Produto macbook = new Produto("Macbook", "Playstation", new BigDecimal("3000"), informatica);

        Cliente cliente = new Cliente("Thais", "123456789");

        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDAO produtoDao = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDao = new CategoriaDAO(entityManager);
        ClienteDAO clienteDAO = new ClienteDAO(entityManager);

        entityManager.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogames);
        categoriaDao.cadastrar(informatica);

        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(videogame);
        produtoDao.cadastrar(macbook);

        clienteDAO.cadastrar(cliente);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
