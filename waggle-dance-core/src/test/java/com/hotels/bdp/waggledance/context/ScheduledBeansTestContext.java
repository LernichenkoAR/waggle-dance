/**
 * Copyright (C) 2016-2019 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hotels.bdp.waggledance.context;

import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import com.hotels.bdp.waggledance.conf.WaggleDanceConfiguration;
import com.hotels.bdp.waggledance.mapping.service.impl.PollingFederationService;

public class ScheduledBeansTestContext {

  @Bean
  public WaggleDanceConfiguration waggleDanceConfiguration() {
    WaggleDanceConfiguration mock = Mockito.mock(WaggleDanceConfiguration.class);
    when(mock.getStatusPollingDelay()).thenReturn(10);
    when(mock.getStatusPollingDelayTimeUnit()).thenReturn(TimeUnit.MILLISECONDS);
    return mock;
  }

  @Bean
  public PollingFederationService pollingFederationService() {
    return Mockito.mock(PollingFederationService.class);
  }

}
