package hiberspring.service.Impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.Json.BranchesSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class BranchServiceImpl implements BranchService {
    private static final String BRANCH_FILE_PATH = "src/main/resources/files/branches.json";

    private  final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final TownService townService;

    public BranchServiceImpl(BranchRepository branchRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, TownService townService) {
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.townService = townService;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count()>0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(BRANCH_FILE_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readBranchesJsonFile(), BranchesSeedDto[].class))
                .filter(branchesSeedDto -> {
                    Town town = townService.findTownByName(branchesSeedDto.getTown());
                    boolean isValid = validationUtil.isValid(branchesSeedDto)&&town!=null;

                    sb.append(isValid ? String.format("Successfully imported Branch %s.", branchesSeedDto.getName())
                            : "Error: Invalid data.")
                            .append(System.lineSeparator());
                    return isValid;
                }).map(branchesSeedDto ->{
                  Branch branch = modelMapper.map(branchesSeedDto, Branch.class);
                  Town town = this.townService.findTownByName(branchesSeedDto.getTown());
                  branch.setTown(town);
                  return branch;
                })
                .forEach(this.branchRepository::saveAndFlush);


        return sb.toString();
    }

    @Override
    public Branch findBranchByName(String name) {
        return this.branchRepository.findBranchByName(name);
    }
}
