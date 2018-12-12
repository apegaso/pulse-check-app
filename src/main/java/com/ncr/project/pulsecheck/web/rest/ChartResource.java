package com.ncr.project.pulsecheck.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.ResponseUtil;

import java.util.Optional;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.service.ChartService;
import com.ncr.project.pulsecheck.service.dto.chart.RadarDatasetDTO;
import com.ncr.project.pulsecheck.service.dto.chart.ScatterDatasetDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/chart")
public class ChartResource {
    private final Logger log = LoggerFactory.getLogger(ChartResource.class);

    private static final String ENTITY_NAME = "chart";

    private final ChartService chartService;

    public ChartResource(ChartService chartService){
        this.chartService = chartService;
    }

    @GetMapping("/importance-performance/{eventId}")
    @Timed
    public ResponseEntity<ScatterDatasetDTO> getImportanceVsPerformance(@PathVariable Long eventId){
        log.debug("REST getImportanceVsPerformance: eventId:{}", eventId);
        Optional<ScatterDatasetDTO> ret = chartService.getImportanceVsPerformance(eventId);
        return ResponseUtil.wrapOrNotFound(ret);
    }

    @GetMapping("/pillars/all/{eventId}")
    @Timed
    public ResponseEntity<RadarDatasetDTO> getPillarsAll(@PathVariable Long eventId){
        log.debug("REST getPillarsAll: eventId:{}", eventId);
        Optional<RadarDatasetDTO> ret = chartService.getPillarsAll(eventId);
        return ResponseUtil.wrapOrNotFound(ret);
    }

    @GetMapping("/pillars/participant/{eventId}/{userId}")
    @Timed
    public ResponseEntity<RadarDatasetDTO> getPillarParticipant(@PathVariable Long eventId, @PathVariable Long userId){
        log.debug("REST getPillarParticipant: eventId:{}", eventId);
        Optional<RadarDatasetDTO> ret = chartService.getPillarParticipant(eventId, userId);
        return ResponseUtil.wrapOrNotFound(ret);
    }

    @GetMapping("/pillars/customer/{eventId}")
    @Timed
    public ResponseEntity<RadarDatasetDTO> getPillarCustomer(@PathVariable Long eventId){
        log.debug("REST getPillarCustomer: eventId:{}", eventId);
        Optional<RadarDatasetDTO> ret = chartService.getPillarCustomer(eventId);
        return ResponseUtil.wrapOrNotFound(ret);
    }

    @GetMapping("/pillars/customer-other/{eventId}")
    @Timed
    public ResponseEntity<RadarDatasetDTO> getPillarCustomerOther(@PathVariable Long eventId){
        log.debug("REST getPillarCustomerOther: eventId:{}", eventId);
        Optional<RadarDatasetDTO> ret = chartService.getPillarCustomerOther(eventId);
        return ResponseUtil.wrapOrNotFound(ret);
    }
    
    @GetMapping("/sentiment-analisys/{type}/{eventId}")
    @Timed
    public ResponseEntity<RadarDatasetDTO> getSentimentAnalisys(@PathVariable String type, @PathVariable Long eventId){
        log.debug("REST getSentimentAnalisys: eventId:{}", eventId);
        Optional<RadarDatasetDTO> ret = chartService.getSentimentAnalisys(eventId, type);
        return ResponseUtil.wrapOrNotFound(ret);
    }

    @GetMapping("/digital-capabilities/{eventId}")
    @Timed
    public ResponseEntity<ScatterDatasetDTO> getDigitalCapabilities(@PathVariable Long eventId){
        log.debug("REST getDigitalCapabilities: eventId:{}", eventId);
        Optional<ScatterDatasetDTO> ret = chartService.getDigitalCapabilities(eventId);
        return ResponseUtil.wrapOrNotFound(ret);
    }

    
    
    
}