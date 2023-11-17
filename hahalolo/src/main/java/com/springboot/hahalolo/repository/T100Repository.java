package com.springboot.hahalolo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.springboot.hahalolo.entity.T100;
import com.springboot.hahalolo.exception.InternalServerException;

@Repository
public class T100Repository {

	private static Logger logger = Logger.getLogger(T100Repository.class);

	@Autowired
	private MongoDatabase database;

	@Autowired
	private JsonWriterSettings settingsJson;

	@SuppressWarnings("unchecked")
	public <T> List<Map<String, T>> getListT100(String turNm, Integer tn120, String lang, String topicId,
			Integer skip, Integer limit) {
		MongoCollection<Document> mongoCollection = database.getCollection("T100");
		BasicDBObject match = new BasicDBObject();
		Object ft101 = null;
		if (turNm != null) {
			match.append("t101.tv151", new BasicDBObject("$regex", turNm).append("$options", "i"));
		}
		if (tn120 != null) {
			match.append("tn120", tn120);
		}
		if (lang != null) {
			match.append("ft101.lang", lang);
			ft101 = new BasicDBObject("$filter", new BasicDBObject("input", "$ft101").append("as", "item").append(
					"cond", new BasicDBObject("$eq", Stream.of("$$item.lang", lang).collect(Collectors.toList()))));
		} else {
			ft101 = Integer.parseInt("1");
		}
		if (topicId != null) {
			match.append("pt550", topicId);
		}
		BasicDBObject project = new BasicDBObject("$project",
				new BasicDBObject("id", 1).append("ft101", ft101).append("currency", 1).append("dl146", 1)
						.append("dl147", 1).append("dl148", 1).append("dl149", 1).append("lang", 1).append("pt550", 1)
						.append("t101", 1).append("t102", 1).append("t550", 1).append("tn120", 1).append("tn123", 1)
						.append("tn127", 1).append("tn130", 1).append("tn131", 1).append("tn133", 1).append("tn134", 1)
						.append("tn135", 1));

		Bson matchs = new BasicDBObject("$match", match);
		Bson sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));
		Bson limits = new BasicDBObject("$limit", limit);
		Bson skips = new BasicDBObject("$skip", skip);
		List<Bson> query = new ArrayList<>();
		query.add(matchs);
		query.add(project);
		query.add(sort);
		query.add(skips);
		query.add(limits);
		Iterator<Document> result = mongoCollection.aggregate(query).iterator();
		List<Map<String, T>> listMapT100 = new ArrayList<>();
		Map<String, T> mapT100 = null;
		while (result.hasNext()) {
			Document docT100 = result.next();
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				mapT100 = objectMapper.readValue(docT100.toJson(settingsJson), Map.class);
			} catch (JsonMappingException e) {
				logger.error(e);
				throw new InternalServerException("lỗi chuyển đổi dữ liệu");
			} catch (JsonProcessingException e) {
				logger.error(e);
				throw new InternalServerException("lỗi chuyển đổi dữ liệu");
			}
			listMapT100.add(mapT100);
		}
		return listMapT100;
	}

	public Document insertT100(T100 t100) {
		MongoCollection<Document> mongoCollection = database.getCollection("T100");	
		BasicDBObject docT101 = new BasicDBObject("id",t100.getT101().getId())
				.append("lang", t100.getT101().getLang())
				.append("tv151", t100.getT101().getTv151());
		Document docT100 = new Document("currency", t100.getCurrency())
				.append("dl146",t100.getDl146())
				.append("dl147", t100.getDl147())
				.append("t101",docT101)
				.append("ft101", Arrays.asList(docT101))
				.append("langPag", t100.getLangPag())
				.append("lang", t100.getLang())
				.append("pb100", t100.getPb100())
				.append("pp100", t100.getPp100())
				.append("tn120", t100.getTn120())
				.append("tn123", t100.getTn123())
				.append("tn127", t100.getTn127())
				.append("tn134", t100.getTn134())
				.append("tv101", t100.getTv101());
		InsertOneResult result = mongoCollection.insertOne(docT100);
		ObjectId idT100 = result.getInsertedId().asObjectId().getValue();
		if(idT100 != null) {
			docT100.append("id", idT100.toString());
			docT100.append("t101",
					new BasicDBObject("lang", t100.getT101().getLang())
					.append("tv151", t100.getT101().getTv151()));
			docT100.remove("dl146");
			docT100.remove("ft101");
		}
		return docT100;
	}
}
