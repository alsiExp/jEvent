package ru.jevent.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jevent.TestData;
import ru.jevent.model.Task;
import ru.jevent.util.DbPopulator;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/service.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
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
        if(task.isNew() ||
                task.getAttachList().size() != 4 ||
                task.getTarget().size() != 6)
            throw new Exception();
    }

    @Test
    public void testSimpleTaskSave() throws Exception {
        Task testTask = testData.getSimpleTask();
        taskService.save(testTask);

        Task savedTask = taskService.get(testTask.getId());
        if(testTask.equals(savedTask)) {
            throw new Exception();
        }
    }
}
