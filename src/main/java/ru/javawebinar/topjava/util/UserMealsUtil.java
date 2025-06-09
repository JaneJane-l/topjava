package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        //List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }


    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (c1, c2)-> c1+c2));

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
       meals.forEach(meal -> {
           if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)){
                   //userMealWithExcesses.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDate.get(meal.getDateTime())> caloriesPerDay));
           userMealWithExcesses.add(mealTo(meal, caloriesSumByDate.get(meal.getDateTime())>caloriesPerDay));
           }
       });

        return null;

      //  Map<LocalDate, List<UserMeal>> map = new HashMap<>();
     //   for (UserMeal userMeal : meals) {
     //       if (map.containsKey(userMeal.getDateTime().toLocalDate())) {
     //           map.get(userMeal.getDateTime().toLocalDate()).add(userMeal);
     //       } else {
     //           List<UserMeal> list = new ArrayList<>();
     //           list.add(userMeal);
     //           map.put(userMeal.getDateTime().toLocalDate(), list);
     //       }
      //  }

      //  List<UserMealWithExcess> listExcess = new ArrayList<>();
      //  Set<LocalDate> dateExcess = new HashSet<>();
     //   for (Map.Entry<LocalDate, List<UserMeal>> entry : map.entrySet()) {
       //     LocalDate key = entry.getKey();
       //     List<UserMeal> value = entry.getValue();
       //     int userCalories = 0;
       //     for (UserMeal user : value) {
       //         userCalories = userCalories + user.getCalories();
      //      }
      //      if (userCalories > caloriesPerDay) {
       //         dateExcess.add(key);
      //      }
      //  }
      //  for (Map.Entry<LocalDate, List<UserMeal>> entry : map.entrySet()) {
      //      LocalDate key = entry.getKey();
      //      List<UserMeal> value = entry.getValue();
      //      for (UserMeal userMealExcess : value) {
       //         if (TimeUtil.isBetweenHalfOpen(userMealExcess.getDateTime().toLocalTime(), startTime, endTime)) {
       //             if (dateExcess.contains(key)) {
        //                listExcess.add(new UserMealWithExcess(userMealExcess.getDateTime(), userMealExcess.getDescription(), userMealExcess.getCalories(), true));
         //           } else {
       //                 listExcess.add(new UserMealWithExcess(userMealExcess.getDateTime(), userMealExcess.getDescription(), userMealExcess.getCalories(), false));
       //             }
       //         }
       //     }
      //  }
     //   // TODO return filtered list with excess. Implement by cycles
     //   return listExcess;

    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesByDate = meals.stream().collect
                (Collectors.groupingBy(ml -> ml.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> userMealWithExcesses = meals.stream()
                .filter(ml -> isBetweenHalfOpen(ml.getDateTime().toLocalTime(), startTime, endTime))
                .map(ml -> mealTo(ml, sumCaloriesByDate.get(ml.getDateTime().toLocalDate())>caloriesPerDay) )
                .collect(Collectors.toList());
        return userMealWithExcesses;

    }
    private static UserMealWithExcess mealTo ( UserMeal meal, boolean excess){
        return  new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}

