package com.mamba.popidea.conf.constant;

/**
 * @version 1.0
 * @author: JoeBig7
 * @date: 2019/5/24 14:16
 * @description 业务状态类型
 */
public final class ServiceTypeEnum {


    enum CommentStatus {
        COMMENT_TO_QUESTION(0),
        COMMENT_TO_ARTICLE(1),
        COMMENT_TO_USER(2);

        private Integer type;

        CommentStatus(Integer type) {
            this.type = type;
        }
    }


}