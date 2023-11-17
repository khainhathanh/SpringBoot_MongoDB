package com.spring.demo2.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "DateOpen")
public class DateOpen {
	
	@Id
	private ObjectId id;

	private List<String> dateAvailable;
	
	private Integer status;
	
	private String tourID;
}
