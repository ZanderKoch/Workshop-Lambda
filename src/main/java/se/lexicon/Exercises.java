package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	TODO: Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> result = storage.findMany(p -> p.getFirstName().equals("Erik"));
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        2.	TODO: Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> result = storage.findMany(p -> p.getGender().equals(Gender.FEMALE));
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        3.	TODO: Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        //Write your code here
        LocalDate date = LocalDate.of(2000, 1, 1);

        List<Person> result = storage.findMany(
                p -> p.getBirthDate().equals(date) || p.getBirthDate().isAfter(date)
        );
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        4.	TODO: Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        //Write your code here
        System.out.println(storage.findOne(p -> p.getId() == 123));

        System.out.println("----------------------");
    }

    /*
        5.	TODO: Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        //Write your code here
        String result = storage.findOneAndMapToString(p -> p.getId() == 456
                , p -> String.format("Name: %s %s born %s\n"
                        , p.getFirstName()
                        , p.getLastName()
                        , DateTimeFormatter.ofPattern("yyyy-MM-dd").format(p.getBirthDate()))
        );
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        6.	TODO: Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        //Write your code here
        List<String> result = storage.findManyAndMapEachToString(
                p -> p.getGender() == Gender.MALE
                        && (p.getFirstName().startsWith("E") || p.getLastName().startsWith("E"))
                , p -> p.getFirstName() + " " + p.getLastName()
        );
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        7.	TODO: Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        //Write your code here
        LocalDate today = LocalDate.now();

        Function<Person, Integer> getAge = p -> Period.between(p.getBirthDate(), today).getYears();

        List<String> result = storage.findManyAndMapEachToString(
                p -> getAge.apply(p) < 10
                , p -> String.format("%s %s %d year%s"
                        , p.getFirstName()
                        , p.getLastName()
                        , getAge.apply(p)
                        , getAge.apply(p) > 1 ? "s" : ""
                )
        );
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        8.	TODO: Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(p -> p.getFirstName().equals("Ulf")
                , p -> System.out.println(p));

        System.out.println("----------------------");
    }

    /*
        9.	TODO: Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        // Write your code here
        storage.findAndDo(
                p -> p.getLastName().toLowerCase().contains(p.getFirstName().toLowerCase()),
                p -> System.out.println(p.getFirstName() + " " + p.getLastName())
        );

        System.out.println("----------------------");
    }

    /*
        10.	TODO: Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        // Write your code here
        Predicate<String> isPalindrome = string -> {
            String lowerCaseString = string.toLowerCase(); // Convert both strings to lowercase
            return lowerCaseString.contentEquals(new StringBuilder(lowerCaseString).reverse().toString());
        };

        storage.findAndDo(
                p -> isPalindrome.test(p.getFirstName()),
                p -> System.out.println(p.getFirstName() + " " + p.getLastName())
        );

        System.out.println("----------------------");
    }

    /*
        11.	TODO: Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> result = storage.findAndSort(p -> p.getFirstName().startsWith("A")
                , (p1, p2) -> p1.getBirthDate().compareTo(p2.getBirthDate())
        );
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        12.	TODO: Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        // Write your code here
        List<Person> result = storage.findAndSort(
                p -> p.getBirthDate().isBefore(LocalDate.of(1950, 1, 1))
                , (p1, p2) -> p2.getBirthDate().compareTo(p1.getBirthDate())
        );
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        13.	TODO: Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        //Write your code here
        List<Person> result = storage.findAndSort(p -> true
                , Comparator.comparing((Person p) -> p.getLastName())
                        .thenComparing((Person p) -> p.getFirstName())
                        .thenComparing((Person p) -> p.getBirthDate())
        );
        System.out.println(result);

        System.out.println("----------------------");
    }

}