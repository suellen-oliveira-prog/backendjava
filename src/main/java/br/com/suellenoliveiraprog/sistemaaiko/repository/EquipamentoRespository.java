package br.com.suellenoliveiraprog.sistemaaiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.suellenoliveiraprog.sistemaaiko.model.Equipamento;

@Repository
public interface EquipamentoRespository extends JpaRepository<Equipamento, Long>{
    
}
