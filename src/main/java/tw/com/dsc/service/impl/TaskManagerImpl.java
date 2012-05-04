package tw.com.dsc.service.impl;

import org.springframework.core.task.TaskExecutor;

import tw.com.dsc.service.TaskManager;
import tw.com.dsc.task.MailTask;

public class TaskManagerImpl implements TaskManager {
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
