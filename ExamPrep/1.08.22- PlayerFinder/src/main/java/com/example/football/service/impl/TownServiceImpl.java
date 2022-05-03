package com.example.football.service.impl;

import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class TownServiceImpl implements TownService {

    private static final String TOWN_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count()>0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        String path = Files.readAllLines(Path.of(TOWN_FILE_PATH))+"\n";
        return  null;
    }

    @Override
    public String importTowns() {
        return null;
    }
}
