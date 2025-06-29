package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {

        this.repository = repository;
    }

    public Meal create (Meal meal){

        return repository.save(meal);
    }

    public void delete (int id){

        checkNotFound(repository.delete(id), id);
    }

    public Meal get (int id){
        return checkNotFound(repository.get(id), id);
    }

     public Collection<Meal> getAll (){
       return repository.getAll();
     }

    public Meal update (Meal meal, int id){
        return
                checkNotFound(repository.save(meal), id);
    }

}