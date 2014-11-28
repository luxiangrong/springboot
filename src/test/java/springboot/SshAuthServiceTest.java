package springboot;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kai.springboot.SshConfiguration;
import com.kai.springboot.domain.ShieldServer;
import com.kai.springboot.domain.ShieldServerRepository;
import com.kai.springboot.domain.ShieldUser;
import com.kai.springboot.domain.ShieldUserRepository;
import com.kai.springboot.service.SshAuthService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SshConfiguration.class)
public class SshAuthServiceTest {

	@Autowired
	private SshAuthService sshAuthService;

	@Autowired
	private ShieldServerRepository shieldServerRepository;

	@Autowired
	private ShieldUserRepository shieldUserRepository;

	@Before
	public void before() {
		// 受保护主机1：192.168.0.1:22,拥有2个用户
		ShieldServer shieldServer1 = new ShieldServer();
		shieldServer1.setHost("192.168.0.1");
		shieldServer1.setPort(22);
		ShieldUser shieldUser1 = new ShieldUser();
		shieldUser1.setUsername("user1");
		shieldUser1.setPassword("passowrd1");
		shieldUser1.setShieldServer(shieldServer1);
		shieldServer1.addShieldUser(shieldUser1);
		ShieldUser shieldUser2 = new ShieldUser();
		shieldUser2.setUsername("user2");
		shieldUser2.setPassword("passowrd2");
		shieldUser2.setShieldServer(shieldServer1);
		shieldServer1.addShieldUser(shieldUser2);

		// 受保护主机2：192.168.0.2:22,拥有1个用户
		ShieldServer shieldServer2 = new ShieldServer();
		shieldServer2.setHost("192.168.0.2");
		shieldServer2.setPort(22);
		ShieldUser shieldUser3 = new ShieldUser();
		shieldUser3.setUsername("user2");
		shieldUser3.setPassword("passowrd2");
		shieldUser3.setShieldServer(shieldServer2);
		shieldServer2.addShieldUser(shieldUser3);

		// 受保护主机3：192.168.0.3:22,没有用户
		ShieldServer shieldServer3 = new ShieldServer();
		shieldServer3.setHost("192.168.0.3");
		shieldServer3.setPort(22);

		shieldServerRepository.save(shieldServer1);
		shieldServerRepository.save(shieldServer2);
		shieldServerRepository.save(shieldServer3);
	}

	@After
	public void after() {
		shieldServerRepository.deleteAll();
	}

	@Test
	public void testListAllShieldServers() {
		List<ShieldServer> shieldServers = sshAuthService
				.listAllShieldServers();
		assertEquals(3, shieldServers.size());

		List<ShieldUser> shieldUsers = (List<ShieldUser>) shieldUserRepository
				.findAll();
		assertEquals(3, shieldUsers.size());
	}

	@Test
	public void testCreateShieldServerSuccess() {
		ShieldServer savedShieldServer = sshAuthService.createShieldServer(
				"192.168.3.1", 22);
		ShieldServer selectedServer = shieldServerRepository.findByHostAndPort(
				"192.168.3.1", 22);
		assertEquals(savedShieldServer.getId(), selectedServer.getId());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testCreatShieldServerErrorWithSameHostAndPort() {
		sshAuthService.createShieldServer("192.168.0.1", 22);
	}

	@Test
	public void testAddShieldServerUser() {
		ShieldServer shieldServer = sshAuthService.createShieldServer(
				"192.168.3.77", 22);
		sshAuthService.addShieldServerUser(shieldServer.getId(), "shieldUser1",
				"123456");
	}

}
