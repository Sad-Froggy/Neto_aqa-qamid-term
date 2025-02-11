package ru.netology.utils;

import com.github.javafaker.Faker;
import lombok.*;

import java.time.LocalDate;
import java.util.Random;

@Getter
public class DataHelper {

    static Random random = new Random();

    public static String getName() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    public static String getCurrentYear() {
        int year = LocalDate.now().getYear() - 2000;
        return Integer.toString(year);
    }

    public static String getNextYear() {
        int year = LocalDate.now().getYear() - 1999;
        return Integer.toString(year);
    }

    public static String getPreviousYear() {
        int year = LocalDate.now().getYear() - 2001;
        return Integer.toString(year);
    }

    public static String getMonth() {
        int month = LocalDate.now().getMonthValue();
        return String.format("%02d", month);
    }

    public static String getPreviousMonth() {
        int month = LocalDate.now().getMonthValue() - 1;
        return String.format("%02d", month);
    }

    public static String getCVC() {
        return Integer.toString(random.nextInt(900) + 100);
    }

    public static String getShortCVC() {
        return Integer.toString(random.nextInt(90) + 10);
    }

    public static String randomCardNumber() {
        Faker faker = new Faker();
        return Long.toString(faker.number().randomNumber(16, true));
    }

    public static String getApprovedCardNumber() {
        return "1111222233334444";
    }

    public static String getDeclinedCardNumber() {
        return "5555666677778888";
    }


    @Data
    @Setter
    public static class Card {
        private String number;
        private String month;
        private String year;
        private String owner;
        private String cvc;

        public Card() {
            number = DataHelper.randomCardNumber();
            month = DataHelper.getMonth();
            year = DataHelper.getNextYear();
            owner = DataHelper.getName();
            cvc = DataHelper.getCVC();
        }

        public void checkIfMonthIsCorrect() {
            if (month.equals("0")) {
                setMonth("12");
                setYear(Integer.toString(Integer.parseInt(year) - 1));
            }
        }

    }

}
