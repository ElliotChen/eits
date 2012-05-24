package tw.com.dsc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import tw.com.dsc.service.TaskManager;
import tw.com.dsc.task.MailTask;

@Component("taskManager")
public class TaskManagerImpl implements TaskManager {
	@Autowired
	private TaskExecutor taskExecutor;
	
	public void arrangeMailTask(MailTask task) {
		taskExecutor.execute(task);
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	
}
