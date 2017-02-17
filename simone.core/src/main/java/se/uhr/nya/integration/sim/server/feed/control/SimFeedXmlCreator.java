package se.uhr.nya.integration.sim.server.feed.control;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.uhr.nya.atom.feed.server.control.FeedXmlCreator;
import se.uhr.nya.integration.sim.extension.api.Constants;
import se.uhr.nya.integration.sim.server.feed.entity.SimFeedRepository;

@Startup
@Singleton
@DependsOn("FeedDatabaseInitializer")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class SimFeedXmlCreator {

	private static final long DELAY = 2_000L;

	private final static Logger LOG = LoggerFactory.getLogger(SimFeedCreator.class);

	@Inject
	private FeedXmlCreator feedXmlCreator;

	@Inject
	private SimFeedRepository feedRepository;

	@Resource
	private TimerService timer;

	@PostConstruct
	public void initialize() {
		schedule();
	}

	@Timeout
	public void createXmlForFeeds() {
		try {
			feedXmlCreator.createXmlForFeeds(feedRepository, Constants.FEED_URI);
		} catch (Exception e) {
			LOG.error("Failed to create xml for feed", e);
		} finally {
			schedule();
		}
	}

	private void schedule() {
		timer.createSingleActionTimer(DELAY, new TimerConfig(null, false));
	}
}
