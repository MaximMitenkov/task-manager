package org.mitenkov.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.FilterType;
import org.mitenkov.enums.SortType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public void addTask(Task task) {
        entityManager.persist(task);
    }

    @Transactional
    public void removeTask(Task task) {
        entityManager.createQuery("delete from Task t where t.id = :id")
                .setParameter("id", task.getId())
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<Task> getFilteredTasks(FilterType type) {
        return entityManager.createQuery("select t from Task t where t.class = :type", Task.class)
                .setParameter("type", type.getFilterClass())
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Task> getSortedTasks(SortType type) {
        return entityManager.createQuery("select t from Task t order by :sort desc ", Task.class)
                .setParameter("sort", type.toString().toLowerCase())
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Task> getSortedAndFilteredTasks(FilterType type, SortType sortType) {
        return entityManager.createQuery(
                "select t from Task t where t.class = :type order by :sort desc", Task.class)
                .setParameter("type", type.getFilterClass())
                .setParameter("sort", sortType.name())
                .getResultList();

    }

    @Transactional(readOnly = true)
    public List<Task> getTasks() {
        return entityManager.createQuery("select t from Task t", Task.class).getResultList();
    }

}
