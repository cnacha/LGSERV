package com.telabase.json.formatter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void serialize(Date d, JsonGenerator gen, SerializerProvider pro)
			throws IOException, JsonProcessingException {
		fm.setTimeZone(TimeZone.getTimeZone("GMT+7"));

		gen.writeString(fm.format(d));

	}

}
