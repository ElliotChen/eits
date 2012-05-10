package tw.com.dsc.service;

import java.util.List;

import tw.com.dsc.domain.ErrorType;
import tw.com.dsc.domain.Group;
import tw.com.dsc.to.Series;
import tw.com.dsc.to.User;

public interface SystemService {
	List<Series> listAllSeries();
	ErrorType login(final User user);
	void parseGroup(List<Group> groups, User user);
}
