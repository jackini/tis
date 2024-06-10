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

package com.qlangtech.tis.plugin.datax.transformer;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson2.annotation.JSONField;
import com.qlangtech.tis.extension.Describable;
import com.qlangtech.tis.extension.Descriptor;
import com.qlangtech.tis.plugin.IPluginStore.AfterPluginSaved;
import com.qlangtech.tis.util.IPluginContext;

import java.util.List;
import java.util.Optional;

/**
 * DataX 的UDF 定义 <br/>
 * https://github.com/alibaba/DataX/blob/master/transformer/doc/transformer.md
 *
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2023-05-07 12:35
 **/
public abstract class UDFDefinition implements Describable<UDFDefinition>, AfterPluginSaved,PluginLiteriaDesc {

    public final String getImpl() {
        return this.getClass().getName();
    }

    @Override
    public final void afterSaved(IPluginContext pluginContext, Optional<Context> context) {
        try {
            // 直接传输到前端UI上
            Context c = context.orElseThrow(() -> new IllegalStateException("context must be present"));

//            Map<String, Object> biz = Maps.newHashMap();
//            biz.put("item", (new DescribableJSON(this)).getItemJson());
//            biz.put("itemLiteria", this.getLiteria());

            pluginContext.setBizResult(c, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @JSONField(serialize = false)
    @Override
    public final Descriptor<UDFDefinition> getDescriptor() {
        return Describable.super.getDescriptor();
    }
}
