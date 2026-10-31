package br.com.matheusvictor.gremio.controller;

import br.com.matheusvictor.gremio.entity.Candidato;
import br.com.matheusvictor.gremio.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidatos")
@CrossOrigin("*")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping("/")
    public List<Candidato> findAll() {
        return candidatoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> findById(@PathVariable Long id) {
        return candidatoService.findById(id)
                .map(ResponseEntity :: ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidato> create (@RequestBody Candidato candidato){
        var salvo = candidatoService.create(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> update (@PathVariable Long id, @RequestBody Candidato candidato){
        return candidatoService.update(id, candidato)
                .map(ResponseEntity:: ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        return candidatoService.delete(id)
                ?ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Candidato> votar (@PathVariable Long id){
        return candidatoService.votar(id)
                .map(ResponseEntity :: ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
