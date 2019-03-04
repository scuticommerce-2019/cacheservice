package com.scuticommerce.cache.controller;

import com.scuticommerce.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/cache/")
public class CacheController {

    @Autowired
    CacheService service;

    @GetMapping(value="/key")
    public ResponseEntity<?> cache(@RequestParam String key){

        return new ResponseEntity<>(service.getCache(key), HttpStatus.OK);
    }

    @PostMapping(value="/store")
    public ResponseEntity<?> store(@RequestParam String key, @RequestParam String value){

        return new ResponseEntity<>(service.storeCache(key,value), HttpStatus.OK);
    }
}
