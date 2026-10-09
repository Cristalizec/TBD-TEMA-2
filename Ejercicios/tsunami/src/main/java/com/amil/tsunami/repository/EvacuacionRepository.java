
package com.amil.tsunami.repository;

import com.amil.tsunami.entity.Evacuacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvacuacionRepository
        extends JpaRepository<Evacuacion, Long> {
}
