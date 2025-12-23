package com.Homis.ddeugae.Make.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class MadeUploadDto {

    @NotNull(message = "도안명은 공백일 수 없습니다.")
    @Length(min=1, max=25, message = "도안명은 최대 25자까지만 가능합니다.")
    private String madeName;

    @NotNull(message = "도안 규격 값이 없습니다.")
    @Min(value = 10, message = "도안 규격은 최소 10x10입니다.")
    @Max(value = 100, message = "도안 규격은 최대 100x100입니다.")
    private Integer size;

    @NotNull(message = "도안 디자인 정보가 없습니다.")
    private String madeDesign;

    private String script;
    private List<String> download;
}
