package org.journalApp.service;
//Not in working condition
import org.journalApp.entity.User;
import org.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceMockTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    @Test
    void loadUserByUserName(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder()
                .username("Ram").password("Ram").roles(new String[0]).build());
        UserDetails user =userDetailsService.loadUserByUsername("Ram");
    }


}
