package app.dao;

import java.math.BigInteger;

public interface HitCountDao {
	BigInteger getHitCount(String sessionId);

}
