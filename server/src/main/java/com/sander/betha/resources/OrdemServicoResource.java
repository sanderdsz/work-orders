package com.sander.betha.resources;

import com.sander.betha.models.OrdemServico;

import com.sander.betha.repository.OrdemServicoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
//Identificando como REST API
@RestController
@RequestMapping(value="/api")
@Api(value="API REST de ordens de serviço")
public class OrdemServicoResource {

    @Autowired
    OrdemServicoRepository ordemServicoRepository;

    @ApiOperation(value = "Retorna lista de ordens")
    // Lista todos as ordens de serviços
    @GetMapping("/ordens")
    public List<OrdemServico> listaOrdens(){
        return ordemServicoRepository.findAll();
    }

    // Lista apenas a ordem via ID
    @GetMapping("/ordem/{id}")
    @ApiOperation(value = "Retorna única ordem referente ao ID")
    public OrdemServico listaOrdem(@PathVariable(value = "id") long id)  {
        return ordemServicoRepository.findById(id);
    }

     // Posta Ordens no banco de dados
    @PostMapping("/ordem")
    @ApiOperation(value = "Insere ordem no banco de dados")
    public OrdemServico salvaOrdem(@RequestBody @Valid OrdemServico ordemServico){
        return ordemServicoRepository.save(ordemServico);
    }

    // Deleta Ordens no banco de dados pelo ID
    @DeleteMapping("/ordem")
    @ApiOperation(value = "Deleta ordem no banco de dados via ID")
    public void deletaOrdem(@RequestBody @Valid OrdemServico ordemServico){
        ordemServicoRepository.delete(ordemServico);
    }

    // Atualiza produto
    @PutMapping("/ordem")
    @ApiOperation(value = "Atualiza ordem no banco de dados via ID")
    public OrdemServico atualizaOrdem(@RequestBody @Valid OrdemServico ordemServico){
        return ordemServicoRepository.save(ordemServico);
    }
}



