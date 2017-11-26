package molab.main.java.service;

import java.util.List;

import molab.main.java.dao.ServerDao;
import molab.main.java.entity.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
	
	@Autowired
	private ServerDao dao;
	
	public List<Server> findAll() {
		return dao.findAll();
	}
	
}
