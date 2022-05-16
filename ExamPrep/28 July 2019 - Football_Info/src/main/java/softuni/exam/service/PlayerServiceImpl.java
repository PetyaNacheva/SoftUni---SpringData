package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.Dto.Json.PlayerSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final String PLAYER_FILE_PATH = "src/main/resources/files/json/players.json";
    private final PlayerRepository playerRepository;
    private  final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;
    private final PictureService pictureService;
    private final TeamService teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, PictureService pictureService, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.pictureService = pictureService;
        this.teamService = teamService;
    }

    @Override
    public String importPlayers() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readPlayersJsonFile(), PlayerSeedDto[].class))
                .filter(playerSeedDto -> {
                    Picture picture = this.pictureService.findPictureByUrl(playerSeedDto.getPicture().getUrl());

                    Team team = this.teamService.findTeamByName(playerSeedDto.getTeam().getName());
                    boolean isValid = validatorUtil.isValid(playerSeedDto)&&team!=null&&picture!=null;

                    sb.append( isValid ? String.format("Successfully imported player: %s %s", playerSeedDto.getFirstName(), playerSeedDto.getLastName())
                            : "Invalid player").append(System.lineSeparator());
                    return isValid;
                }).map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    Picture picture = this.pictureService.findPictureByUrl(playerSeedDto.getPicture().getUrl());

                    Team team = this.teamService.findTeamByName(playerSeedDto.getTeam().getName());
                    if(picture!=null&& team!=null){
                        player.setPicture(picture);
                        player.setTeam(team);
                    }
                    return player;
                }).forEach(this.playerRepository::save);

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count()>0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        List<Player> players = this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000));
        for (Player player : players) {
            sb.append(String.format("Player name: %s %s \n" +
                    "\tNumber: %d\n" +
                    "\tSalary: %.2f\n" +
                    "\tTeam: %s\n", player.getFirstName(), player.getLastName(),
                    player.getNumber(), player.getSalary(), player.getTeam().getName()));
        }
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        List<Player>players = this.playerRepository.findAllByTeamName("North Hub");
        sb.append("Team: North Hub").append(System.lineSeparator());
        for (Player player : players) {
            sb.append(String.format(
                    "\tPlayer name: %s %s - %s\n" +
                    "\tNumber: %d\n",player.getFirstName(),
                    player.getLastName(), player.getPosition(),
                    player.getNumber()));
        }

        return sb.toString();
    }
}
