package ru.jevent.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Task;
import ru.jevent.util.DbPopulator;

import java.util.List;

import static ru.jevent.Profiles.JPA;
import static ru.jevent.Profiles.POSTGRES;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({POSTGRES, JPA})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private DbPopulator dbPopulator;
    @Autowired
    private TestData testData;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Task task = taskService.get(100025);
//        if(task.isNew() ||
//                task.getTarget().size() != 6)
//            throw new Exception();
    }

    @Test
    public void testSimpleTaskSave() throws Exception {
        Task testTask = testData.getSimpleTask();
        taskService.save(testTask);

//        Task savedTask = taskService.get(testTask.getId());
//        if(!testTask.equals(savedTask)) {
//            throw new Exception();
//        }
    }

    @Test
    public void testCompletedTaskSave() throws Exception {
        Task testTask = testData.getCompletedTask();
        taskService.save(testTask);

//        Task savedTask = taskService.get(testTask.getId());
//        if(!testTask.equals(savedTask)) {
//            throw new Exception();
//        }
    }

    @Test
    public void testUpdate()throws Exception{
        Task testTask = testData.getCompletedTask();
        taskService.save(testTask);
        testTask.setDescription("Update Description");

        taskService.update(testTask);
        Task taskFromDB = taskService.get(testTask.getId());
        if(!testTask.getDescription().equals(taskFromDB.getDescription())){
            throw new Exception();
        }

    }

    @Test
    public void testDeleteAndGetAllCreated() throws Exception{
        taskService.delete(100025);
        List<Task> allTask =taskService.getAllCreated(100006);
        for(Task task : allTask){
           if(task.getId() == 100025){
               throw new Exception();
           }
        }
    }
}
