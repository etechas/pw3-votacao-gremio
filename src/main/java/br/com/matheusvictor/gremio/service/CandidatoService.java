package br.com.matheusvictor.gremio.service;

import br.com.matheusvictor.gremio.entity.Candidato;
import br.com.matheusvictor.gremio.enums.SimNaoEnum;
import br.com.matheusvictor.gremio.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public List<Candidato> findAll() {
        return candidatoRepository.findAllByOrderByQuantidadeVotosDesc();
        }

        public Optional<Candidato> findById(Long id) {
        return candidatoRepository.findById(id);
        }

        public Candidato create(Candidato candidato) {
            candidato.setId(null);
            candidato.setQuantidadeVotos(0);
            candidato.setEleito(SimNaoEnum.N);
            return candidatoRepository.save(candidato);
        }

        @Transactional
        public Optional<Candidato> update(Long id, Candidato dados) {
            return candidatoRepository.findById(id).map(candidato -> {
                String categoriaAntiga = candidato.getCategoria();
                candidato.setNome(dados.getNome());
                candidato.setCategoria(dados.getCategoria());
                candidatoRepository.save(candidato);
                if (categoriaAntiga != null && !categoriaAntiga.equals(candidato.getCategoria())) {
                    recalcularEleitos(categoriaAntiga);
                }
                recalcularEleitos(candidato.getCategoria());

                return candidato;
            });
        }

        @Transactional
        public boolean delete(Long id) {
            return candidatoRepository.findById(id).map(candidato -> {
                candidatoRepository.delete(candidato);
                recalcularEleitos(candidato.getCategoria());
                return true;
            }).orElse(false);
        }

        @Transactional
        public Optional<Candidato> votar(Long id) {
            return candidatoRepository.findById(id).map(candidato -> {
                candidato.setQuantidadeVotos(candidato.getQuantidadeVotos() + 1);
                candidatoRepository.save(candidato);

                recalcularEleitos(candidato.getCategoria());

                return candidato;
            });
        }

        private void recalcularEleitos(String categoria) {
            var todosDaCategoria = candidatoRepository.findByCategoria(categoria);

            int maiorVotacao = todosDaCategoria.stream()
                    .mapToInt(Candidato::getQuantidadeVotos)
                    .max()
                    .orElse(0);

            for (Candidato c : todosDaCategoria) {
                boolean eleito = maiorVotacao > 0 && c.getQuantidadeVotos() == maiorVotacao;
                c.setEleito(eleito ? SimNaoEnum.S : SimNaoEnum.N);
            }
            candidatoRepository.saveAll(todosDaCategoria);
        }
    }


