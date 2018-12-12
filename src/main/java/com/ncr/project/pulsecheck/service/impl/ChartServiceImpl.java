package com.ncr.project.pulsecheck.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ncr.project.pulsecheck.domain.Category;
import com.ncr.project.pulsecheck.domain.Questionnaire;
import com.ncr.project.pulsecheck.domain.QuestionnaireAnswer;
import com.ncr.project.pulsecheck.repository.CategoryRepository;
import com.ncr.project.pulsecheck.repository.QuestionnaireAnswerRepository;
import com.ncr.project.pulsecheck.repository.QuestionnaireRepository;
import com.ncr.project.pulsecheck.service.ChartService;
import com.ncr.project.pulsecheck.service.dto.chart.RadarDataRecordDTO;
import com.ncr.project.pulsecheck.service.dto.chart.RadarDatasetDTO;
import com.ncr.project.pulsecheck.service.dto.chart.ScatterDatasetDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartServiceImpl implements ChartService {

    private final Logger log = LoggerFactory.getLogger(ChartServiceImpl.class);

    private final QuestionnaireAnswerRepository questionnaireAnswerRepository;
    
    private final QuestionnaireRepository questionnaireRepository;

    private final CategoryRepository categoryRepository;

    public ChartServiceImpl(QuestionnaireAnswerRepository questionnaireAnswerRepository, QuestionnaireRepository questionnaireRepository, CategoryRepository categoryRepository){
        this.questionnaireAnswerRepository = questionnaireAnswerRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<ScatterDatasetDTO> getImportanceVsPerformance(Long eventId) {
        return null;
    }

    @Override
    public Optional<RadarDatasetDTO> getPillarsAll(Long eventId) {
        return null;
    }

    @Override
    public Optional<RadarDatasetDTO> getPillarParticipant(Long eventId, Long participantId) {
        String participantName = "";
        Optional<Questionnaire> questionnaireOpt = questionnaireRepository.findByParticipantIdAndEventId(participantId, eventId);
        if (!questionnaireOpt.isPresent())
            return Optional.empty();
        Questionnaire questionnaire = questionnaireOpt.get();
        List<QuestionnaireAnswer> answers = questionnaireAnswerRepository.findAllByQuestionnaireId(questionnaire.getId());
        

        Map<Category, RadarDataRecordDTO> categoryMap = Maps.newHashMap();

        for (QuestionnaireAnswer answer : answers) {
            Category pillarCatergory = getPillarCatergory(answer.getQuestion().getCategories());
            if(pillarCatergory == null) {
                log.error("Error creating Pillar chart: empty pillar category");
                return null;
            }
            
            RadarDataRecordDTO record = getRecorData(categoryMap, pillarCatergory, participantName);
            
        }

        List<RadarDataRecordDTO> datasets = null;
        List<String> labels = Arrays.asList("Stategy", "Organization", "Customer", "Ecosystem", "Operations", "Tecnology", "Innovation");
        


        RadarDatasetDTO ret = new RadarDatasetDTO();
        ret.setDatasets(datasets);
        ret.setLabels(labels);
        
        return Optional.of(ret);
    }
    
    private RadarDataRecordDTO getRecorData(Map<Category, RadarDataRecordDTO> categoryMap, Category pillarCatergory, String label) {
        RadarDataRecordDTO radarDataRecordDTO = categoryMap.get(pillarCatergory);
        if(radarDataRecordDTO == null){
            radarDataRecordDTO = new RadarDataRecordDTO();
            radarDataRecordDTO.setData(Lists.newArrayList());
            radarDataRecordDTO.setLabel(label);
            categoryMap.put(pillarCatergory, radarDataRecordDTO);
        }
        return radarDataRecordDTO;
    }

    private Category getPillarCatergory(Set<Category> categories) {
        return null;
    }

    @Override
    public Optional<RadarDatasetDTO> getPillarCustomer(Long eventId) {
        return null;
    }

    @Override
    public Optional<RadarDatasetDTO> getPillarCustomerOther(Long eventId) {
        return null;
    }

    @Override
    public Optional<RadarDatasetDTO> getSentimentAnalisys(Long eventId, String type) {
        return null;
    }

	@Override
	public Optional<ScatterDatasetDTO> getDigitalCapabilities(Long eventId) {
		return null;
	}

}