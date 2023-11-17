package com.springboot.hahalolo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "T100")
public class T100 {

	@Id
	private String id;
	private String lang;
	private String currency;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dl146;
	private String dl147;
	private String dl148;
	private String dl149;
	private List<T101> ft101;
	private T101 t101;
	private String pt550;
	private T102 t102;
	private T550 t550;
	private Integer tn120;
	private Integer tn123;
	private Integer tn127;
	private Integer tn130;
	private Integer tn131;
	private Integer tn133;
	private Integer tn134;
	private Integer tn135;
	private String pp100;
	private String pb100;
	private String tv101;
	private String langPag;

	@Data
	public class T101 {
		@Id
		private String id;

		private String lang;

		private String tv151;

		private String tv152;

		private String tv153;

		private String tv154;

		private String tv159;
	}
}
