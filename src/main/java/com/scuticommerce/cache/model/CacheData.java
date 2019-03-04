package com.scuticommerce.cache.model;

import lombok.Data;

@Data
public class CacheData<T> {

    String key;
    T value;
}
