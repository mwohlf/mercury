package net.wohlfart.mercury.util;

import net.wohlfart.mercury.model.Role;
import net.wohlfart.mercury.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DummyDataGenerator {

    public static List<User> getUsers(int amount, int idStarsFrom) {
        List<User> users = new ArrayList<>();
        for (int i = idStarsFrom; i < amount + idStarsFrom; i++) {
            users.add(User.builder()
                    .name(String.format("random-name%d", i))
                    .email(String.format("random%d@email.com", i))
                    .password(String.format("password%d", i))
                    .roles(Collections.singleton(Role.builder().id(0L).name("USER").build()))
                    .build()
            );
            users.get(i - idStarsFrom).setId((long) i);
        }

        return users;
    }

    public static List<User> getUsers(int amount) {
        return getUsers(amount, 1);
    }

}