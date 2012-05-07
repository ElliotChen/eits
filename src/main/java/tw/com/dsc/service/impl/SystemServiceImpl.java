package tw.com.dsc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.dsc.service.SystemService;
import tw.com.dsc.to.Model;
import tw.com.dsc.to.Series;

@Service("systemService")
@Transactional(readOnly=true)
public class SystemServiceImpl implements SystemService {
	private static List<Series> series;
	static {
		series = new ArrayList<Series>();
		
		for (int i = 0; i < 100; i++) {
			Series s = new Series("Series"+i, "Series"+i);
			series.add(s);
			int start = (i * 10) + 1;
			for (int mi = start; mi < start + 10; mi++ ) {
				s.addModel(new Model("model"+mi, "model"+mi));
			}
		}
	}
	@Override
	public List<Series> listAllSeries() {
		return series;
	}

}
