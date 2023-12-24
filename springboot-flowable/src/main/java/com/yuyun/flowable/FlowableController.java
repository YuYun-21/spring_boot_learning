package com.yuyun.flowable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "流程图")
public class FlowableController {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;
    private final ProcessEngine processEngine;

    @GetMapping("/pic")
    @ApiOperation(value = "流程图", notes = "图片生成")
    public void showPic(HttpServletResponse resp, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (pi == null) {
            return;
        }
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(processId)
                .list();

        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        // 生成流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engineConf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engineConf.getProcessDiagramGenerator();
        byte[] buf = new byte[1024];
        int length = 0;
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                "png", activityIds, flows,
                engineConf.getActivityFontName(),
                engineConf.getLabelFontName(),
                engineConf.getAnnotationFontName(),
                engineConf.getClassLoader(),
                1.0, false);
             OutputStream out = resp.getOutputStream()) {
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        }
    }
}
