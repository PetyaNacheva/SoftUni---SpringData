package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Dto.Json.Xml.PostRootSeedDto;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.Post;
import softuni.exam.instagraphlite.models.User;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {
    private static final String POST_SEED_PATH = "src/main/resources/files/posts.xml";
    private final PostRepository postRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final PictureService pictureService;

    public PostServiceImpl(PostRepository postRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, PictureService pictureService) {
        this.postRepository = postRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;

        this.pictureService = pictureService;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POST_SEED_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        this.xmlParser.parseXml(POST_SEED_PATH, PostRootSeedDto.class).getPosts()
                .stream()
                .filter(postSeedDto -> {
                    User user = userService.findUserByUsername(postSeedDto.getUser().getUsername());
                    Picture picture = pictureService.findPictureByPath(postSeedDto.getPicture().getPath());
                    boolean isValid = validationUtil.isValid(postSeedDto)&&user!=null&&picture!=null;
                    sb.append(isValid ? String.format("Successfully imported Post, made by %s", postSeedDto.getUser().getUsername()) : "Invalid Post").append(System.lineSeparator());

                return isValid;})
                .map(postSeedDto -> {
                    Post post =modelMapper.map(postSeedDto, Post.class);
                    User user = userService.findUserByUsername(postSeedDto.getUser().getUsername());
                    Picture picture = pictureService.findPictureByPath(postSeedDto.getPicture().getPath());

                    if(user!=null&&picture!=null){
                        post.setUser(user);
                        post.setPicture(picture);
                    }
                    return post;
                }).forEach(postRepository::save);
        return sb.toString();
    }
}
