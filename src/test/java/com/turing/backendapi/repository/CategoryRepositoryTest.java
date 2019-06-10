package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.CategoryEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAll(){
        Pageable pageable = PageRequest.of(5, 3, Sort.by("name").descending());

        Page<CategoryEntity> all = categoryRepository.findAll(pageable);

        all.get().forEach(System.out::println);

    }
}