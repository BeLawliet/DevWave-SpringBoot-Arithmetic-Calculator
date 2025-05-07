package arithmetic.calculator.api.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResponseDTO<T> {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private List<T> content;

    public PageResponseDTO(Page<T> pageData) {
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalElements = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
        this.last = pageData.isLast();
        this.content = pageData.getContent();
    }
}
