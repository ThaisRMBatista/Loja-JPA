package br.com.trmb.loja.testes;

import br.com.trmb.loja.dao.CategoriaDAO;
import br.com.trmb.loja.dao.ProdutoDAO;
import br.com.trmb.loja.modelo.Categoria;
import br.com.trmb.loja.modelo.Produto;
import br.com.trmb.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {
        cadastrarProduto();

        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDAO produtoDao = new ProdutoDAO(entityManager);
        List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
        todos.forEach(produto -> System.out.println(produto.getNome()));

        BigDecimal precoProduto = produtoDao.buscarPrecoDoProdutoComNome("Xiami Redmi");
        System.out.println("Preço do produto " + precoProduto);

    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiami Redmi", "Muito Legal", new BigDecimal("800"), celulares);

        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDAO produtoDao = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDao = new CategoriaDAO(entityManager);

        entityManager.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
