package org.plushy.factoryapi.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

import org.plushy.factoryapi.models.ParameterEvent;
import org.plushy.factoryapi.models.ParameterEventId;

public interface ParameterEventRepository extends JpaRepository<ParameterEvent, ParameterEventId> {

    @Query(value = "SELECT pe " + "FROM ParameterEvent pe " + "WHERE pe.parameter.parameterKey = :parameterKey AND "
            + "pe.parameter.machineKey = :machineKey " + "AND pe.dateTime <= :end AND pe.dateTime >= :start "
            + "ORDER BY CAST(pe.value as double)")
    List<ParameterEvent> findAllParameterEvents(String machineKey, String parameterKey, LocalDateTime start,
            LocalDateTime end);

    @Query("SELECT pe " + "FROM ParameterEvent pe WHERE pe.parameter.machineKey = :machineKey "
            + "AND pe.parameter.parameterKey = :parameterKey " + "ORDER BY pe.dateTime DESC")
    List<ParameterEvent> getLatest(String machineKey, String parameterKey, Pageable pageable);

    @Query("SELECT pe " + "FROM ParameterEvent pe WHERE pe.parameter.machineKey = :machineKey "
            + "AND pe.parameter.parameterKey = :parameterKey " + "ORDER BY pe.dateTime DESC")
    List<ParameterEvent> getLatest(String machineKey, String parameterKey);
}
