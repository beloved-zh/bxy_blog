package com.zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.mapper.FeedbackMappper;
import com.zh.pojo.Feedback;
import com.zh.service.FeedbackService;
import org.springframework.stereotype.Service;

/**
 * @author Beloved
 * @date 2020/8/23 14:45
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMappper, Feedback> implements FeedbackService {
}
