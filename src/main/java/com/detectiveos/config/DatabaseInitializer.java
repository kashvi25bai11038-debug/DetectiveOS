package com.detectiveos.config;

import com.detectiveos.engine.CaseGenerator;
import com.detectiveos.model.*;
import com.detectiveos.repository.CaseRepository;
import com.detectiveos.repository.UserRepository;
import com.detectiveos.util.PasswordUtil;

public final class DatabaseInitializer {
    private DatabaseInitializer() {}
    public static void seed() {
        UserRepository users = new UserRepository();
        if (users.findByUsername("detective") == null)
            users.save(new User("detective", PasswordUtil.sha256("detective123"), "DETECTIVE"));
        CaseRepository cases = new CaseRepository();
        if (cases.findAll().isEmpty()) {
            CaseGenerator generator = new CaseGenerator();
            CaseFile c = cases.save(generator.generate("The Blackwood Mansion Affair", 20260915));
            generator.linkGeneratedSolution(c);
            cases.update(c);
        }
    }
}
