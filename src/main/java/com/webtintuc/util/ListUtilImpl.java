package com.webtintuc.util;

import com.webtintuc.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ListUtilImpl {

    public static List<Article> intersect(List<Article> src, List<Article> des) {
        List<Article> result = new ArrayList<>();
        for (Article srcArticle : src) {
            for (Article desArticle : des) {
                if (srcArticle.getId().equals(desArticle.getId())) {
                    result.add(srcArticle);
                }
            }
        }
        return result;
    }
}
