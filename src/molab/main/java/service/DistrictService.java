package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.DistrictDao;
import molab.main.java.entity.District;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {
	
	@Autowired
	private DistrictDao dao;
	
	public List<District> findAll() {
		return dao.findAll();
	}
	
}
