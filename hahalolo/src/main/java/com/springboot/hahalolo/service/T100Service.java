package com.springboot.hahalolo.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.hahalolo.entity.T100;
import com.springboot.hahalolo.exception.InternalServerException;
import com.springboot.hahalolo.repository.T100Repository;


@Service
public class T100Service {
	
	private static Logger logger = Logger.getLogger(T100Service.class);
	
	@Autowired
	private T100Repository t100Repository;
	
	public <T> List<Map<String,T>> getListT100(String turNm, Integer tn120, String lang, String topicId , Integer ofset, Integer limit){
		Integer skip = (ofset-1)* limit;
		List<Map<String,T>> listMapT100 = null;
		try {
			listMapT100 = t100Repository.getListT100( turNm,  tn120,  lang,  topicId, skip, limit);
		} catch (Exception e) {
			logger.error(e);
			throw new InternalServerException("Lỗi truy vấn dữ liệu");
		}
		return listMapT100;
	}
	
	
	public Document insertT100(T100 t100) {
		Document result = null;
		try {
			result = t100Repository.insertT100(t100);
		} catch (Exception e) {
			logger.error(e);
			throw new InternalServerException("lỗi insert!");
		}
		return result;
	}
	
}
