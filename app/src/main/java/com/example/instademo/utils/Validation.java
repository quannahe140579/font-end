package com.example.instademo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String PWD_FORMAT = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*[@#$%]).{8,20}$";
    private static final String USERNAME_FORMAT = "^[a-zA-Z0-9_.]{8,20}$";

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PWD_FORMAT);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isUserNameValid(String username) {
        Pattern pattern = Pattern.compile(USERNAME_FORMAT);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isPhoneNumberValid(String phone) {
        if (phone.length() == 10 && (phone.startsWith("03") || phone.startsWith("05") ||
                phone.startsWith("09"))) {
            return true;
        }
        return false;
    }

    public static String validateRegisterInputs(String username, String password, String rePassword) {
        if (!username.isEmpty() && !password.isEmpty() && !rePassword.isEmpty()) {
            if (username.length() >= 8 && username.length() <= 20) {
                if (isUserNameValid(username)) {
                } else {
                    return ErrorMessage.CONTAIN_ONLY;
                }
            } else {
                return ErrorMessage.LENGTH_USERNAME;
            }

            if (password.length() >= 8 && password.length() <= 20) {
                if (!isPasswordValid(password)) {
                    return ErrorMessage.CONTAIN_SPECIAL_PASSWORD;
                }
            } else {
                return ErrorMessage.LENGTH_PASSWORD;
            }

            if (!password.equals(rePassword)) {
                return ErrorMessage.EQUAL_PASSWORD;
            }
        } else {
            return ErrorMessage.EMPTY_DATA;
        }

        return "";
    }

    public static String validateLoginInputs(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            if (username.length() < 8 || username.length() > 20) {
                return ErrorMessage.LENGTH_USERNAME;
            }
            if (password.length() < 8 || password.length() > 20) {
                return ErrorMessage.LENGTH_PASSWORD;
            }

        } else {
            return ErrorMessage.EMPTY_DATA;
        }

        return "";
    }

    public static String validateEditProfileInputs (String name, String phone) {
        //check name is empty
        if (name.isEmpty()) {
            return ErrorMessage.EMPTY_NAME;
        }

        if (!phone.isEmpty()) {
            if (!isPhoneNumberValid(phone)) {
                return ErrorMessage.PHONE;
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String errorMessage = null;
        errorMessage = validateEditProfileInputs("thuong", "0975367654");
        System.out.println(errorMessage);
    }

}
