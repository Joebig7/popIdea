package com.mamba.popidea.service;

import com.mamba.popidea.model.QuestionBean;
import com.mamba.popidea.model.bo.QuestionBeanBo;
import com.mamba.popidea.model.common.result.RestData;
import com.mamba.popidea.model.vo.QuestionVO;

/**
 * @version 1.0
 * @author: JoeBig7
 * @date: 2019/5/23 18:36
 */
public interface QuestionService {

    void releaseOrUpdateQuestion(QuestionBeanBo questionBeanBo);

    void deleteQuestion(Long id);

    QuestionVO getQuestionInfo(Long id);

    RestData<QuestionBean> getQuestionListByKeyWord(String key, Integer pageNo, Integer pageSize);

    RestData<QuestionBean> getQuestionListByUserId(Long userId, Integer pageNo, Integer pageSize);

    RestData getQuestionSearch(String keyword, Integer pageNo, Integer pageSize);

    RestData<QuestionBean> recommend(Long userId, Integer pageNo, Integer pageSize);
}