package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;

@RestController // converte para JSON
@RequestMapping("/lancamento") // mapeando a requisição
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	/*
	@Autowired
	private ApplicationEventPublisher publisher;
	*/
	
	@GetMapping
	public List<Lancamento> listar(){
		return lancamentoRepository.findAll(); // fazendo select na tabela
	}
	
	/*
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalva = lancamentoRepository.save(lancamento);
		
		//		this = representa que foi gerado para essa classe
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);

		/*
		// recuperação de recurso
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand( categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		// retornando o valor da inserção na tabela
		return ResponseEntity.created( uri ).body( categoriaSalva );
		
	}

	*/
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		
		 Lancamento lancamento = lancamentoRepository.findById(codigo).get();
		 return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	
}