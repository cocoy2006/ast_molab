package molab.main.java.service;

import java.util.logging.Logger;

import molab.main.java.dao.ModelDao;
import molab.main.java.entity.Model;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

	private static final Logger log = Logger.getLogger(ModelService.class.getName());

	@Autowired
	private ModelDao dao;
	
	public int saveOrUpdate(int id, int manufactureId, String name, float occupancy) {
		Model model;
		if(id == -1) {
			model = new Model();
		} else {
			model = dao.findById(id);
		}
		model.setManufactureId(manufactureId);
		model.setName(name);
		model.setOccupancy(occupancy);
		dao.saveOrUpdate(model);
		return Status.Common.SUCCESS.getInt();
	}
	
	public int remove(int manufactureId, String[] ids) {
		Model model;
		for(String id : ids) {
			int i = Integer.parseInt(id);
			model = dao.load(i);
			if(model != null && model.getManufactureId() == manufactureId) {
				dao.remove(model);
			}
		}
		return Status.Common.SUCCESS.getInt();
	}
	
}
