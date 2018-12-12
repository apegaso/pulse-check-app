package com.ncr.project.pulsecheck.service;

import java.util.Optional;

import com.ncr.project.pulsecheck.service.dto.chart.RadarDatasetDTO;
import com.ncr.project.pulsecheck.service.dto.chart.ScatterDatasetDTO;

public interface ChartService{

	Optional<ScatterDatasetDTO> getImportanceVsPerformance(Long eventId);

	Optional<RadarDatasetDTO> getPillarsAll(Long eventId);

	Optional<RadarDatasetDTO> getPillarParticipant(Long eventId, Long userId);

	Optional<RadarDatasetDTO> getPillarCustomer(Long eventId);

	Optional<RadarDatasetDTO> getPillarCustomerOther(Long eventId);

	Optional<RadarDatasetDTO> getSentimentAnalisys(Long eventId, String type);

	Optional<ScatterDatasetDTO> getDigitalCapabilities(Long eventId);

}