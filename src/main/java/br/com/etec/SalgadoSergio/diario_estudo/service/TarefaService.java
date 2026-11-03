package br.com.etec.SalgadoSergio.diario_estudo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.etec.SalgadoSergio.diario_estudo.entity.Tarefa;
import br.com.etec.SalgadoSergio.diario_estudo.repository.TarefaRepository;

@Service
public class TarefaService {

    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tarefa> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Tarefa salvar(Tarefa tarefa) {
        calcularPrioridade(tarefa);
        return repository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizar(Long id, Tarefa tarefa) {
        Tarefa existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));
        existente.setTitulo(tarefa.getTitulo());
        existente.setMateria(tarefa.getMateria());
        existente.setDataEntrega(tarefa.getDataEntrega());
        existente.setStatus(tarefa.getStatus());
        calcularPrioridade(existente);
        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public Tarefa concluir(Long id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));
        tarefa.setStatus("CONCLUIDA");
        return repository.save(tarefa);
    }

    private void calcularPrioridade(Tarefa tarefa) {
        if (tarefa.getDataEntrega() == null) {
            tarefa.setPrioridade("BAIXA");
            return;
        }
        long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), tarefa.getDataEntrega());
        if (diasRestantes <= 2) {
            tarefa.setPrioridade("ALTA");
        } else if (diasRestantes <= 5) {
            tarefa.setPrioridade("MEDIA");
        } else {
            tarefa.setPrioridade("BAIXA");
        }
    }
}
