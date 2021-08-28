package com.smart.sso.client.local.manager;

import com.smart.sso.client.common.CodeContent;
import com.smart.sso.client.common.ExpirationPolicy;
import com.smart.sso.client.local.session.CodeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地授权码管理
 * 
 * @author Joe
 */
@Component
public class LocalCodeManager implements CodeManager, ExpirationPolicy {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, DummyCode> codeMap = new ConcurrentHashMap<>();
	
	@Override
	public void create(String code, CodeContent codeContent) {
		codeMap.put(code, new DummyCode(codeContent, System.currentTimeMillis() + getExpiresIn() * 1000));
		logger.info("授权码生成成功, code:{}", code);
	}

	@Override
	public CodeContent getAndRemove(String code) {
		DummyCode dc = codeMap.remove(code);
        if (dc == null || System.currentTimeMillis() > dc.expired) {
            return null;
        }
        return dc.codeContent;
	}
	
	@Scheduled(cron = SCHEDULED_CRON)
	@Override
    public void verifyExpired() {
		codeMap.forEach((code, dummyCode) -> {
            if (System.currentTimeMillis() > dummyCode.expired) {
                codeMap.remove(code);
                logger.info("授权码已失效, code:{}", code);
            }
        });
    }
	
    private class DummyCode {
    	private CodeContent codeContent;
        private long expired; // 过期时间

        public DummyCode(CodeContent codeContent, long expired) {
            this.codeContent = codeContent;
            this.expired = expired;
        }
    }
}
