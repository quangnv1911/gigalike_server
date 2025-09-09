package com.gigalike.shared.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gigalike.shared.constant.SortBy;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE
)
public class PageRequest {
    int page;

    int size;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    SortBy sortBy = SortBy.DESC;

    String sortDirection;
}
