package com.banking.repository;

import com.banking.model.IdModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T extends IdModel> extends JpaRepository<T, Integer> {
    public T findById(long id);
}
