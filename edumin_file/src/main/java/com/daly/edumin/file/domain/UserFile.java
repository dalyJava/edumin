package com.daly.edumin.file.domain;

import lombok.Data;

/**
 * Created by daly on 2018/7/12.
 */

public @Data class UserFile {
    private String fileName;
    private String fileId;
    private Double fileSize;
}
