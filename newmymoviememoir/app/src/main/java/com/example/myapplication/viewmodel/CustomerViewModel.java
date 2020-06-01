package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entity.Customer;
import com.example.myapplication.repository.CustomerRepository;

import java.util.List;

public class CustomerViewModel extends ViewModel {
    private CustomerRepository cRepository;
    private MutableLiveData<List<Customer>> allCustomers;
    public CustomerViewModel () { allCustomers=new MutableLiveData<>();
    }
    public void setCustomers(List<Customer> customers) { allCustomers.setValue(customers);
    }
    public LiveData<List<Customer>> getAllCustomers() { return cRepository.getAllCustmers();
    }
    public void initalizeVars(Application application){ cRepository = new CustomerRepository(application);
    }
    public void insert(Customer customer) { cRepository.insert(customer);
    }
    public void insertAll(Customer... customers) { cRepository.insertAll(customers);
    }
    public void deleteAll() { cRepository.deleteAll();
    }
    public void insertAll(Customer customer) { cRepository.delete(customer);
    }
    public void update(Customer... customers) {
        cRepository.updateCustomers(customers); }
    public Customer insertAll(int id) { return cRepository.findByID(id);
    }
    public Customer findByID(int customerId){ return cRepository.findByID(customerId);
    } }
