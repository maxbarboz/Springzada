package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController // converte para JSON
@RequestMapping("/categorias") // mapeando a requisição
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll(); // fazendo select na tabela
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
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
		*/
	}
	
	@GetMapping("/{codigo}")
	//public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		//return categoriaRepository.findOne(codigo);

		// não retorna o desejável
		
		 Categoria categoria = categoriaRepository.findById(codigo).get();
		 return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
		//return categoriaRepository.findById(codigo).get() === null ? categoriaRepository.findById(codigo).get() : ;
		//return categoriaRepository.getOne(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategoria(@PathVariable Long codigo) {
		categoriaRepository.delete(categoriaRepository.getOne(codigo));
	}
	
	
}