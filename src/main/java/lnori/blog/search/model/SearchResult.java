package lnori.blog.search.model;

import lombok.Data;

import java.util.List;

/**
 * @author Lnori
 */
@Data
public class SearchResult<T> {
    private Long total;
    private Integer totalPage;
    private List<T> list;

    public SearchResult() {
    }

    public SearchResult(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public SearchResult(Long total, Integer totalPage, List<T> list) {
        this.total = total;
        this.totalPage = totalPage;
        this.list = list;
    }
}