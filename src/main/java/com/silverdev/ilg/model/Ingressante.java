package com.silverdev.ilg.model;

import com.silverdev.ilg.model.enums.Sexo;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by narcizo on 04/10/17.
 */
@Entity
public class Ingressante {

    @Id
    @Column(name= "ingressante_id")
    private Integer id;

    @CPF
    @Column(unique = true, name = "ingressante_cpf")
    private String cpf;

    @Column(name = "ingressante_pos_uem")
    private Integer posUem;

    @Column(name = "ingressante_media")
    private float media;

    @Column(name = "ingressante_sit_entrega")
    private Boolean sit_entrega;

    @Column(name = "ingressante_sit_pagamento")
    private Boolean sit_pagamento;

    /*@Column(unique = true, name = "ingressante_rg")
    private String rg;

    @Column(name = "ingressante_nome")
    private String nome;

    @Column(name = "ingressante_sobrenome")
    private String sobrenome;

    @Enumerated(EnumType.STRING)
    @Column(name = "ingressante_sexo")
    private Sexo sexo;

    @Column(name = "ingressante_telefone")
    private String telefone;

    @Temporal(TemporalType.DATE)
    @Column(name = "ingressante_data_nasc")
    private Date data_nasc;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getPosUem() {
        return posUem;
    }

    public void setPosUem(Integer posUem) {
        this.posUem = posUem;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public Boolean getSit_entrega() {
        return sit_entrega;
    }

    public void setSit_entrega(Boolean sit_entrega) {
        this.sit_entrega = sit_entrega;
    }

    public Boolean getSit_pagamento() {
        return sit_pagamento;
    }

    public void setSit_pagamento(Boolean sit_pagamento) {
        this.sit_pagamento = sit_pagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingressante that = (Ingressante) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
