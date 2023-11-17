package com.springboot.hahalolo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mifmif.common.regex.Generex;
import com.springboot.hahalolo.entity.ResponseOutput;
import com.springboot.hahalolo.entity.T100;
import com.springboot.hahalolo.service.T100Service;

@RestController
public class T100API {
	
	@Autowired
	private T100Service t100Service;
	
	@GetMapping(value = "/hahalolo")
	public <T> ResponseOutput<List<Map<String,T>>> getListT100(@RequestParam(value = "turNm", required = false) String turNm,
			@RequestParam(value = "tn120", required = false) Integer tn120,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "topicId", required = false) String topicId,
			@RequestParam(value = "ofset", required = false , defaultValue = "1") Integer ofset,
			@RequestParam(value = "limit", required = false , defaultValue = "5") Integer limit){
		if(ofset <= 0 && limit <=0) {
			ofset = 1;
			limit = 5;
		}
		List<Map<String,T>> listMapT100 = t100Service.getListT100(turNm, tn120, lang, topicId, ofset, limit);
		ResponseOutput<List<Map<String,T>>> responseOutput = new ResponseOutput<>();
		if(!listMapT100.isEmpty()) {
			responseOutput.setCode(200);
			responseOutput.setSuccess(true);
			responseOutput.setData(listMapT100);
		}else {
			responseOutput.setCode(2004);
			responseOutput.setMessage("No Content");
			responseOutput.setSuccess(false);
		}
		return responseOutput;
	}
	
	@PostMapping(value = "/hahalolo")
	public ResponseOutput<Document> insertT100(@RequestBody T100 t100){
		Generex generex = new Generex("T-\\d{11}-[A-Z0-9]\\d{6}");
		T100.T101 t101 = t100.getT101();
		t101.setId(new ObjectId().toHexString());
		t100.setT101(t101);
		t100.setDl146(new Date(System.currentTimeMillis()));
		t100.setDl147("huynhlap30081998@gmail.com");
		t100.setLangPag("en");
		t100.setLang(t100.getT101().getLang());	
		t100.setTn120(0);
		t100.setTn123(0);
		t100.setTn127(0);
		t100.setTn134(0);
		t100.setTv101(generex.random());
		Document result = t100Service.insertT100(t100);
		ResponseOutput<Document> responseOutput = new ResponseOutput<>();
		if(result != null) {
			responseOutput.setCode(200);
			responseOutput.setSuccess(true);
			responseOutput.setData(result);
		}else {
			responseOutput.setCode(2023);
			responseOutput.setMessage("No found record insert!");
			responseOutput.setSuccess(false);
		}
		return responseOutput;
	}
}
