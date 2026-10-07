package com.example.taskboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 실습용 초기 데이터. create-drop이라 서버를 켤 때마다 다시 넣는다
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, TaskRepository taskRepository,
                           TagRepository tagRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 세 사용자 모두 비밀번호는 pass1234. DB에는 해시만 들어간다
        String password = passwordEncoder.encode("pass1234");
        User ara = userRepository.save(new User("ara", password));
        User bogum = userRepository.save(new User("bogum", password));
        User chulsoo = userRepository.save(new User("chulsoo", password));

        Tag study = tagRepository.save(new Tag("공부"));
        Tag urgent = tagRepository.save(new Tag("긴급"));
        Tag workout = tagRepository.save(new Tag("운동"));

        save(new Task("JPA 익히기", "7장", ara), study);
        save(new Task("N+1 잡기", "8장", ara), study, urgent);
        save(new Task("러닝", null, bogum), workout);
        save(new Task("스트레칭", null, bogum), workout);
        save(new Task("코드 리뷰", null, chulsoo), urgent);
    }

    private void save(Task task, Tag... tags) {
        for (Tag tag : tags) task.addTag(tag);
        taskRepository.save(task);
    }
}
