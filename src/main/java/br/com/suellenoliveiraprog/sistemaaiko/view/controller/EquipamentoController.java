package br.com.suellenoliveiraprog.sistemaaiko.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suellenoliveiraprog.sistemaaiko.services.EquipamentoService;
import br.com.suellenoliveiraprog.sistemaaiko.shared.EquipamentoDTO;
import br.com.suellenoliveiraprog.sistemaaiko.view.model.EquipamentoRequest;
import br.com.suellenoliveiraprog.sistemaaiko.view.model.EquipamentoResponse;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    @GetMapping
    public ResponseEntity<List<EquipamentoResponse>> obterTodosEquipamentos(){
        List<EquipamentoDTO> equipamentos = equipamentoService.obterTodosEquipamentos();

        List<EquipamentoResponse> resposta = equipamentos.stream()
        .map(equipamentoDTO -> new ModelMapper().map(equipamentoDTO, EquipamentoResponse.class))
        .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EquipamentoResponse>> obterEquipamentoPorId(@PathVariable long id){

        Optional<EquipamentoDTO> equipamentoDTO = equipamentoService.obterEquipamentoPorId(id);
        EquipamentoResponse equipamento = new ModelMapper().map(equipamentoDTO.get(), EquipamentoResponse.class); 
        return new ResponseEntity<>(Optional.of(equipamento), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EquipamentoResponse> adicionarEquipamento(@RequestBody EquipamentoRequest equipamentoReq){
        
        EquipamentoDTO equipamentoDTO = new ModelMapper().map(equipamentoReq, EquipamentoDTO.class);
        equipamentoDTO = equipamentoService.adicionarEquipamento(equipamentoDTO);
        return new ResponseEntity<EquipamentoResponse>(new ModelMapper().map(equipamentoDTO, EquipamentoResponse.class), HttpStatus.CREATED);        
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> atualizarEquipamento(@RequestBody EquipamentoRequest equipamentoReq, @PathVariable long id){

        EquipamentoDTO equipamentoDTO = new ModelMapper().map(equipamentoReq, EquipamentoDTO.class);
        equipamentoDTO = equipamentoService.atualizarEquipamento(equipamentoDTO, id);
        return new ResponseEntity<EquipamentoResponse>(new ModelMapper().map(equipamentoDTO, EquipamentoResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEquipamento(@PathVariable long id){
        
        equipamentoService.deletarEquipamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
