package tw.com.dsc.service;

import tw.com.dsc.task.MailTask;
import tw.com.dsc.task.PackageMailTask;

public interface TaskManager {
	void arrangeMailTask(MailTask task);
	void arrangePackageMailTask(PackageMailTask task);
}
