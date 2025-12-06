import org.example.exceptions.DAOException;
import org.example.jpa.ClienteJpa;
import org.example.jpa.dao.ClienteDaoJpa;
import org.example.jpa.dao.ClienteDaoJpa2;
import org.example.jpa.dao.ClienteDaoJpa3;
import org.example.jpa.dao.IClienteDaoJpa;
import org.junit.After;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;

public class ClienteJpa2BancosTest {

    private IClienteDaoJpa<ClienteJpa> clienteDao;
    private IClienteDaoJpa<ClienteJpa> clienteDao2;
    private IClienteDaoJpa<ClienteJpa> clienteDao3;

    private Random rd;

    public ClienteJpa2BancosTest() {
        this.clienteDao = new ClienteDaoJpa();
        this.clienteDao2 = new ClienteDaoJpa2();
        this.clienteDao3 = new ClienteDaoJpa3();
        this.rd = new Random();
    }

    @After
    public void end() throws DAOException {
        Collection<ClienteJpa> list1 = this.clienteDao.buscarTodosJpa();
        list1.forEach(cliente -> {
            try {
                this.clienteDao.excluirJpa(cliente);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });

        Collection<ClienteJpa> list2 = this.clienteDao2.buscarTodosJpa();

        list2.forEach(cliente -> {
            try{
                this.clienteDao2.excluirJpa(cliente);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });

        Collection<ClienteJpa> list3 = this.clienteDao3.buscarTodosJpa();

        list3.forEach(cliente -> {
            try {
                this.clienteDao3.excluirJpa(cliente);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisarCliente() throws DAOException {
        ClienteJpa cliente = this.criarCliente();
        clienteDao.cadastrarJpa(cliente);

        ClienteJpa clienteConsultado = this.clienteDao.consultarJpa(cliente.getId());

        assert clienteConsultado != null;

        cliente.setId(null);
        this.clienteDao2.cadastrarJpa(cliente);

        ClienteJpa clienteConsultado2 = this.clienteDao2.consultarJpa(cliente.getId());
        assert clienteConsultado2 != null;

        cliente.setId(null);
        this.clienteDao3.cadastrarJpa(cliente);

        ClienteJpa clienteConsultado3 = this.clienteDao3.consultarJpa(cliente.getId());
        assert clienteConsultado3 != null;
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
