package org.plushy.factoryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.plushy.factoryapi.models.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

}
