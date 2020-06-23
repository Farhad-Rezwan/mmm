package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entity.MovieEntity;
import com.example.myapplication.repository.CustomerRepository;

import java.util.List;

public class CustomerViewModel extends ViewModel {
    private CustomerRepository cRepository;
    private MutableLiveData<List<MovieEntity>> allCustomers;
    public CustomerViewModel () { allCustomers=new MutableLiveData<>();
    }
    public void setCustomers(List<MovieEntity> movieEntities) { allCustomers.setValue(movieEntities);
    }
    public LiveData<List<MovieEntity>> getAllCustomers() { return cRepository.getAllCustmers();
    }
    public void initalizeVars(Application application){ cRepository = new CustomerRepository(application);
    }
    public void insert(MovieEntity movieEntity) { cRepository.insert(movieEntity);
    }
    public void insertAll(MovieEntity... movieEntities) { cRepository.insertAll(movieEntities);
    }
    public void deleteAll() { cRepository.deleteAll();
    }
    public void insertAll(MovieEntity movieEntity) { cRepository.delete(movieEntity);
    }
    public void update(MovieEntity... movieEntities) {
        cRepository.updateCustomers(movieEntities); }
    public MovieEntity insertAll(int id) { return cRepository.findByID(id);
    }
    public MovieEntity findByID(int customerId){ return cRepository.findByID(customerId);
    } }
