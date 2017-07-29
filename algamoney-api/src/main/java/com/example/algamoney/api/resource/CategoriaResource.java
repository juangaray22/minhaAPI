package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repositoty.CategoriaRepository;

/**
 * 
 * @author juang Request controler , controle rest
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	@Autowired
	private ApplicationEventPublisher publish;

	/*
	 * @PostMapping
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public void criar(@RequestBody Categoria
	 * categoria) { Categoria categoriaSalva = categoriaRepository.save(categoria);
	 * }
	 */

	/*
	 * @PostMapping
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public void criar(@RequestBody Categoria
	 * categoria) { Categoria categoriaSalva = categoriaRepository.save(categoria);
	 * }
	 */

	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publish.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscaPeloCodigo(@PathVariable Long codigo) {
		Categoria categoriaEncontrada = categoriaRepository.findOne(codigo);

		if (categoriaEncontrada == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(categoriaEncontrada);
		}
	}

	/*
	 * @GetMapping public ResponseEntity<?> listar(){ List<Categoria> categorias =
	 * categoriaRepository.findAll(); return !categorias.isEmpty() ?
	 * ResponseEntity.ok(categorias) : ResponseEntity.noContent().build(); }
	 */
}
