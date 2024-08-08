package service;

import dao.Storage;
import entities.Task;
import lombok.Data;

@Data
public class Manager {

    private final Storage storage;

    public Task[] Manager() {}
}
