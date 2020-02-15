package org.plushy.factoryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.plushy.factoryapi.models.Parameter;
import org.plushy.factoryapi.models.ParameterEventId;

public interface ParameterRepository extends JpaRepository<Parameter, ParameterEventId> {

}
