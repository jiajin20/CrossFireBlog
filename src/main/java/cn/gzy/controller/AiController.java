package cn.gzy.controller;

import cn.gzy.pojo.QuestionRequest;
import cn.gzy.pojo.Result;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static cn.gzy.util.ApiTestUtil.callWithMessage;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class AiController {

    @PostMapping("/test")
    public Result aiService(@RequestBody QuestionRequest request) {
        try {
            String question = request.getQuestion();  // 获取前端发送的 question
            // 调用阿里云 AIGC API 获取生成的结果
            GenerationResult result = callWithMessage(question);
            String responseContent = result.getOutput().getChoices().get(0).getMessage().getContent();
            log.info("生成的回答：{}", responseContent); // 记录生成的回答内容
            return Result.success(responseContent);
        } catch (NoApiKeyException e) {
            log.error("API Key 缺失: {}", e.getMessage(), e); // 记录具体的错误信息
            return Result.error("API Key 缺失，请检查配置");
        } catch (InputRequiredException e) {
            log.error("输入问题缺失: {}", e.getMessage(), e); // 记录具体的错误信息
            return Result.error("缺少必要的输入参数");
        } catch (ApiException e) {
            log.error("API 调用失败: {}", e.getMessage(), e); // 记录具体的错误信息
            return Result.error("调用服务失败，请稍后重试");
        } catch (Exception e) {
            log.error("未知错误: {}", e.getMessage(), e); // 捕获其他未预料的错误
            return Result.error("未知错误，请稍后重试");
        }
    }
}
