/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qlangtech.tis.datax.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.qlangtech.tis.datax.DBDataXChildTask;
import com.qlangtech.tis.datax.DataXCfgFile;
import com.qlangtech.tis.datax.DataXJobInfo;
import com.qlangtech.tis.datax.IDataXPluginMeta;
import com.qlangtech.tis.datax.IDataxContext;
import com.qlangtech.tis.datax.IDataxGlobalCfg;
import com.qlangtech.tis.datax.IDataxProcessor;
import com.qlangtech.tis.datax.IDataxReader;
import com.qlangtech.tis.datax.IDataxReaderContext;
import com.qlangtech.tis.datax.IDataxWriter;
import com.qlangtech.tis.datax.IGroupChildTaskIterator;
import com.qlangtech.tis.datax.TableAliasMapper;
import com.qlangtech.tis.manage.common.TisUTF8;
import com.qlangtech.tis.offline.DataxUtils;
import com.qlangtech.tis.plugin.datax.CreateTableSqlBuilder;
import com.qlangtech.tis.plugin.ds.CMeta;
import com.qlangtech.tis.plugin.ds.ISelectedTab;
import com.qlangtech.tis.plugin.trigger.JobTrigger;
import com.qlangtech.tis.datax.IDataXGenerateCfgs;
import com.qlangtech.tis.trigger.util.JsonUtil;
import com.qlangtech.tis.util.IPluginContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author: baisui 百岁
 * @create: 2021-04-20 18:06
 **/
public class DataXCfgGenerator {

    private transient static final VelocityEngine velocityEngine;

