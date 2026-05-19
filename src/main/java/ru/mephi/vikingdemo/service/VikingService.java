package ru.mephi.vikingdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.repository.VikingStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class VikingService {

    private final VikingFactory vikingFactory;
    private final VikingStorage vikingStorage;

    // список слушателей — всех, кто хочет знать об изменениях
    private final List<Runnable> changeListeners = new ArrayList<>();

    @Autowired
    public VikingService(VikingFactory vikingFactory, VikingStorage vikingStorage) {
        this.vikingFactory = vikingFactory;
        this.vikingStorage = vikingStorage;
    }


    public void addChangeListener(Runnable listener) {
        changeListeners.add(listener);
    }


    private void notifyListeners() {
        changeListeners.forEach(Runnable::run);
    }

    public List<Viking> findAll() {
        return vikingStorage.findAll();
    }

    public Viking createRandomViking() {
        Viking viking = vikingFactory.createRandomViking();
        Viking saved = vikingStorage.save(viking);
        notifyListeners();
        return saved;
    }

    public Viking addViking(Viking viking) {
        Viking saved = vikingStorage.save(viking);
        notifyListeners();
        return saved;
    }

    public Viking updateViking(int id, Viking viking) {
        Viking updated = vikingStorage.update(id, viking);
        notifyListeners();
        return updated;
    }

    public void deleteById(int id) {
        vikingStorage.deleteById(id);
        notifyListeners();
    }
}