package com.example.springliquidbase.infrastructure.dictionaryRepository;

import com.example.springliquidbase.domain.Dictionary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DictionaryRepository {

    private final Map<Long, Dictionary> dictionary = new HashMap<>();

}
