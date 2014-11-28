package com.kai.springboot.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShieldServerRepository extends
		PagingAndSortingRepository<ShieldServer, Long> {

	ShieldServer findByHostAndPort(String host, int port);

}
