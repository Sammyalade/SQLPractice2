package org.example.chef__fizzy.repositories;

import org.example.chef__fizzy.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository();

    @Test
    void saveUser() {
        User user = new User();
//        user.setId(1L);
//        user.setWalletId(1L);
        User savedUser = userRepository.saveUser(user);
        assertNotNull(savedUser);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        Long walletId = 200L;

        User user = userRepository.updateUser(userId, walletId);

        assertEquals(200L, user.getWalletId());
    }

}