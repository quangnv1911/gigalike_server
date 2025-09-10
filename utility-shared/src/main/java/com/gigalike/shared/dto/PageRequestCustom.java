package com.gigalike.shared.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gigalike.shared.constant.SortDirection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE
)
public class PageRequestCustom {
    int page;

    int size;

    String sortBy;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    SortDirection sortDirection = SortDirection.DESC;
}