    static {
        try {
            velocityEngine = new VelocityEngine();
            Properties prop = new Properties();
            prop.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");

            prop.setProperty("resource.loader", "tisLoader");
            prop.setProperty("tisLoader.resource.loader.class", TISClasspathResourceLoader.class.getName());

            velocityEngine.init(prop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private final IDataxProcessor dataxProcessor;
    private final IDataxGlobalCfg globalCfg;
    private final String dataxName;
    private final IPluginContext pluginCtx;

    public DataXCfgGenerator(IPluginContext pluginCtx, String dataxName, IDataxProcessor dataxProcessor) {
        Objects.requireNonNull(dataxProcessor, "dataXprocessor can not be null");
        IDataxGlobalCfg dataXGlobalCfg = dataxProcessor.getDataXGlobalCfg();
        Objects.requireNonNull(dataXGlobalCfg, "globalCfg can not be null");
        this.dataxProcessor = dataxProcessor;
        this.globalCfg = dataXGlobalCfg;
        this.dataxName = dataxName;
        this.pluginCtx = pluginCtx;
    }

    protected String getTemplateContent(IDataxReader reader, IDataxWriter writer) {
        final String tpl = globalCfg.getTemplate();

        // List<IDataxReader> readers = dataxProcessor.getReaders(pluginCtx);
        //        for (IDataxReader reader : readers) {
        //IDataxReader reader = dataxProcessor.getReader(pluginCtx);
        // IDataxWriter writer = dataxProcessor.getWriter(pluginCtx);
        String readerTpl = reader.getTemplate();
        String writerTpl = writer.getTemplate();
        if (StringUtils.isEmpty(readerTpl)) {
            throw new IllegalStateException("readerTpl of '" + reader.getDataxMeta().getName() + "' can not be null");
        }
        if (StringUtils.isEmpty(writerTpl)) {
            throw new IllegalStateException("writerTpl of '" + writer.getDataxMeta().getName() + "' can not be null");
        }
        String template = StringUtils.replace(tpl, "<!--reader-->", readerTpl);
        template = StringUtils.replace(template, "<!--writer-->", writerTpl);
        return template;
        // }


    }


    /**
     * 取得之前已经存在的文件
     *
     * @param parentDir
     * @return
     */
    public GenerateCfgs getExistCfg(File parentDir) throws Exception {
        File dataxCfgDir = dataxProcessor.getDataxCfgDir(this.pluginCtx);
        GenerateCfgs generateCfgs = new GenerateCfgs(dataxCfgDir);

        File genFile = new File(parentDir, FILE_GEN);
        if (!genFile.exists()) {
            return generateCfgs;
        }


        generateCfgs.createDDLFiles = getExistDDLFiles();

        GenerateCfgs cfgs = GenerateCfgs.readFromGen(dataxCfgDir, Optional.empty());
        generateCfgs.setGenTime(cfgs.getGenTime());
        generateCfgs.setGroupedChildTask(cfgs.getGroupedChildTask());
        return generateCfgs;
    }

    /**
     * 取得已经存在的DDL Sql文件
     *
     * @return
     */
    private List<String> getExistDDLFiles() {
        File dataxCreateDDLDir = dataxProcessor.getDataxCreateDDLDir(this.pluginCtx);
        return Lists.newArrayList(dataxCreateDDLDir.list((dir, f) -> {
            return StringUtils.endsWith(f, DataXCfgFile.DATAX_CREATE_DDL_FILE_NAME_SUFFIX);
        }));
    }


    public static final String FILE_GEN = "gen";

    public GenerateCfgs startGenerateCfg(final File dataXCfgDir) throws Exception {
        return startGenerateCfg(new IGenerateScriptFile() {
            @Override
            public void generateScriptFile(IDataxReader reader, IDataxWriter writer,
                                           IDataxReaderContext readerContext, Set<String> createDDLFiles,
                                           Optional<IDataxProcessor.TableMap> tableMapper) throws IOException {
                generateDataXAndSQLDDLFile(dataXCfgDir, reader, writer, readerContext, createDDLFiles, tableMapper);
            }
        });
    }


    public GenerateCfgs startGenerateCfg(IGenerateScriptFile scriptFileGenerator) throws Exception {


        //        FileUtils.forceMkdir(dataXCfgDir);
        //        // 先清空文件
        //        FileUtils.cleanDirectory(dataXCfgDir);

        boolean unStructedReader = dataxProcessor.isReaderUnStructed(this.pluginCtx);


        IDataxWriter writer = dataxProcessor.getWriter(this.pluginCtx);
        DataxWriter.BaseDataxWriterDescriptor writerDescriptor = writer.getWriterDescriptor();

        TableAliasMapper tabAlias = Objects.requireNonNull(dataxProcessor.getTabAlias(this.pluginCtx), "tabAlias can "
                + "not be null");
        Set<String> createDDLFiles = Sets.newHashSet();
        List<String> existDDLFiles = getExistDDLFiles();

        GenerateCfgs cfgs = new GenerateCfgs(this.dataxProcessor.getDataxCfgDir(this.pluginCtx));
        List<IDataxReader> readers = dataxProcessor.getReaders(this.pluginCtx);
        if (CollectionUtils.isEmpty(readers)) {
            throw new IllegalStateException(dataxName + " relevant readers can not be empty");
        }
        for (IDataxReader reader : readers) {

            AtomicReference<Map<String, ISelectedTab>> selectedTabsRef = new AtomicReference<>();
            java.util.concurrent.Callable<Map<String, ISelectedTab>> selectedTabsCall = () -> {
                if (selectedTabsRef.get() == null) {
                    Map<String, ISelectedTab> selectedTabs =
                            reader.getSelectedTabs().stream().collect(Collectors.toMap((t) -> t.getName(), (t) -> t));
                    selectedTabsRef.set(selectedTabs);
                }
                return selectedTabsRef.get();
            };


            try (IGroupChildTaskIterator subTasks = Objects.requireNonNull(reader.getSubTasks(),
                    "subTasks can not " + "be" + " null")) {
                IDataxReaderContext readerContext = null;
                //  File configFile = null;
                // List<String> subTaskName = Lists.newArrayList();

                Optional<IDataxProcessor.TableMap> tableMapper = null;
                //StringBuffer createDDL = new StringBuffer();

                while (subTasks.hasNext()) {
                    readerContext = subTasks.next();
                    if (!dataxProcessor.isWriterSupportMultiTableInReader(this.pluginCtx)) {

                        if (tabAlias.isSingle()) {
                            // 针对ES的情况
                            Optional<IDataxProcessor.TableMap> first = tabAlias.getFirstTableMap();
                            //                            = tabAlias.values().stream().filter((t) -> t instanceof
                            //                            IDataxProcessor.TableMap)
                            //                            .map((t) -> (IDataxProcessor.TableMap) t).findFirst();
                            if (first.isPresent()) {
                                tableMapper = first;
                            }
                        } else {
                            //IDataxWriter writer = dataxProcessor.getWriter(this.pluginCtx);
                            if (writer instanceof IDataxProcessor.INullTableMapCreator) {
                                tableMapper = Optional.empty();
                            }
                        }
                        Objects.requireNonNull(tableMapper,
                                "tabMapper can not be null,tabAlias.size()=" + tabAlias.size() + ",tabs:[" + tabAlias.getFromTabDesc() + "]");
                    } else if (unStructedReader) {
                        // 是在DataxAction的doSaveWriterColsMeta() 方法中持久化保存的
                        Optional<IDataxProcessor.TableMap> f = tabAlias.getFirstTableMap();
                        if (!f.isPresent()) {
                            // Objects.requireNonNull(tableMapper, "tableMap can not be null");
                            throw new IllegalStateException("tableMap can not be null");
                        }
                        tableMapper = f;
                        //                for (TableAlias tab : tabAlias.values()) {
                        //                    tableMapper = Optional.of((IDataxProcessor.TableMap) tab);
                        //                    break;
                        //                }

                    } else if (dataxProcessor.isRDBMS2UnStructed(this.pluginCtx)) {
                        // example: mysql -> oss
                        IDataxProcessor.TableMap m = createTableMap(tabAlias, selectedTabsCall.call(), readerContext);

                        tableMapper = Optional.of(m);
                    } else if (dataxProcessor.isRDBMS2RDBMS(this.pluginCtx)) {
                        // example: mysql -> mysql
                        tableMapper = Optional.of(createTableMap(tabAlias, selectedTabsCall.call(), readerContext));
                    } else {
                        // example:oss -> oss
                        // tableMapper = Optional.of(createTableMap(tabAlias, selectedTabsCall.call(), readerContext));
                        throw new IllegalStateException("unexpect status");
                    }
                    scriptFileGenerator.generateScriptFile(reader, writer, readerContext, createDDLFiles, tableMapper);
                }
                Map<String, List<DBDataXChildTask>> groupedInfo = subTasks.getGroupedInfo();
                if (MapUtils.isEmpty(groupedInfo)) {
                    throw new IllegalStateException("groupedInfo can not be empty");
                }
                cfgs.groupedChildTask.putAll(groupedInfo);
            }
            //  IDataxReader reader = dataxProcessor.getReader(this.pluginCtx);
        }

        // 将老的已经没有用的ddl sql文件删除调
        if (writerDescriptor.isSupportTabCreate()) {
            File createDDLDir = this.dataxProcessor.getDataxCreateDDLDir(this.pluginCtx);
            for (String oldDDLFile : existDDLFiles) {
                if (!createDDLFiles.contains(oldDDLFile)) {
                    FileUtils.deleteQuietly(new File(createDDLDir, oldDDLFile));
                }
            }
            if (CollectionUtils.isEmpty(createDDLFiles)) {
                throw new IllegalStateException("createDDLFiles can not be empty ");
            }
        }


        long current = System.currentTimeMillis();
        //        FileUtils.write(new File(dataXCfgDir, FILE_GEN), String.valueOf(current), TisUTF8.get(), false);
        cfgs.createDDLFiles = Lists.newArrayList(createDDLFiles);

        //  cfgs.dataxFiles = subTaskName;
        cfgs.genTime = current;
        return cfgs;
    }

    public interface IGenerateScriptFile {
        void generateScriptFile(IDataxReader reader, IDataxWriter writer, IDataxReaderContext readerContext,
                                Set<String> createDDLFiles, Optional<IDataxProcessor.TableMap> tableMapper) throws IOException;
    }


    private void generateDataXAndSQLDDLFile(File dataXCfgDir, IDataxReader reader, IDataxWriter writer,
                                            IDataxReaderContext readerContext, Set<String> createDDLFiles,
                                            Optional<IDataxProcessor.TableMap> tableMapper) throws IOException {
        generateTabCreateDDL(this.pluginCtx, dataxProcessor, writer, readerContext, createDDLFiles, tableMapper, false);
        if (StringUtils.isEmpty(readerContext.getTaskName())) {
            throw new IllegalStateException("readerContext.getTaskName() must be present");
        }
        //        File configFile = new File(dataXCfgDir
        //                , readerContext.getReaderContextId() + File.separator + readerContext.getTaskName() +
        //                IDataxProcessor.DATAX_CREATE_DATAX_CFG_FILE_NAME_SUFFIX);
        File configFile = DataXJobInfo.getJobPath(dataXCfgDir, readerContext.getReaderContextId(),
                readerContext.getTaskName() + DataXCfgFile.DATAX_CREATE_DATAX_CFG_FILE_NAME_SUFFIX);
        FileUtils.write(configFile, generateDataxConfig(readerContext, writer, reader, tableMapper), TisUTF8.get(), false);

    }

    public static void generateTabCreateDDL(IPluginContext pluginCtx, IDataxProcessor dataxProcessor,
                                            IDataxWriter writer, IDataxReaderContext readerContext,
                                            Set<String> createDDLFiles,
                                            Optional<IDataxProcessor.TableMap> tableMapper, boolean overWrite) throws IOException {
        DataxWriter.BaseDataxWriterDescriptor writerDescriptor = writer.getWriterDescriptor();
        if (tableMapper.isPresent() && writerDescriptor.isSupportTabCreate()) {
            for (CMeta colMeta : tableMapper.get().getSourceCols()) {
                if (colMeta.getType() == null) {
                    throw new IllegalStateException("reader context:" + readerContext.getSourceTableName() + " " +
                            "relevant col type which's name " + colMeta.getName() + " can not be null");
                }
                if (StringUtils.isEmpty(colMeta.getName())) {
                    throw new IllegalStateException("reader context:" + readerContext.getSourceTableName() + " " +
                            "relevant col name  can not be null");
                }
            }
            // 创建ddl

            IDataxProcessor.TableMap mapper = tableMapper.get();
            String sqlFileName = mapper.getTo() + DataXCfgFile.DATAX_CREATE_DDL_FILE_NAME_SUFFIX;
            if (!createDDLFiles.contains(sqlFileName)) {

                CreateTableSqlBuilder.CreateDDL createDDL = Objects.requireNonNull(writer.generateCreateDDL(mapper),
                        "createDDL can not be null");

                createDDLFiles.add(sqlFileName);
                // 由于用户可能已经手动改动过生成的DDL文件，所以不能强行覆盖已经存在的DDL文件，overWrite参数应该为false
                dataxProcessor.saveCreateTableDDL(pluginCtx, createDDL.getDDLScript(), sqlFileName, overWrite);
            }
        }
    }

    public static class GenerateCfgs implements IDataXGenerateCfgs {
        private List<DataXCfgFile> _dataxFiles;
        private List<String> createDDLFiles = Collections.emptyList();
        private Map<String, List<DBDataXChildTask>> groupedChildTask = Maps.newHashMap();
        private long genTime;

        private final File dataxCfgDir;

        public GenerateCfgs(File dataxCfgDir) {
            this.dataxCfgDir = dataxCfgDir;
        }

        public List<DataXCfgFile> getDataxFiles() {
            return getDataXCfgFiles().stream().map((file) -> file).collect(Collectors.toList());
        }

        @JSONField(serialize = false)
        public List<DataXCfgFile> getDataXCfgFiles() {

            if (this._dataxFiles == null) {
                this._dataxFiles =
                        this.getGroupedChildTask().values().stream().flatMap((tasks) -> tasks.stream()).map((task) -> {
                            task.getDbFactoryId();
                            File dataXCfg = task.getJobPath(this.dataxCfgDir);

                            if (!dataXCfg.exists()) {
                                throw new IllegalStateException("dataXCfg is not exist, path:" + dataXCfg.getAbsolutePath());
                            }
                            return (new DataXCfgFile()).setFile(dataXCfg).setDbFactoryId(task.dbFactoryId);
                        }).collect(Collectors.toList());
            }
            return this._dataxFiles;
        }


        public final Set<String> getTargetTabs() {
            return this.getGroupedChildTask().keySet();
        }

        /**
         * Map<String, List<String>> key: logicTableName
         *
         * @return
         */
        private Map<String, List<DBDataXChildTask>> getGroupedChildTask() {
            if (groupedChildTask == null) {
                throw new IllegalStateException("groupedChildTask can not be null");
            }
            return groupedChildTask;
        }

        public void setGroupedChildTask(Map<String, List<DBDataXChildTask>> groupedChildTask) {
            this.groupedChildTask = groupedChildTask;
        }

        /**
         * 取得一个任务组（通常是一个逻辑表）对应的子任务（导入分库分表的子任务）
         *
         * @param taskGroupName 通常是一个表的名称
         * @return
         */
        public List<DBDataXChildTask> getDataXTaskDependencies(String taskGroupName) {
            List<DBDataXChildTask> subChildTask = null;
            Map<String, List<DBDataXChildTask>> groupdTsk = this.getGroupedChildTask();
            if (CollectionUtils.isEmpty(subChildTask = groupdTsk.get(taskGroupName))) {
                throw new IllegalStateException("taskGroupName:" + taskGroupName + " relevant childTask:" + String.join(",", groupdTsk.keySet()) + " can not be empty");
            }
            return subChildTask;
        }


        public static final String KEY_GEN_TIME = "genTime";
        public static final String KEY_GROUP_CHILD_TASKS = "groupChildTasks";


        public void write2GenFile(File dataxCfgDir) {
            try {
                JSONObject o = new JSONObject();
                o.put(KEY_GROUP_CHILD_TASKS, this.getGroupedChildTask());
                o.put(KEY_GEN_TIME, this.getGenTime());
                FileUtils.write(new File(dataxCfgDir, DataXCfgGenerator.FILE_GEN), JsonUtil.toString(o),
                        TisUTF8.get(), false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Example:
         * <pre>
         * {
         * 	"genTime":1675309210655,
         * 	"groupChildTasks":{
         * 		"totalpayinfo":[
         *         {
         * 			"dataXCfgFileName":"totalpayinfo_0",
         * 			"dbIdenetity":"jdbc:mysql://192.168.28.200:3306/order2?useUnicode=yes&useCursorFetch=true&useSSL=false&serverTimezone=Asia%2FShanghai&useCompression=true&characterEncoding=utf8"
         *         }
         * 		]
         *    }
         * }
         * </pre>
         *
         * @param dataxCfgDir
         * @return
         */
        public static GenerateCfgs readFromGen(File dataxCfgDir, Optional<JobTrigger> partialTrigger) {
            try {
                GenerateCfgs cfgs = new GenerateCfgs(dataxCfgDir);
                JSONObject o = JSON.parseObject(FileUtils.readFileToString(new File(dataxCfgDir,
                        DataXCfgGenerator.FILE_GEN), TisUTF8.get()));

                cfgs.genTime = o.getLongValue(KEY_GEN_TIME);

                Map<String, List<DBDataXChildTask>> groupedChildTasks = Maps.newHashMap();

                Map<String, JSONArray> childTasks = o.getObject(KEY_GROUP_CHILD_TASKS, Map.class);
                Set<String> filterTabsName = null;
                if (partialTrigger.isPresent()) {
                    filterTabsName = partialTrigger.get()
                            .selectedTabs().stream().map((tab) -> tab.identityValue()).collect(Collectors.toSet());
                }

                List<DBDataXChildTask> tasks = null;
                for (Map.Entry<String, JSONArray> entry : childTasks.entrySet()) {

                    if (filterTabsName != null) {
                        if (!filterTabsName.contains(entry.getKey())) {
                            // 如果不存在则跳过
                            continue;
                        }
                    }

                    tasks = entry.getValue().toJavaList(DBDataXChildTask.class);
                    groupedChildTasks.put(entry.getKey(), tasks);
                }

                cfgs.groupedChildTask = groupedChildTasks;
                return cfgs;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 建表script
         *
         * @return
         */
        public List<String> getCreateDDLFiles() {
            return this.createDDLFiles;
        }

        public long getGenTime() {
            return genTime;
        }


        public void setGenTime(long genTime) {
            this.genTime = genTime;
        }
    }

    private IDataxProcessor.TableMap createTableMap(
            TableAliasMapper tabAlias, Map<String, ISelectedTab> selectedTabs
            , IDataxReaderContext readerContext) {
        return readerContext.createTableMap(tabAlias, selectedTabs);
    }

    /**
     * @param readerContext
     * @param tableMap
     * @return 生成的配置文件内容
     * @throws IOException
     */
    public String generateDataxConfig(
            IDataxReaderContext readerContext, IDataxWriter writer, IDataxReader reader,
            Optional<IDataxProcessor.TableMap> tableMap) throws IOException {
        Objects.requireNonNull(writer, "writer can not be null");
        StringWriter writerContent = null;
        final String tpl = getTemplateContent(reader, writer);
        if (StringUtils.isEmpty(tpl)) {
            throw new IllegalStateException("velocity template content can not be null");
        }
        try {
            VelocityContext mergeData = createContext(readerContext, writer.getSubTask(tableMap));
            writerContent = new StringWriter();
            velocityEngine.evaluate(mergeData, writerContent, "tablex-writer.vm", tpl);
        } catch (Exception e) {
            throw new RuntimeException(tpl + "\n", e);
        }
        String content = writerContent.toString();
        try {
            JSONObject cfg = JSON.parseObject(content);
            validatePluginName(writer, reader, cfg);
            return JsonUtil.toString(cfg, true);
        } catch (Exception e) {
            throw new RuntimeException(content, e);
        }
    }

    public void validatePluginName(IDataxWriter writer, IDataxReader reader, JSONObject cfg) {
        JSONObject job = cfg.getJSONObject("job");
        if (job != null) {
            JSONArray contentAry = job.getJSONArray("content");
            JSONObject rw = contentAry.getJSONObject(0);
            String readerName = rw.getJSONObject("reader").getString("name");
            String writerName = rw.getJSONObject("writer").getString("name");
            validatePluginName(writer.getDataxMeta(), reader.getDataxMeta(), writerName, readerName);
        } else {
            // 在单元测试流程中
            return;
        }

    }

    public static void validatePluginName(IDataXPluginMeta.DataXMeta writer, IDataXPluginMeta.DataXMeta reader,
                                          String writerName, String readerName) {
        if (!StringUtils.equals(readerName, reader.getName())) {
            throw new IllegalStateException("reader plugin name:" + readerName + " must equal with '" + reader.getName() + "'");
        }
        if (!StringUtils.equals(writerName, writer.getName())) {
            throw new IllegalStateException("writer plugin name:" + writerName + " must equal with '" + writer.getName() + "'");
        }
    }

    private VelocityContext createContext(IDataxContext reader, IDataxContext writer) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put(DataxUtils.DATAX_NAME, this.dataxName);
        velocityContext.put("reader", reader);
        velocityContext.put("writer", writer);
        velocityContext.put("cfg", this.globalCfg);
        return velocityContext;
    }

}
