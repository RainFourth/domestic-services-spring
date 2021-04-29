package com.rrain.springdomesticservicesapp.controllers;

import com.rrain.springdomesticservicesapp.model.Service;
import com.rrain.springdomesticservicesapp.repo.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ServiceController {

    @Autowired private ServiceRepository serviceRepo;


    private record ServicesRequest(Integer page, Integer size){
        public ServicesRequest(Integer page, Integer size){
            int defaultPage = 1, defaultSize = 5;
            this.page = (page==null ? defaultPage : page)-1; // нумерация страниц в UI идёт с 1
            this.size = size==null ? defaultSize : size;
        }
    }
    private record ServicesResponse(List<Service> content, int selectedPage, int totalPages, int pageSize){}
    @GetMapping("/services")
    public ResponseEntity<?> getServices(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        //System.out.println("iiii"+cnt++);
        //System.out.println("page="+page+" size="+size);
        ServicesRequest servicesRequest = new ServicesRequest(page, size);
        Pageable pageable = PageRequest.of(servicesRequest.page(), servicesRequest.size());
        Page<Service> servicesPage = serviceRepo.findAll(pageable);
        ServicesResponse servicesResponse = new ServicesResponse(
                servicesPage.getContent(), servicesPage.getNumber()+1, servicesPage.getTotalPages(), servicesPage.getSize()
        );
        return ResponseEntity.ok(servicesResponse);
    }
    //private int cnt = 0;

}
