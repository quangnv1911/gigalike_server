package com.gigalike.marketing.service.impl;

import com.gigalike.marketing.repository.WebConfigRepository;
import com.gigalike.marketing.service.IWebConfigService;
import com.gigalike.shared.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebConfigService implements IWebConfigService {
    WebConfigRepository webConfigRepository;

    @Override
    public String getWebConfig(String domain) {
        var webConfig = webConfigRepository.findFirstByDomain(domain)
                .orElseThrow(() ->new NotFoundException("webConfig not found"));

        return webConfig.getWebToken();
    }
}
