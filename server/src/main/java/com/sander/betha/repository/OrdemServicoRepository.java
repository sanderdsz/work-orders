package com.sander.betha.repository;

import com.sander.betha.models.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    // Método de procura de ordem via ID
    OrdemServico findById(long id);

}
