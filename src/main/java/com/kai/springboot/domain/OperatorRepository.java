package com.kai.springboot.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface OperatorRepository extends CrudRepository<Operator, Long> {

}
