package com.ccb.referencedata.repo;

import com.ccb.referencedata.model.CreditException;
import com.ccb.referencedata.model.Province;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReferenceDataRepositoryTest {

    @Autowired
    private ReferenceDataRepository repository;

    @Test
    void loadsAllThirteenProvincesAtStartup() {
        List<Province> provinces = repository.provinces();

        assertThat(provinces).hasSize(13);
        assertThat(provinces).extracting(Province::code)
                .contains("ON", "BC", "QC", "NU");
        assertThat(provinces).allSatisfy(province ->
                assertThat(province.countryCode()).isEqualTo("CA"));
    }

    @Test
    void loadsProvinceNamesNotJustCodes() {
        assertThat(repository.provinces())
                .filteredOn(province -> province.code().equals("ON"))
                .singleElement()
                .extracting(Province::name)
                .isEqualTo("Ontario");
    }

    @Test
    void loadsAllSixCreditExceptions() {
        List<CreditException> exceptions = repository.exceptions();

        assertThat(exceptions).hasSize(6);
        assertThat(exceptions).extracting(CreditException::code)
                .containsExactlyInAnyOrder("EXC-LTV", "EXC-DSCR", "EXC-DOC",
                        "EXC-TENURE", "EXC-COLLATERAL", "EXC-KYC");
    }

    @Test
    void creditExceptionsCarryCategoryAndSeverity() {
        assertThat(repository.exceptions())
                .filteredOn(exception -> exception.code().equals("EXC-DSCR"))
                .singleElement()
                .satisfies(exception -> {
                    assertThat(exception.category()).isEqualTo("CREDIT");
                    assertThat(exception.severity()).isEqualTo("HIGH");
                });
    }

    @Test
    void returnedListsAreImmutable() {
        assertThatThrownBy(() -> repository.provinces().add(new Province("ZZ", "Nowhere", "CA")))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
