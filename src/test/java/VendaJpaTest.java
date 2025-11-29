import org.example.domain.Venda;
import org.example.exceptions.DAOException;
import org.example.exceptions.MaisDeUmRegistroException;
import org.example.exceptions.TableException;
import org.example.exceptions.TipoChaveNaoEncontradaException;
import org.example.jpa.ClienteJpa;
import org.example.jpa.ProdutoJpa;
import org.example.jpa.ProdutoQuantidadeJpa;
import org.example.jpa.VendaJpa;
import org.example.jpa.dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class VendaJpaTest {

    private VendaDaoJpa vendaDao;
    private ClienteDaoJpa clienteDao;
    private ProdutoDaoJpa produtoDao;
    private ProdutoQuantidadeDaoJpa produtoQuantidadeDao;
    private Random rd;
    private VendaExcluirDaoJpa vendaExcluirDao;
    private ClienteJpa cliente;
    private ProdutoJpa produto;

    public VendaJpaTest() {
        this.vendaDao = new VendaDaoJpa();
        this.rd = new Random();
        this.clienteDao = new ClienteDaoJpa();
        this.vendaExcluirDao = new VendaExcluirDaoJpa();
        this.produtoDao = new ProdutoDaoJpa();
        this.produtoQuantidadeDao = new ProdutoQuantidadeDaoJpa();
    }

    @Before
    public void init() throws DAOException {
        this.cliente =criarCliente();
        this.produto = criarProduto("A1", BigDecimal.TEN);

    }

    @After
    public void end() throws DAOException {
        this.excluirProduto();
        this.excluirVendas();
        this.clienteDao.excluirJpa(this.cliente);
    }

    @Test
    public void pesquisar() throws DAOException {
        VendaJpa venda = this.criarVenda("A1");
        VendaJpa retorno = this.vendaDao.cadastrarJpa(venda);
        assert retorno != null;

        VendaJpa vendaConsultada = this.vendaDao.consultarJpa(venda.getId());
        assert vendaConsultada != null;
        assert vendaConsultada.getCodigo().equals(venda.getCodigo());

    }

    @Test
    public void salvar() throws DAOException {
        VendaJpa venda = criarVenda("A2");
        VendaJpa retorno = this.vendaDao.cadastrarJpa(venda);

        assert retorno != null;
        assert venda.getValorTotal().equals(BigDecimal.valueOf(20));
        assert venda.getStatus().equals(VendaJpa.Status.INICIADA);

        VendaJpa vendaConsultada = this.vendaDao.consultarJpa(venda.getId());

        assert vendaConsultada.getId() != null;
        assert vendaConsultada.getCodigo().equals(venda.getCodigo());


    }

    @Test
    public void cancelarVenda() throws DAOException, TipoChaveNaoEncontradaException {
        String codigo = "A3";
        VendaJpa venda = this.criarVenda(codigo);
        VendaJpa retorno = this.vendaDao.cadastrarJpa(venda);

        assert retorno != null;
        assert venda.getCodigo().equals(codigo);

        retorno.setStatus(VendaJpa.Status.CANCELADA);
        this.vendaDao.cancelarVenda(retorno);

        VendaJpa vendaConsultada = this.vendaDao.consultarJpa(retorno.getId());
        assert vendaConsultada.getCodigo().equals(codigo);

    }

    @Test
    public void adicionarMaisProdutoDoMesmo() throws DAOException {
        String codigo = "A4";
        VendaJpa venda = criarVenda(codigo);
        VendaJpa retorno = this.vendaDao.cadastrarJpa(venda);

        assert retorno != null;
        assert retorno.getCodigo().equals(codigo);

        VendaJpa vendaConsultada = this.vendaDao.consultarComCollection(retorno.getId());
        vendaConsultada.adicionarProduto(this.produto, 1);

        assert vendaConsultada.getQuantidadeTotalProdutos().equals(3);
        BigDecimal valorTotal = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_DOWN);
        assert vendaConsultada.getValorTotal().equals(valorTotal);
        assert vendaConsultada.getStatus().equals(VendaJpa.Status.INICIADA);

    }

    @Test
    public void adicionarMaisProdutosDiferentes() throws DAOException {
        String codigo = "A5";
        VendaJpa venda = criarVenda(codigo);
        VendaJpa retorno = this.vendaDao.cadastrarJpa(venda);

        assert retorno != null;
        assert retorno.getCodigo().equals(codigo);

        ProdutoJpa prod = this.criarProduto(codigo, BigDecimal.valueOf(50));


        assert prod.getCodigo().equals(codigo);

        VendaJpa vendaConsultada = this.vendaDao.consultarComCollection(retorno.getId());
        vendaConsultada.adicionarProduto(prod, 1);

        assert vendaConsultada.getQuantidadeTotalProdutos().equals(3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assert vendaConsultada.getValorTotal().equals(valorTotal);
        assert vendaConsultada.getStatus().equals(VendaJpa.Status.INICIADA);


    }

    @Test(expected = DAOException.class)
    public void salvarVendaComMesmoCodigoExistente() throws DAOException {
        VendaJpa venda = criarVenda("A6");
        VendaJpa retorno = this.vendaDao.cadastrarJpa(venda);

        assert retorno != null;

        VendaJpa venda1 = criarVenda("A6");
        VendaJpa retorno1 = this.vendaDao.cadastrarJpa(venda1);

        assert retorno1 != null;
        assert retorno.getStatus().equals(VendaJpa.Status.INICIADA);

    }

    @Test
    public void removerProduto() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A7";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.cadastrarJpa(venda);
        assert retorno!= null;
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        ProdutoJpa prod = criarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        VendaJpa vendaConsultada = vendaDao.consultarJpa(retorno.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertEquals(3, (int) vendaConsultada.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);


        vendaConsultada.removerProduto(prod, 1);
        assertEquals(2, (int) vendaConsultada.getQuantidadeTotalProdutos());
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);
        assertEquals(VendaJpa.Status.INICIADA, vendaConsultada.getStatus());
    }

    @Test
    public void removerApenasUmProduto() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A8";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.cadastrarJpa(venda);
        assert retorno != null;
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        ProdutoJpa prod = criarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        VendaJpa vendaConsultada = vendaDao.consultarJpa(retorno.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));


        vendaConsultada.removerProduto(prod, 1);
        assertEquals(2, (int) vendaConsultada.getQuantidadeTotalProdutos());
        valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);
        assertEquals(VendaJpa.Status.INICIADA, vendaConsultada.getStatus());
    }

    @Test
    public void removerTodosProdutos() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A9";
        VendaJpa venda = criarVenda(codigoVenda);
         VendaJpa retorno = vendaDao.cadastrarJpa(venda);
        assert retorno != null;
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        ProdutoJpa prod = criarProduto(codigoVenda, BigDecimal.valueOf(50));
        assertNotNull(prod);
        assertEquals(codigoVenda, prod.getCodigo());

        VendaJpa vendaConsultada = vendaDao.consultarJpa(retorno.getId());
        vendaConsultada.adicionarProduto(prod, 1);
        assertEquals(3, (int) vendaConsultada.getQuantidadeTotalProdutos());
        BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
        assertEquals(vendaConsultada.getValorTotal(), valorTotal);


        vendaConsultada.removerTodosProdutos();
        assertEquals(0, (int) vendaConsultada.getQuantidadeTotalProdutos());
        assertEquals(vendaConsultada.getValorTotal(), BigDecimal.valueOf(0));
        assertEquals(VendaJpa.Status.INICIADA, vendaConsultada.getStatus());
    }

    @Test
    public void finalizarVenda() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A10";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.cadastrarJpa(venda);
        assert retorno != null;
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        vendaDao.finalizarVenda(venda);

        Venda vendaConsultada = vendaDao.consultarJpa(retorno.getId());
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
        assertEquals(VendaJpa.Status.CONCLUIDA, vendaConsultada.getStatus());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void tentarAdicionarProdutosVendaFinalizada() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        String codigoVenda = "A11";
        VendaJpa venda = criarVenda(codigoVenda);
        VendaJpa retorno = vendaDao.cadastrarJpa(venda);
        assert retorno != null;
        assertNotNull(venda);
        assertEquals(codigoVenda, venda.getCodigo());

        vendaDao.finalizarVenda(venda);
        VendaJpa vendaConsultada = vendaDao.consultarJpa(retorno.getId());
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
        assertEquals(VendaJpa.Status.CONCLUIDA, vendaConsultada.getStatus());

        vendaConsultada.adicionarProduto(this.produto, 1);

    }

    private ClienteJpa criarCliente() throws DAOException {
        ClienteJpa cliente = new ClienteJpa();
        cliente.setCpf(String.valueOf(rd.nextLong()));
        cliente.setNome("Alessandro");
        cliente.setCidade("cidade test");
        cliente.setEnd("Rio de Janeiro");
        cliente.setEstado("RJ");
        cliente.setNumero(12);
        cliente.setTel(21421311231L);
        cliente.setPais("Brasil");
        this.clienteDao.cadastrarJpa(cliente);
        return cliente;

    }

    private ProdutoJpa criarProduto(String codigo, BigDecimal valor) throws DAOException {
        ProdutoJpa produto = new ProdutoJpa();
        produto.setCodigo(codigo);
        produto.setDescricao("descrição");
        produto.setNome("nome");
        produto.setValor(valor);

        this.produtoDao.cadastrarJpa(produto);

        return produto;
    }

    private VendaJpa criarVenda(String codigo) {
        VendaJpa venda = new VendaJpa();
        venda.setCodigo(codigo);
        venda.setDataVenda(Instant.now());
        venda.setCliente(this.cliente);
        venda.setStatus(VendaJpa.Status.INICIADA);
        venda.adicionarProduto(this.produto, 2);
        return venda;
    }

    private void excluirVendas() throws DAOException {
        Collection<VendaJpa> list = this.vendaExcluirDao.buscarTodosJpa();
        list.forEach(venda -> {
            try {
                this.vendaExcluirDao.excluirJpa(venda);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void excluirProduto() throws DAOException {
        Collection<ProdutoJpa> list = this.produtoDao.buscarTodosJpa();
        Collection<ProdutoQuantidadeJpa> list1 = this.produtoQuantidadeDao.buscarTodosJpa();
        list1.forEach(qtd-> {
            try {
                this.produtoQuantidadeDao.excluirJpa(qtd);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        });


        list.forEach(prod -> {
            try {

                this.produtoDao.excluirJpa(prod);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
