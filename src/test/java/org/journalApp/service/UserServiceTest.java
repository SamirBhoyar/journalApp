package org.journalApp.service;

import org.journalApp.entity.User;
import org.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Disabled  //it will skip this test when you run tests for this class
    @Test
    public void testFindByUserName(){
        User user= userRepository.findByUserName("Ram");
        assertTrue(!user.getJournalEntries().isEmpty()) ;
    }

    @ParameterizedTest  //Dummy Parameterize test
    @CsvSource({
        "1,2,3",
        "2,10,12",
        "3,3,9"
    })
    public void test(int a,int b,int expected){ // variables a=1,b=2 which is not equals to expected 3 ->pass
        assertEquals(expected,a+b,"failed for: "+ expected);// variables a=3,b=3 which is not equals to expected 9 ->fail
    }
}
