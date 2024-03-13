package com.generation.sustentatech.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.sustentatech.model.Categoria;
import com.generation.sustentatech.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin ("*")
public class CategoriaController {

@Autowired
private CategoriaRepository categoriaRepository;
 	
@GetMapping 
public ResponseEntity<List<Categoria>> getAll(){
	
	return ResponseEntity.ok(categoriaRepository.findAll());
	
}

@GetMapping("/descricao/{descricao}")
public ResponseEntity<List<Categoria>> getByDescricao(@PathVariable String descricao){
	return ResponseEntity.ok(categoriaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
}

@GetMapping("/{id}")
public ResponseEntity<Categoria> getById(@PathVariable Long id){
	return categoriaRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
}

@GetMapping("/tipo/{tipo}")
public ResponseEntity<List<Categoria>> getByTipo(@PathVariable String tipo){
	return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
}

@PostMapping
public ResponseEntity<Categoria> postCategoria(@Valid @RequestBody Categoria categoria){
	return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	
}

@PutMapping
public ResponseEntity<Categoria> putCategoria(@Valid @RequestBody Categoria categoria){
	return categoriaRepository.findById(categoria.getId())
			.map(resposta -> {
				return ResponseEntity.ok().body(categoriaRepository.save(categoria));
			})
			.orElse(ResponseEntity.notFound().build());
}	

@DeleteMapping("/{id}")
public ResponseEntity<Categoria> deleteCategoria(@PathVariable Long id){
	if (categoriaRepository.existsById(id)) {
		categoriaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	return ResponseEntity.notFound().build();
}
	

}
