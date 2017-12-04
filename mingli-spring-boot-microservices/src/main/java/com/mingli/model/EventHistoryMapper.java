package com.mingli.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class EventHistoryMapper implements RowMapper<EventHistory> {

	public EventHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
		EventHistory pd = new EventHistory();
		pd.setEvent_id(rs.getInt("event_id"));
		pd.setEvent_name(rs.getString("event_name"));
		return pd;
	}

}
