package com.example;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
public class DemoController {

    private final ProductRepository productRepository;
    private final PagedResourcesAssembler<Object> asemb;

    @Autowired
    public DemoController(ProductRepository productRepository,
                          PagedResourcesAssembler<Object> asemb) {
        this.productRepository = productRepository;
        this.asemb = asemb;
    }

    @RequestMapping(method = GET, path="/products/search/listProducts", produces = HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<PersistentEntityResource>> getAll(Pageable pageable, PersistentEntityResourceAssembler persistentEntityResourceAssembler) {
        Iterable<?> all = (Iterable<?>) productRepository.findAll(pageable);

        return ResponseEntity.ok(asemb.toResource((Page<Object>) all, persistentEntityResourceAssembler));
    }
}
