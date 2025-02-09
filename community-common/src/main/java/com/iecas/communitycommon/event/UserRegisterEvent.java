/**
 * @Time: 2025/2/9 18:34
 * @Author: guoxun
 * @File: UserRegisterEvent
 * @Description:
 */

package com.iecas.communitycommon.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor
@Builder
public class UserRegisterEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户鉴权信息表id
     */
    private Long authUserId;

    /**
     * 用户邮箱
     */
    private String email;
}
