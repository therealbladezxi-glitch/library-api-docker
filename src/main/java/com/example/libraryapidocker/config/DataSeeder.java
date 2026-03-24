package com.example.libraryapidocker.config;

import com.example.libraryapidocker.model.Book;
import com.example.libraryapidocker.model.User;
import com.example.libraryapidocker.model.UserStatus;
import com.example.libraryapidocker.model.UserType;
import com.example.libraryapidocker.repository.BookRepository;
import com.example.libraryapidocker.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public DataSeeder(UserRepository userRepository, BookRepository bookRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userSeed();
        bookSeed();
    }

    private void userSeed(){
        if (userRepository.count()==0) {
            User superUser = new User();
            superUser.setName("Bladez");
            superUser.setSurname("Super");
            superUser.setMail("bladez@XI.de");
            superUser.setPass("pass");
            superUser.setMemberShipDate(LocalDate.now());
            superUser.setUserType(UserType.SUPER);
            superUser.setUserStatus(UserStatus.ACTIVE);
            userRepository.save(superUser);
        }
    }

    private void bookSeed(){
        if (bookRepository.count() == 0){
            Book zeroBook = new Book();
            zeroBook.setTitle("OnePiece");
            zeroBook.setAuthorName("Bladez");
            zeroBook.setPublisher("BladezCO");
            zeroBook.setReleaseYear(2026);
            zeroBook.setTotalCopies(1);
            bookRepository.save(zeroBook);
        }
    }
}
