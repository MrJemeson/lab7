package ru.bmstu.repository.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.bmstu.object.User;
import ru.bmstu.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class CsvUserRepositoryImpl implements UserRepository {
    private final Resource csvResource;
    private final String csvHeader = "\"ID\",\"FullName\",\"Role\",\"Tokens\"";

    public CsvUserRepositoryImpl(@Value("${app.data.file}") Resource resource) {
        this.csvResource = resource;
    }

    @Override
    public List<User> loadUsers(){
        List<User> users = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(csvResource.getInputStream()))) {
            String[] parts;
            boolean firstLine = true;

            while ((parts = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                try {
                    users.add(parseCsvLine(parts));
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + String.join(",", parts));
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            return null;
        }

        return users;
    }

    @Override
    public User parseCsvLine(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid CSV format");
        }

        int id = Integer.parseInt(parts[0].trim());
        String fullName = parts[1].trim();
        String role = parts[2].trim();
        Integer tokens = (!role.equals("Teacher") && !parts[3].trim().isEmpty())
                ? Integer.parseInt(parts[3].trim())
                : null;

        return new User(id, fullName, role, tokens);
    }

    @Override
    public void saveUsers(List<User> users) {
        try {
            File file = new File(csvResource.getURI());
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
                writer.println(csvHeader);
                for (User user : users) {
                    writer.println(toCsvLine(user));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV", e);
        }
    }


    @Override
    public String toCsvLine(User user) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(Integer.toString(user.getID()))
                .add(user.getFULL_NAME())
                .add(user.getROLE())
                .add(((user.getTokens() == null)?(""):(Integer.toString(user.getTokens()))));

        return joiner.toString();
    }
}
