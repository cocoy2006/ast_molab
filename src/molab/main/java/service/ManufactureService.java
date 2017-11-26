package molab.main.java.service;

import java.util.logging.Logger;

import molab.main.java.dao.ManufactureDao;
import molab.main.java.dao.ModelDao;
import molab.main.java.entity.Manufacture;
import molab.main.java.entity.Model;
import molab.main.java.util.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufactureService {

	private static final Logger log = Logger.getLogger(ManufactureService.class.getName());
	
	@Autowired
	private ManufactureDao dao;

	@Autowired
	private ModelDao md;
	
	public int saveOrUpdate(int id, String name) {
		Manufacture manufacture;
		if(id == -1) {
			manufacture = new Manufacture();
		} else {
			manufacture = dao.findById(id);
		}
		manufacture.setName(name);
		dao.saveOrUpdate(manufacture);
		return Status.Common.SUCCESS.getInt();
	}
	
	public int remove(int id) {
		Manufacture manufacture = dao.findById(id);
		for(Model model : md.findByManufactureId(id)) {
			md.remove(model);
		}
		dao.remove(manufacture);
		return Status.Common.SUCCESS.getInt();
	}
	
}
