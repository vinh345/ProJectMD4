package com.ra.model.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;
@Data
@Builder
public class PageResponse
{
    private Object data;
    private boolean first;
    private boolean last;
    private int totalPages;
    private int totalElements;
    private int size;
    private int number;
    private Sort sort;

}
