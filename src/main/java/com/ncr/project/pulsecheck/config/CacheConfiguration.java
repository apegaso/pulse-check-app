package com.ncr.project.pulsecheck.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.ncr.project.pulsecheck.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Organization.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Organization.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Organization.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Organization.class.getName() + ".admins", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Event.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Event.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Event.class.getName() + ".leads", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.ClientLead.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.ClientLead.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.OrgAdmin.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.OrgAdmin.class.getName() + ".organizations", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Participant.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Participant.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Question.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Question.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Category.class.getName() + ".sons", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Category.class.getName() + ".questions", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.CategoryLevel.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.CategoryLevel.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Questionnaire.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.QuestionnaireAnswer.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.UserExt.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.Email.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.QuestionGroup.class.getName(), jcacheConfiguration);
            cm.createCache(com.ncr.project.pulsecheck.domain.QuestionGroup.class.getName() + ".questions", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
