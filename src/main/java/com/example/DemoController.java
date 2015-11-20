package com.example;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
public class DemoController {

    private final ProductRepository productRepository;
    private final PagedResourcesAssembler<Object> pagedResourcesAssembler;

    @Autowired
    public DemoController(ProductRepository productRepository,
                          PagedResourcesAssembler<Object> pagedResourcesAssembler) {
        this.productRepository = productRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    //use PersistentEntityResourceAssembler and PagedResourcesAssembler to return a resource with paging links
    @RequestMapping(method = GET, path="/products/search/listProductsPage", produces = HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<PersistentEntityResource>> getAllPage(Pageable pageable, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Iterable<?> all = productRepository.findAll(pageable);

        return ResponseEntity.ok(pagedResourcesAssembler.toResource((Page<Object>) all, persistentEntityResourceAssembler));
    }

    //return a plain list of resources
    @RequestMapping(method = GET, path="/products/search/listProducts", produces = HAL_JSON_VALUE)
    public ResponseEntity<Resources<PersistentEntityResource>> getAll(Pageable pageable, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {

        return ResponseEntity.ok(new Resources<PersistentEntityResource>(productRepository.findAll().stream()
                .map(persistentEntityResourceAssembler::toResource)
                .collect(Collectors.toList())));
    }
}
