package se.uhr.nya.integration.sim.server.feed.entity;

import org.springframework.jdbc.core.JdbcTemplate;

import se.uhr.nya.atom.feed.server.entity.AtomFeed;
import se.uhr.nya.atom.feed.server.entity.AtomFeedDAO;

/**
 * Provides a memory optimization for Derby DB, see
 * https://issues.apache.org/jira/browse/DERBY-6818
 */

public class DerbyAtomFeedDAO extends AtomFeedDAO {

	private JdbcTemplate jdbcTemplate;

	public DerbyAtomFeedDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public AtomFeed fetchRecent() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F.FEED_ID, F.NEXT_FEED_ID, F.PREV_FEED_ID, XMLSERIALIZE(F.FEED_XML AS CLOB(1M)) AS FEED_XML FROM ATOM_FEED F WHERE F.FEED_ID = ( SELECT MAX( FEED_ID ) FROM ATOM_FEED )");
		return jdbcTemplate.queryForObject(sql.toString(), new AtomFeedDAO.AtomFeedRowMapper());
	}

}
