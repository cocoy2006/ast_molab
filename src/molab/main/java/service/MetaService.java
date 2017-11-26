package molab.main.java.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import molab.main.java.dao.ManufactureDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.Manufacture;
import molab.main.java.entity.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaService {

	private static final Logger log = Logger.getLogger(MetaService.class.getName());
	
	@Autowired
	private ManufactureDao mad;

	@Autowired
	private ModelDao mod;
	
	public List<Map> findAll() {
		List<Manufacture> manufactures = mad.findAll();
		List manufactureList = new ArrayList();
		for(Manufacture manufacture : manufactures) {
			Map manufactureMap = new HashMap();
			manufactureMap.put("title", manufacture.getName());
			manufactureMap.put("key", manufacture.getId());
			manufactureMap.put("state", manufacture.getState());
			List<Model> models = mod.findByManufactureId(manufacture.getId());
			List modelList = new ArrayList();
			for(Model model : models) {
				Map modelMap = new HashMap();
				modelMap.put("title", model.getName());
				modelMap.put("key", model.getId());
				modelMap.put("manufactureId", model.getManufactureId());
				modelMap.put("occupancy", model.getOccupancy());
				modelMap.put("state", model.getState());
				modelList.add(modelMap);
			}
			Collections.sort(modelList, new Comparator<Map>() {

				@Override
				public int compare(Map o1, Map o2) {
					return o1.get("title").toString().compareTo(o2.get("title").toString());
				}
				
			});
			manufactureMap.put("children", modelList);
			manufactureList.add(manufactureMap);
		}
		Collections.sort(manufactureList, new Comparator<Map>() {

			@Override
			public int compare(Map o1, Map o2) {
				return o1.get("title").toString().compareTo(o2.get("title").toString());
			}
			
		});
		return manufactureList;
	}
	
}
