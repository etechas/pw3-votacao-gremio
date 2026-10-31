package br.com.matheusvictor.gremio.repository;

import br.com.matheusvictor.gremio.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    List<Candidato> findAllByOrderByQuantidadeVotosDesc();
    List<Candidato> findByCategoria(String categoria);

}
