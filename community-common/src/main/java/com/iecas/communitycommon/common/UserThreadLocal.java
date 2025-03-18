/**
 * @Time: 2025/2/21 15:04
 * @Author: guoxun
 * @File: UserThreadLocal
 * @Description: 用户信息线程本地存储
 */

package com.iecas.communitycommon.common;

import com.iecas.communitycommon.model.user.entity.UserInfo;

public class UserThreadLocal {

    private static ThreadLocal<UserInfo> userInfoThreadLocal = ThreadLocal.withInitial(() -> null);

    /**
     * 设置用户信息
     * @param userInfo 用户信息
     */
    public static void setUserInfo(UserInfo userInfo){
        userInfoThreadLocal.set(userInfo);
    }


    /**
     * 获取用户
     * @return 用户信息
     */
    public static UserInfo getUserInfo(){
        return userInfoThreadLocal.get();
    }


    /**
     * 移除用户
     */
    public static void removeUser() {
        userInfoThreadLocal.remove();
    }
}
