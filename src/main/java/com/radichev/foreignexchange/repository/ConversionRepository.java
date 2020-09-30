package com.radichev.foreignexchange.repository;

import com.radichev.foreignexchange.domain.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<Conversion, String> {
}
