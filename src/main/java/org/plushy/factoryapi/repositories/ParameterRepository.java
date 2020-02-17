package org.plushy.factoryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.plushy.factoryapi.models.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    Optional<Parameter> findByParameterKeyAndMachineKey(String parameterKey, String machineKey);

    List<Parameter> findAllByType(String type);

    List<Parameter> findAllByMachineKey(String machineKey);
}
