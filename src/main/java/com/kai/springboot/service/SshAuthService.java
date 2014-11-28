package com.kai.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kai.springboot.domain.OperatorRepository;
import com.kai.springboot.domain.ShieldServer;
import com.kai.springboot.domain.ShieldServerRepository;
import com.kai.springboot.domain.ShieldUser;
import com.kai.springboot.domain.ShieldUserRepository;

@Service("sshAuthService")
public class SshAuthService {

	@Autowired
	private ShieldServerRepository shieldServerRepository;

	@Autowired
	private ShieldUserRepository shieldUserRepository;

	@Autowired
	private OperatorRepository operatorRepository;

	public List<ShieldServer> listAllShieldServers() {
		return (List<ShieldServer>) shieldServerRepository.findAll();
	}

	public Page<ShieldServer> listAllShieldServers(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return shieldServerRepository.findAll(pageRequest);
	}

	/**
	 * 增加一个受保护的服务器
	 * 
	 * @param host
	 * @param port
	 */
	public ShieldServer createShieldServer(String host, int port) {
		ShieldServer shieldServer = new ShieldServer(host, port);
		shieldServerRepository.save(shieldServer);
		return shieldServer;
	}

	/**
	 * 为受保护服务器添加一个用户
	 * 
	 * @param shieldServerId
	 * @param username
	 * @param password
	 */
	public void addShieldServerUser(Long shieldServerId, String username,
			String password) {
		ShieldServer shieldServer = shieldServerRepository
				.findOne(shieldServerId);
		ShieldUser shieldUser = new ShieldUser();
		shieldUser.setUsername(username);
		shieldUser.setPassword(password);
		shieldUser.setShieldServer(shieldServer);
		shieldServerRepository.save(shieldServer);
	}

	/**
	 * 使用用户名和密码创建一个操作员
	 * 
	 * @param username
	 * @param password
	 */
	public void addOperator(String username, String password) {

	}

	/**
	 * 将操作员与受保护的服务器用户关联起来
	 * 
	 * @param shieldServerId
	 * @param shieldUserId
	 * @param operatorId
	 */
	public void addSheldServerUserToOperator(Long shieldServerId,
			Long shieldUserId, Long operatorId) {

	}

}
