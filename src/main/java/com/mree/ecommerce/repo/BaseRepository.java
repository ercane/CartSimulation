package com.mree.ecommerce.repo;

import com.mree.ecommerce.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
