package com.computersoftwaresolutionsllc.moviecatalogservice;

import com.computersoftwaresolutionsllc.moviecatalogservice.models.CatalogItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name="movie-catalog-service")
public interface MovieCatalogResourceClient {
    @RequestMapping("/catalog/{userId}")
    List<CatalogItem> getCatalog(@PathVariable("userId") String userId);
}
