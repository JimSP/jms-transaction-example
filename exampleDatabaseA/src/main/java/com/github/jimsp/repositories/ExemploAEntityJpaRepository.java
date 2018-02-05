package com.github.jimsp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.jimsp.entities.ExemploAEntity;

@Repository
public interface ExemploAEntityJpaRepository extends JpaRepository<ExemploAEntity, Long> {

}
