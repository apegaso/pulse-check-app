package com.ncr.project.pulsecheck.service.mapper.util;

import java.util.HashSet;
import java.util.Set;
import com.ncr.project.pulsecheck.domain.Category;

public class CategoryUtils {
    public static Set<Category> getFathers(Category category){
        Set<Category> ret = new HashSet<>();
        Category father = category.getFather();
        if (father != null) {
            ret.add(father);
            ret.addAll(getFathers(father));
        }
        return ret;
    }

}