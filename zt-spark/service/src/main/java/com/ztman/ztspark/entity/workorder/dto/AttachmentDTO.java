package com.ztman.ztspark.entity.workorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {
    private Long id;
    /**
     * 虚拟路径
     */
    private String path;
    /**
     * 文件类型(1.语音 2.图片 3.视频 4.文件)
     */
    private String type;
}
