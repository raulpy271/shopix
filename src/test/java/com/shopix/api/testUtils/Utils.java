package com.shopix.api.testUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	
	public static <T> List<T> readValues(String res, Class<T> cls) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Iterator<T> resIter = mapper.readerFor(cls).readValues(res);
		List<T> resList = new ArrayList<>();
		resIter.forEachRemaining(resList::add);
		return resList;
	}

}
