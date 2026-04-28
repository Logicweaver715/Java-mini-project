// src/service/ProfileService.java
package service;

import java.io.FileWriter;
import model.UserProfile;

public class ProfileService {

    public void saveProfile(UserProfile p) {

        try {

            FileWriter fw =
                new FileWriter("data/profiles.txt", true);

            fw.write(
                p.name + "," +
                p.age + "," +
                p.gender + "," +
                p.weight + "," +
                p.height + "\n"
            );

            fw.close();

        } catch (Exception e) {
            System.out.println("Profile Save Error");
        }
    }
}