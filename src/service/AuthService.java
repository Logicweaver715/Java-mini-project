package service;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import model.User;

public class AuthService {

    private HashMap<String, User> users =
            new HashMap<>();
    public AuthService() {
        loadUsers();   // VERY IMPORTANT
    }
    public boolean signup(String u,
                          String p,
                          String e) {

        if (users.containsKey(u)) {
            return false;
        }
        User user = new User(u, p, e);

        users.put(u, user);
        saveUser(user);
        return true;
    }

    public boolean login(String u, String p) {

    u = u.trim();
    p = p.trim();

    System.out.println("Trying login: " + u);
    System.out.println("Loaded users: " + users.keySet());

    if (users.containsKey(u)) {

        System.out.println("User found");

        String storedPassword =
                users.get(u).password.trim();

        System.out.println("Stored password: " + storedPassword);

        return storedPassword.equals(p);
    }

    System.out.println("User not found");

    return false;
}
    private void saveUser(User user) {

        try {

            FileWriter fw =
                new FileWriter("data/users.txt", true);

            fw.write(
                user.username + "," +
                user.password + "," +
                user.email + "\n"
            );

            fw.close();

        } catch (Exception e) {
            System.out.println("File Save Error");
        }
    }
    private void loadUsers() {

        try {

            File file =
                new File("data/users.txt");

            if (!file.exists()) {
                return;
            }

            Scanner sc =
                new Scanner(file);

            while (sc.hasNextLine()) {

                String line =
                    sc.nextLine();

                String[] data =
                    line.split(",");

                if (data.length == 3) {

                    String username = data[0];
                    String password = data[1];
                    String email    = data[2];

                    users.put(
                        username,
                        new User(
                            username,
                            password,
                            email
                        )
                    );
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("File Load Error");
        }
    }
}

