package org.zafritech.zscode.todos.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.zscode.todos.data.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
