package com.lambda.foodtruck.repositories;

import com.lambda.foodtruck.models.Menu;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu,Long>
{
}
