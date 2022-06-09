package com.ridingmate.api.payload.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileResult {
    private String originalFileName;
    private String url;
}
