import org.example.exceptions.DAOException;
import org.example.exceptions.TipoChaveNaoEncontradaException;
import org.example.jpa.ClienteJpa;
import org.example.jpa.dao.ClienteDaoJpa;
import org.junit.After;
import org.junit.Test;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;

public class ClienteJpaTest {

    private ClienteDaoJpa dao;

    private Random rd;

    public ClienteJpaTest () {
        this.dao = new ClienteDaoJpa();
        this.rd = new Random();
    }

    @After
    public void end() throws DAOException {
        Collection<ClienteJpa> list = this.dao.buscarTodosJpa();
        list.forEach(cli -> {
            try {
                this.dao.excluirJpa(cli);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisarCliente() throws DAOException {
        ClienteJpa cliente = criarCliente();
        this.dao.cadastrarJpa(cliente);

        ClienteJpa clienteJpa = this.dao.consultarJpa(cliente.getId());

        assert clienteJpa != null;

    }

    @Test
    public void salvarCliente() throws DAOException {
        ClienteJpa cliente = criarCliente();
        ClienteJpa clienteRetorno = this.dao.cadastrarJpa(cliente);

        assert clienteRetorno != null;

        ClienteJpa clienteConsultado = this.dao.consultarJpa(clienteRetorno.getId());

        assert clienteConsultado != null;

        this.dao.excluirJpa(cliente);

        ClienteJpa clienteConsultado1 = this.dao.consultarJpa(clienteRetorno.getId());

        assert clienteConsultado1 == null;
    }

    @Test
    public void alterarCliente() throws DAOException, TipoChaveNaoEncontradaException {
        ClienteJpa cliente = criarCliente();
        ClienteJpa retorno = this.dao.alterarJpa(cliente);

        assert retorno != null;

        ClienteJpa clienteConsultado = this.dao.consultarJpa(retorno.getId());

        clienteConsultado.setNome("Alessandro Rangel");
        this.dao.alterarJpa(clienteConsultado);

        ClienteJpa clienteAlterado = this.dao.consultarJpa(clienteConsultado.getId());

        assert clienteAlterado != null;

        assert clienteAlterado.getNome().equals("Alessandro Rangel");

        this.dao.excluirJpa(cliente);

        clienteConsultado = this.dao.consultarJpa(clienteAlterado.getId());

        assert clienteConsultado == null;
    }

    @Test
    public void buscarTodos() throws DAOException, TipoChaveNaoEncontradaException {
        ClienteJpa cliente = criarCliente();
        ClienteJpa retorno = this.dao.cadastrarJpa(cliente);

        assert retorno != null;

        ClienteJpa cliente1 = criarCliente();
        ClienteJpa retorno1 = this.dao.cadastrarJpa(cliente1);

        assert retorno1 != null;

        Collection<ClienteJpa> list = this.dao.buscarTodosJpa();

        assert list != null;
        assert list.size() == 2;

        list.forEach(clienteJpa -> {
            try {
                this.dao.excluirJpa(clienteJpa);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });

        Collection<ClienteJpa> list1 = this.dao.buscarTodosJpa();

        assert list1 != null;
        assert list1.isEmpty();


    }

    private ClienteJpa criarCliente() {
        ClienteJpa cliente = new ClienteJpa();
        cliente.setCpf(String.valueOf(rd.nextLong()));
        cliente.setNome("Alessandro");
        cliente.setCidade("cidade test");
        cliente.setEnd("Rio de Janeiro");
        cliente.setEstado("RJ");
        cliente.setNumero(12);
        cliente.setTel(21421311231L);
        cliente.setPais("Brasil");

        return cliente;

    }
}
