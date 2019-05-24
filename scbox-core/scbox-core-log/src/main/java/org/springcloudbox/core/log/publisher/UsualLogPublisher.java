/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springcloudbox.core.log.publisher;

import org.springcloudbox.core.log.constant.EventConstant;
import org.springcloudbox.core.log.event.UsualLogEvent;
import org.springcloudbox.core.log.model.LogUsual;
import org.springcloudbox.core.log.utils.LogAbstractUtil;
import org.springcloudbox.core.tool.utils.SpringUtil;
import org.springcloudbox.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * BLADE日志信息事件发送
 *
 * @author Chill
 */
public class UsualLogPublisher {

	public static void publishEvent(String level, String id, String data) {
		HttpServletRequest request = WebUtil.getRequest();
		LogUsual logUsual = new LogUsual();
		logUsual.setLogLevel(level);
		logUsual.setLogId(id);
		logUsual.setLogData(data);

		LogAbstractUtil.addRequestInfoToLog(request, logUsual);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logUsual);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new UsualLogEvent(event));
	}

}