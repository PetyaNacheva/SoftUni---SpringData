package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class AgentServiceImpl implements AgentService {
    private static final String AGENT_FILE_PATH = "C:\\Users\\Pepi\\Desktop\\Java DB Advanced - Exam 2.4.22\\skeleton\\src\\main\\resources\\files\\json\\agents.json";

    private final AgentRepository agentRepository;
    private  final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final TownService townService;

    public AgentServiceImpl(AgentRepository agentRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, TownService townService) {
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count()>0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENT_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readAgentsFromFile(), AgentSeedDto[].class))
                .filter(agentSeedDto -> {
                    Agent agent = this.agentRepository.findAgentByFirstName(agentSeedDto.getFirstName());
                    Town town = this.townService.findTownByName(agentSeedDto.getTown());
                    boolean isValid = validationUtil.isValid(agentSeedDto)&&agent==null&&town!=null;
                    sb.append(isValid ? String.format("Successfully imported agent - %s %s", agentSeedDto.getFirstName(), agentSeedDto.getLastName())
                            : "Invalid agent").append(System.lineSeparator());

                    return isValid;
                }).map(agentSeedDto -> {
                    Agent agent = modelMapper.map(agentSeedDto, Agent.class);
                    Town town = this.townService.findTownByName(agentSeedDto.getTown());
                    agent.setTown(town);
                    return agent;
                }).forEach(this.agentRepository::save);


        return sb.toString();
    }

    @Override
    public Agent findAgentByFirstName(String name) {
        return this.agentRepository.findAgentByFirstName(name);
    }
}
