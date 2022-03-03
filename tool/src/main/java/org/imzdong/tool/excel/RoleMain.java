package org.imzdong.tool.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author dong.zhou
 * @since 2022/3/2 19:05
 */
public class RoleMain {

    private static String filePath = "D:\\WorkSpace\\task\\7c4role\\";

    public static void main(String[] args) {
        String excelName = "Resources.xlsx";
        List<Resource> roots = new ArrayList<>();
        List<Resource> subs = new ArrayList<>();
        List<ResourceAction> actionList = new ArrayList<>();
        List<ResourceActionPath> actionPathList = new ArrayList<>();
        // 读取部分sheet
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(filePath + excelName).build();

            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet rootResourceSheet = EasyExcel.readSheet("RootResources").head(Resource.class)
                            .registerReadListener(new PageReadListener<Resource>(roots::addAll))
                            .build();
            ReadSheet subResourceSheet = EasyExcel.readSheet("SubResources").head(Resource.class)
                            .registerReadListener(new PageReadListener<Resource>(subs::addAll))
                            .build();
            ReadSheet actionSheet = EasyExcel.readSheet("Actions").head(ResourceAction.class)
                            .registerReadListener(new PageReadListener<ResourceAction>(actionList::addAll))
                            .build();
            ReadSheet actionPathSheet = EasyExcel.readSheet("Actions_Path").head(ResourceActionPath.class)
                    .registerReadListener(new PageReadListener<ResourceActionPath>(actionPathList::addAll))
                    .build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(rootResourceSheet, subResourceSheet, actionSheet, actionPathSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }

        List<Result> results = new ArrayList<>();
        Map<String, List<Resource>> subListMap = subs.stream().collect(Collectors.groupingBy(m -> m.getParentId()));
        Map<String, List<ResourceAction>> actionListMap = actionList.stream().collect(Collectors.groupingBy(m -> m.getResourceId()));
        Map<String, List<ResourceActionPath>> actionPathListMap = actionPathList.stream().collect(Collectors.groupingBy(m -> m.getActionId()));
        roots.forEach(r->{
            Result rootResult = new Result();
            rootResult.setIdOne(r.getResourceId());
            rootResult.setShowName(r.getShowName());
            results.add(rootResult);
            List<Resource> subResources = subListMap.get(r.getResourceId());
            if(CollectionUtils.isNotEmpty(subResources)) {
                subResources.forEach(s -> {
                    Result subResult = new Result();
                    subResult.setShowName(s.getShowName());
                    subResult.setParentIdTwo(r.getResourceId());
                    subResult.setIdTwo(s.getResourceId());
                    results.add(subResult);
                    List<Result> fillActions = fillActions(s, actionListMap, actionPathListMap);
                    if(fillActions != null) {
                        results.addAll(fillActions);
                    }
                });
            }else {
                List<Result> fillRootActions = fillActions(r, actionListMap, actionPathListMap);
                if(fillRootActions != null) {
                    results.addAll(fillRootActions);
                }
            }
        });
        String wExcelName = "Resources-result_3.xlsx";
        EasyExcel.write(filePath + wExcelName, Result.class).sheet("resources").doWrite(results);

    }

    private static List<Result> fillActions(Resource s, Map<String, List<ResourceAction>> actionListMap,
                                   Map<String, List<ResourceActionPath>> actionPathListMap){
        List<ResourceAction> actions = actionListMap.get(s.getResourceId());
        if(CollectionUtils.isEmpty(actions)){
            System.out.println(s.getResourceId());
            return null;
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<Result> list = new ArrayList<>();
        actions.forEach(m -> {
            String actionId = m.getActionId();
            List<ResourceActionPath> paths = actionPathListMap.get(actionId);
            List<Result> actionResults = paths.stream().map(path -> {
                Result actionResult = new Result();
                actionResult.setShowName(m.getShowName());
                actionResult.setParentIdThree(s.getResourceId());
                actionResult.setIdTwo(atomicInteger.decrementAndGet() + "");
                actionResult.setPath(path.getPath());
                return actionResult;
            }).collect(Collectors.toList());
            list.addAll(actionResults);
        });
        return list;
    }
}
