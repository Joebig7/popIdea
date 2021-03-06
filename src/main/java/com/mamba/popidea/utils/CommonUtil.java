package com.mamba.popidea.utils;

import com.mamba.popidea.dao.UserBeanMapper;
import com.mamba.popidea.exception.ErrorCodes;
import com.mamba.popidea.exception.RestException;
import com.mamba.popidea.exception.ServiceException;
import com.mamba.popidea.model.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @author: JoeBig7
 * @date: 2019/5/21 13:58
 * @description 封装 popidea专用工具
 */
public class CommonUtil {


    private static UserBeanMapper userBeanMapper;

    @Autowired
    public static void setUserBeanMapper(UserBeanMapper userBeanMapper) {
        CommonUtil.userBeanMapper = userBeanMapper;
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public static Long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            return Long.valueOf(request.getAttribute("userId").toString());
        } catch (NumberFormatException e) {
            throw RestException.newInstance(ErrorCodes.TOKEN_CHECKED_ERROR);
        }
    }


    /**
     * 判断是否为空
     *
     * @param t
     * @param errorCodes
     * @param <T>
     */
    public static <T> void assertNull(T t, ErrorCodes errorCodes) {
        if (t == null) {
            throw ServiceException.newInstance(errorCodes);
        }
    }

    /**
     * 判断是否相等
     *
     * @param source
     * @param target
     * @param errorCodes
     * @param <T>
     * @param <S>
     */
    public static <T, S> void assertEqual(T source, S target, ErrorCodes errorCodes) {
        if (source.equals(target)) {
            throw ServiceException.newInstance(errorCodes);
        }
    }


    /**
     * 获取评论树形结构
     *
     * @param var1
     * @return
     */
    public static List<CommentVO> getCommentTreeStructure(List<CommentVO> var1) {
        List<CommentVO> list = var1.stream().filter(t -> t.getReplyCommentId() == 0).collect(Collectors.toList());
        list.parallelStream()
                .forEach(t -> {
                    List<CommentVO> temp = var1.parallelStream()
                            .filter(t2 -> t2.getReplyCommentId() == t.getCommentId())
                            .collect(Collectors.toList());
                    t.setChildList(temp);
                });

        return list;
    }
}