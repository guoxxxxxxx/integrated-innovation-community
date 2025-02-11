/**
 * @Time: 2025/2/11 21:33
 * @Author: guoxun
 * @File: TokenVO
 * @Description: 鉴权返回的消息
 */

package com.iecas.communitycommon.model.auth.vo;


import com.iecas.communitycommon.model.auth.domain.Claims;
import com.iecas.communitycommon.model.auth.domain.ParseClaims;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class TokenVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 对象信息
     */
    private ParseClaims parseClaims;
}
