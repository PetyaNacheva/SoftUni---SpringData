package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.Dto.Xml.TeamRootSeedDto;
import softuni.exam.domain.Dto.Xml.TeamSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private static final String TEAM_INPUT_FILE="src/main/resources/files/xml/teams.xml";
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final PictureService pictureService;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser, PictureService pictureService) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.pictureService = pictureService;
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        List<TeamSeedDto> teams = xmlParser.parseXml(TEAM_INPUT_FILE, TeamRootSeedDto.class).getTeams();
        xmlParser.parseXml(TEAM_INPUT_FILE, TeamRootSeedDto.class).getTeams().stream()
                .filter(teamSeedDto -> {
                    Picture picture = this.pictureService.findPictureByUrl(teamSeedDto.getPicture().getUrl());
                    boolean isValid = validatorUtil.isValid(teamSeedDto)&&picture!=null;
                    sb.append( isValid ? String.format("Successfully imported - %s", teamSeedDto.getName())
                            : "Invalid team").append(System.lineSeparator());
                    return isValid;
                }).map(teamSeedDto -> {
                    Team team = modelMapper.map(teamSeedDto, Team.class);
                    Picture picture = this.pictureService.findPictureByUrl(teamSeedDto.getPicture().getUrl());
                    if(picture!=null){
                        team.setPicture(picture);
                    }
                    return team;
                })
                .forEach(this.teamRepository::save);
        return sb.toString();
    }

    @Override
    public boolean areImported() {

        return this.teamRepository.count()>0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {

        return Files.readString(Path.of(TEAM_INPUT_FILE));
    }

    @Override
    public Team findTeamByName(String name) {
        return this.teamRepository.findTeamByName(name);
    }
}
