package com.turing.backendapi.service;

import static com.turing.backendapi.controller.exception.ErrorCodes.PAG_02;
import static com.turing.backendapi.controller.exception.ErrorCodes.PAG_03;
import static com.turing.backendapi.service.converter.CategoryConverter.toDomain;
import static java.util.stream.Collectors.toList;

import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.Category;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.repository.CategoryRepository;
import com.turing.backendapi.repository.entity.CategoryEntity;
import com.turing.backendapi.service.converter.CategoryConverter;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
@Transactional(readOnly = true)
@Slf4j
public class CategoryService {

    public static final Set<String> SORTING_FIELDS = Set.of("category_id", "name");

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * @param page Starting with 1. Default: 1. Be aware that Hibernate use a 0 based page index.
     * @param limit Items per page
     * @param orderBy Allowed fields: 'category_id', 'name'.
     */
    public DomainPage<Category> getPage(int page, int limit, String orderBy) {
        log.debug("getPage(page:{}, limit: {}, orderBy: {}", page, limit, orderBy);
        if (page < 1) {
            throw new BadRequestException(PAG_03.getCode(), PAG_03.getDescription(), "page");
        }

        if (limit < 1) {
            throw new BadRequestException(PAG_03.getCode(), PAG_03.getDescription(), "limit");
        }

        if (!StringUtils.isEmpty(orderBy) && !SORTING_FIELDS.contains(orderBy)) {
            throw new BadRequestException(PAG_02.getCode(), PAG_02.getDescription(), "order");
        }

        Pageable pageable = null;
        if (!StringUtils.isEmpty(orderBy) && SORTING_FIELDS.contains(orderBy)) {
            pageable = PageRequest.of(page - 1, limit, Sort.by(orderBy));
        } else {
            pageable = PageRequest.of(page - 1, limit);
        }

        Page<CategoryEntity> pageEntity = categoryRepository.findAll(pageable);

        return new DomainPage<>(pageEntity.getTotalElements(), pageEntity.get().map(CategoryConverter::toDomain).collect(toList()));
    }

    public Category getById(Integer id) {
        return toDomain(categoryRepository.findById(id).orElse(null));
    }

    public List<Category> getByProductId(int productId) {
        return categoryRepository.findInProduct(productId).stream()
            .map(o -> (Object[]) o).map(o -> Category.builder().category_id((Integer) o[0]).department_id((Integer) o[1]).name((String) o[2]).build())
            .collect(toList());
    }

}
