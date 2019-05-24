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
import org.springcloudbox.core.log.event.ErrorLogEvent;
import org.springcloudbox.core.log.model.LogError;
import org.springcloudbox.core.log.utils.LogAbstractUtil;
import org.springcloudbox.core.tool.utils.Exceptions;
import org.springcloudbox.core.tool.utils.Func;
import org.springcloudbox.core.tool.utils.SpringUtil;
import org.springcloudbox.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息事件发送
 *
 * @author Chill
 */
public class ErrorLogPublisher {

	public static void publishEvent(Throwable error, String requestUri) {
		HttpServletRequest request = WebUtil.getRequest();
		LogError logError = new LogError();
		logError.setRequestUri(requestUri);
		if (Func.isNotEmpty(error)) {
			logError.setStackTrace(Exceptions.getStackTraceAsString(error));
			logError.setExceptionName(error.getClass().getName());
			logError.setMessage(error.getMessage());
			StackTraceElement[] elements = error.getStackTrace();
			if (Func.isNotEmpty(elements)) {
				StackTraceElement element = elements[0];
				logError.setMethodName(element.getMethodName());
				logError.setMethodClass(element.getClassName());
				logError.setFileName(element.getFileName());
				logError.setLineNumber(element.getLineNumber());
			}
		}
		LogAbstractUtil.addRequestInfoToLog(request, logError);

		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logError);
		event.put(EventConstant.EVENT_REQUEST, request);
		SpringUtil.publishEvent(new ErrorLogEvent(event));
	}

}
