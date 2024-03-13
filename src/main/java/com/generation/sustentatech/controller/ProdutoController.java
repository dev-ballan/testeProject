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

import com.generation.sustentatech.model.Produto;
import com.generation.sustentatech.repository.CategoriaRepository;
import com.generation.sustentatech.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin ("*")
public class ProdutoController {

@Autowired
 private ProdutoRepository produtoRepository;

@Autowired
private CategoriaRepository categoriaRepository;
 	
@GetMapping("/all")
public ResponseEntity<List<Produto>> getAll(){
	return ResponseEntity.ok(produtoRepository.findAll());
	
}

@GetMapping("/descricao/{descricao}")
public ResponseEntity<List<Produto>> getByDescricao(@PathVariable String descricao){
	return ResponseEntity.ok(produtoRepository.findAllByDescricaoContainingIgnoreCase(descricao));
}

@GetMapping("/{id}")
public ResponseEntity<Produto> getById(@PathVariable Long id){
	return produtoRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
}

@GetMapping("/nome/{nome}")
public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
	return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
}

@PostMapping
public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
    return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	
}

@PutMapping
public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto){
	return produtoRepository.findById((produto.getId()))
			.map(obj -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
}	

@DeleteMapping("/{id}")
public ResponseEntity<Produto> deleteProduto(@PathVariable Long id){
	if (produtoRepository.existsById(id)) {
		produtoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	return ResponseEntity.notFound().build();
}
	

}
