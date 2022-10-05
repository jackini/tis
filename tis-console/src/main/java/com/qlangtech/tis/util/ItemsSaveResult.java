/**
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.qlangtech.tis.util;

import com.qlangtech.tis.extension.Describable;
import com.qlangtech.tis.plugin.SetPluginsResult;

import java.util.List;

/**
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2022-03-26 14:11
 **/
public class ItemsSaveResult {

  public static String KEY_ITEMS_SAVE_RESULT = "items_save_result";
  public final List<Describable> describableList;
  public final SetPluginsResult cfgSaveResult;

  public ItemsSaveResult(List<Describable> describableList, SetPluginsResult cfgSaveResult) {
    this.describableList = describableList;
    this.cfgSaveResult = cfgSaveResult;
  }
}
