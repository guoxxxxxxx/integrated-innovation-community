/**
 * @Time: 2025/3/29 11:27
 * @Author: guoxun
 * @File: CheckFileUploadIsOkVO
 * @Description:
 */

package com.iecas.communityfile.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CheckFileUploadIsOkVO {

    /**
     * 上传状态
     */
    private Boolean status;

    /**
     * 需要重传的分块id
     */
    private List<Integer> failChunksIdList;

}
