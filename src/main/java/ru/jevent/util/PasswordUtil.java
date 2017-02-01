package ru.jevent.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;



public class PasswordUtil {
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
    static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }

    public static String encodePassword(String newPassword) {
        if (StringUtils.isEmpty(newPassword)) {
            return null;
        }
        if (isPasswordEncoded(newPassword)) {
            return newPassword;
        }
        return PASSWORD_ENCODER.encode(newPassword);
    }

    public static boolean isPasswordMatch(String rawPassword, String password) {
        return PASSWORD_ENCODER.matches(rawPassword, password);
    }

    public static boolean isPasswordEncoded(String newPassword) {
        return BCRYPT_PATTERN.matcher(newPassword).matches();
    }

    public static <T extends HasPassword> T getEncoded(T u) {
        u.setPassword(PasswordUtil.encodePassword(u.getPassword()));
        return u;
    }
}
