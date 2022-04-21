package br.com.suellenoliveiraprog.sistemaaiko.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.suellenoliveiraprog.sistemaaiko.model.Equipamento;
import br.com.suellenoliveiraprog.sistemaaiko.model.exception.ResourceNotFoundException;
import br.com.suellenoliveiraprog.sistemaaiko.repository.EquipamentoRespository;
import br.com.suellenoliveiraprog.sistemaaiko.shared.EquipamentoDTO;

@Service
public class EquipamentoService {
    
    @Autowired
    private EquipamentoRespository equipamentoRepositorio;

    /* Método retorna a lista com todos os equipamentos*/
    public List<EquipamentoDTO> obterTodosEquipamentos(){
        
        //Retorna uma lista de equipamento model
        List<Equipamento> equipamentos = equipamentoRepositorio.findAll();
        
        return equipamentos.stream()
        .map(equipamento -> new ModelMapper().map(equipamento, EquipamentoDTO.class))
        .collect(Collectors.toList());
    }

    /*Método retorna o equipamento pelo seu ID*/
    public Optional<EquipamentoDTO> obterEquipamentoPorId (long id){
        
        //Obtendo optional equipamento pelo ID
        Optional<Equipamento> equipamentos = equipamentoRepositorio.findById(id);

        //se não encontrar lança exception
        if(equipamentos.isEmpty()){
            throw new ResourceNotFoundException("Produto com ID: "+ id + " não localizado!");
        }

        //Convertendo o meu optional de equipamento em EquipamentoDTO
        EquipamentoDTO equipamentosDTO = new ModelMapper().map(equipamentos.get(), EquipamentoDTO.class);

        //Criando e retornando um optional de EquipamentoDTO
        return Optional.of(equipamentosDTO);

    }

    /*Método para adicionar produto na lista*/
    public EquipamentoDTO adicionarEquipamento(EquipamentoDTO equipamentoDTO){
        //Convertendo o equipamento em um EquipamentoDTO
        Equipamento equipamentos = new ModelMapper().map(equipamentoDTO, Equipamento.class);

        //Salvar o equipamento no banco
        equipamentos = equipamentoRepositorio.save(equipamentos);
        equipamentoDTO.setId(equipamentos.getId());

        //Retorna o EquipamentoDTO atualizado
        return equipamentoDTO;
    }

    //Método para atualizar o equipamento
    public EquipamentoDTO atualizarEquipamento(EquipamentoDTO equipamentoDTO, long id){
        
        equipamentoDTO.setId(id);

        Equipamento equipamentos = new ModelMapper().map(equipamentoDTO, Equipamento.class);

        equipamentos = equipamentoRepositorio.save(equipamentos);
        
        return equipamentoDTO;

    }

    /*Método para deletar o produto por ID*/
    public void deletarEquipamento(long id){
        //Verificando se o produto existe
        Optional<Equipamento> equipamentos = equipamentoRepositorio.findById(id);

        //se não encontrar lança exception
        if(equipamentos.isEmpty()){
            throw new ResourceNotFoundException("Produto com ID: "+ id + " não localizado!");
        }

        equipamentoRepositorio.deleteById(id);
        
    }
}
