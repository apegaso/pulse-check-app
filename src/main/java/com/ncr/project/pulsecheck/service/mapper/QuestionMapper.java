package com.ncr.project.pulsecheck.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.QuestionDTO;
import com.ncr.project.pulsecheck.service.mapper.util.CategoryUtils;

import org.mapstruct.*;

import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {
    CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    @Override
    default QuestionDTO toDto(Question entity) {
        QuestionDTO ret = new QuestionDTO();
        ret.setId(entity.getId());
        ret.setOrder(entity.getOrder());
        ret.setQuestion(entity.getQuestion());
        final Set<Category> allCategories = new HashSet<>();
        entity.getCategories().forEach(c->{
            allCategories.add(c); 
            allCategories.addAll(CategoryUtils.getFathers(c));
        });
        ret.setCategories(allCategories.stream().map(categoryMapper::toDto).collect(Collectors.toSet()));

        return ret;
    }

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
