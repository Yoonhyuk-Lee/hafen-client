package gugiman.automation.worker;

import gugiman.automation.task.*;
import java.util.*;

public class Worker
{
    private final Task[] tasks;
    private int curidx;
    private int size;

    private Worker(final List<Task> tasks)
    {
        this.size = tasks.size();
        this.tasks = tasks.toArray(new Task[this.size]);
        this.curidx = 0;
    }

    public boolean check()
    {
        while(curidx < size)
        {
            if(!tasks[curidx].check())
                return false;

            ++curidx;
        }

        return true;
    }

    public static class Builder {
        private List<Task> tasks = new LinkedList<>();

        // 작업 추가 메서드
        public Builder addTask(Task task) {
            tasks.add(task);
            return this;
        }

        // Worker 객체 생성 메서드
        public Worker build() {
            return new Worker(tasks);
        }
    }
}
