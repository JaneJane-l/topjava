package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }


    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setIdUser(SecurityUtil.authUserId());
            meal.setId(counter.incrementAndGet());
            mealsMap.put(meal.getId(), meal);
            return meal;
        }

        if (checkUserMeal(meal.getId())) {
            return mealsMap.compute(meal.getId(), (id, oldMeal) -> meal);
        }


        // handle case: update, but not present in storage
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (checkUserMeal(id)) {
            return mealsMap.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id) {
        if (checkUserMeal(id)) {

            return mealsMap.get(id);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        Collection<Meal> mealsGetAll = mealsMap.values();
       return mealsGetAll.stream().sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());

    }

    private boolean checkUserMeal(int mealId) {
        return mealsMap.containsKey(mealId) && mealsMap.get(mealId).getIdUser().equals(SecurityUtil.authUserId());
    }
}

