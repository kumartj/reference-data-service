package com.ccb.referencedata.api;

import com.ccb.referencedata.model.CreditException;
import com.ccb.referencedata.model.Province;
import com.ccb.referencedata.repo.ReferenceDataRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reference-data")
public class ReferenceDataController {

    private final ReferenceDataRepository repository;

    public ReferenceDataController(ReferenceDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/provinces")
    public List<Province> provinces() {
        return repository.provinces();
    }

    @GetMapping("/exceptions")
    public List<CreditException> exceptions() {
        return repository.exceptions();
    }
}
