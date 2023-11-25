package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.smartsync.LogTarefa;

public interface LogTarefaRepository extends JpaRepository<LogTarefa, Integer> {
    
}
