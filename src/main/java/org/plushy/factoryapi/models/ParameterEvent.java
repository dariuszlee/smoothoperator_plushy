package org.plushy.factoryapi.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import lombok.Data;

import javax.persistence.GenerationType;

@Data
@Entity
public class ParameterEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({ @JoinColumn(referencedColumnName = "parameterKey", nullable = false),
            @JoinColumn(referencedColumnName = "machineKey", nullable = false) })
    private Parameter parameter;

    private LocalDateTime dateTime;
    private String value;

    public ParameterEvent() {
    }

    public ParameterEvent(final LocalDateTime dateTime, final String value) {
        this.dateTime = dateTime;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(final Parameter parameter) {
        this.parameter = parameter;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}
