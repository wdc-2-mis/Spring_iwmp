package app.service;

import java.math.BigInteger;

public interface HitCountService {
	BigInteger getHitCount(String sessionId);
}
