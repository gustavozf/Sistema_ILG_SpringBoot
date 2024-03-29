package com.silverdev.ilg.repository;

import com.silverdev.ilg.model.Disputa;
import com.silverdev.ilg.model.enums.PosicaoUEM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisputaRepository extends JpaRepository<Disputa,Integer> {
    List<Disputa> findAllByInscricaoAndAptoOrderByIdTurmaAscMediaDesc(Integer inscricao, boolean apto);
    List<Disputa> findAllByInscricaoAndAptoOrderByNomeIngressanteAsc(Integer inscricao, boolean apto);
    List<Disputa> findByAptoOrderByNomeIngressante(boolean apto);
    List<Disputa> findAllByInscricaoOrderByNomeIngressante(Integer inscricao);
    Disputa findByIdIngressante(Integer id);
    List<Disputa> findAllByCpfIngressanteAndInscricaoAndMatriculado(String cpf, Integer inscricao, boolean matriculado);
}
