package com.ccb.referencedata.repo;

import com.ccb.referencedata.model.CreditException;
import com.ccb.referencedata.model.Province;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Holds the service's static reference data. Both files are read once at
 * construction and exposed as immutable lists; nothing here ever writes.
 */
@Repository
public class ReferenceDataRepository {

    private final List<Province> provinces;
    private final List<CreditException> exceptions;

    public ReferenceDataRepository(ObjectMapper mapper) {
        this.provinces = load(mapper, "reference/provinces.json", new TypeReference<>() {});
        this.exceptions = load(mapper, "reference/exceptions.json", new TypeReference<>() {});
    }

    public List<Province> provinces() {
        return provinces;
    }

    public List<CreditException> exceptions() {
        return exceptions;
    }

    private <T> List<T> load(ObjectMapper mapper, String location, TypeReference<List<T>> type) {
        try (InputStream in = new ClassPathResource(location).getInputStream()) {
            return List.copyOf(mapper.readValue(in, type));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load reference data from " + location, e);
        }
    }
}
