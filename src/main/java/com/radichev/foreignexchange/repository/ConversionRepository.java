package com.radichev.foreignexchange.repository;

import com.radichev.foreignexchange.domain.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ConversionRepository extends JpaRepository<Conversion, String> {

    @Query("SELECT c FROM Conversion as c WHERE c.id = :transactionId OR c.conversionDate = :transactionDate")
    Page<Conversion> findAllConversionsByCriteria(@Param("transactionId") String transactionId,
                                                           @Param("transactionDate") LocalDate transactionDate,
                                                           Pageable pageable);
}
