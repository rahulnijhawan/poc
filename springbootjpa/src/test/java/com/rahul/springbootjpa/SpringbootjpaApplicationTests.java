package com.rahul.springbootjpa;

import com.rahul.springbootjpa.jpa.entity.StoryEntity;
import com.rahul.springbootjpa.jpa.entity.UserEntity;
import com.rahul.springbootjpa.jpa.repository.StoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class SpringbootjpaApplicationTests {

    @Autowired
    private StoryRepository storyRepository;

    @Test
//    @Transactional
    void findAll() {
        List<StoryEntity> all = storyRepository.findAll();

        for (var u :
                all) {
            System.out.println("u.getStories().size() = " + u.getUsers().size());
        }

    }

    @Test
    void findWithStoriesBy() {
        List<StoryEntity> withStoriesBy = storyRepository.findWithStoriesBy();

        for (var u :
                withStoriesBy) {
            System.out.println("u.getStories().size() = " + u.getUsers().size());
        }
    }

}
