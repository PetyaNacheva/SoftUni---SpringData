package com.example.football.service.impl;

import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TeamServiceImpl implements TeamService {
    private static final String TEAM_SERVICE_PATH="src/main/java/com/example/football/service/impl/TeamServiceImpl.java";
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count()>0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return  Files.readString(Path.of(TEAM_SERVICE_PATH));
    }

    @Override
    public String importTeams() {
        return null;
    }
}
