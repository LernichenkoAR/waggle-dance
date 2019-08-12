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
package com.hotels.bdp.waggledance.conf;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.hotels.bdp.waggledance.api.model.DatabaseResolution;
import com.hotels.bdp.waggledance.server.MetaStoreProxyServer;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "")
public class WaggleDanceConfiguration {

  private @NotNull @Min(1) Integer port = MetaStoreProxyServer.DEFAULT_WAGGLEDANCE_PORT;
  private boolean verbose;
  private @Min(1) int disconnectConnectionDelay = 5;
  private @NotNull TimeUnit disconnectTimeUnit = TimeUnit.MINUTES;
  private Map<String, String> configurationProperties;
  private @NotNull DatabaseResolution databaseResolution = DatabaseResolution.MANUAL;

//  //Kerberos environment properties
//  private String securityAuthentication = "SIMPLE";
    //configuration-properties.hive.metastore.sasl.enabled=true
//  private boolean enableSASL = false;
   // configuration-properties.hadoop.security.authentication=KERBEROS
//  private  String kerberosPrincipal;
//  private String keytabFile;
//  private boolean useBeeline = false;
//  private String metastoreTockenSignature;

  // Defaults taken from org.apache.thrift.server.TThreadPoolServer
  // Cannot set the unit but it is in seconds.
  private int thriftServerStopTimeoutValInSeconds = 60;
  private int thriftServerRequestTimeout = 20;
  private TimeUnit thriftServerRequestTimeoutUnit = TimeUnit.SECONDS;
  private int statusPollingDelay = 5;
  private TimeUnit statusPollingDelayTimeUnit = TimeUnit.MINUTES;

//  public boolean isEnableSASL() {
//    return enableSASL;
//  }
//
//  public void setEnableSASL(boolean enableSASL) {
//    this.enableSASL = enableSASL;
//  }
//
//  public String getSecurityAuthentication() {
//    return securityAuthentication;
//  }
//
//  public void setSecurityAuthentication(String securityAuthentication) {
//    this.securityAuthentication = securityAuthentication;
//  }
//
//  public String getKerberosPrincipal() {
//    return kerberosPrincipal;
//  }
//
//  public void setKerberosPrincipal(String kerberosPrincipal) {
//    this.kerberosPrincipal = kerberosPrincipal;
//  }
//
//  public String getKeytabFile() {
//    return keytabFile;
//  }
//
//  public void setKeytabFile(String keytabFile) {
//    this.keytabFile = keytabFile;
//  }
//
//  public boolean isUseBeeline() {
//    return useBeeline;
//  }
//
//  public void setUseBeeline(boolean useBeeline) {
//    this.useBeeline = useBeeline;
//  }
//
//  public String getMetastoreTockenSignature() {
//    return metastoreTockenSignature;
//  }
//
//  public void setMetastoreTockenSignature(String metastoreTockenSignature) {
//    this.metastoreTockenSignature = metastoreTockenSignature;
//  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public boolean isVerbose() {
    return verbose;
  }

  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  public int getDisconnectConnectionDelay() {
    return disconnectConnectionDelay;
  }

  public void setDisconnectConnectionDelay(int disconnectConnectionDelay) {
    this.disconnectConnectionDelay = disconnectConnectionDelay;
  }

  public TimeUnit getDisconnectTimeUnit() {
    return disconnectTimeUnit;
  }

  public void setDisconnectTimeUnit(TimeUnit disconnectTimeUnit) {
    this.disconnectTimeUnit = disconnectTimeUnit;
  }

  public Map<String, String> getConfigurationProperties() {
    return configurationProperties;
  }

  public void setConfigurationProperties(Map<String, String> configurationProperties) {
    this.configurationProperties = configurationProperties;
  }

  public void setDatabaseResolution(DatabaseResolution databaseResolution) {
    this.databaseResolution = databaseResolution;
  }

  public DatabaseResolution getDatabaseResolution() {
    return databaseResolution;
  }

  public int getThriftServerStopTimeoutValInSeconds() {
    return thriftServerStopTimeoutValInSeconds;
  }

  public void setThriftServerStopTimeoutValInSeconds(int thriftServerStopTimeoutValInSeconds) {
    this.thriftServerStopTimeoutValInSeconds = thriftServerStopTimeoutValInSeconds;
  }

  public int getThriftServerRequestTimeout() {
    return thriftServerRequestTimeout;
  }

  public void setThriftServerRequestTimeout(int thriftServerRequestTimeout) {
    this.thriftServerRequestTimeout = thriftServerRequestTimeout;
  }

  public TimeUnit getThriftServerRequestTimeoutUnit() {
    return thriftServerRequestTimeoutUnit;
  }

  public void setThriftServerRequestTimeoutUnit(TimeUnit thriftServerRequestTimeoutUnit) {
    this.thriftServerRequestTimeoutUnit = thriftServerRequestTimeoutUnit;
  }

  public int getStatusPollingDelay() {
    return statusPollingDelay;
  }

  public void setStatusPollingDelay(int statusPollingDelay) {
    this.statusPollingDelay = statusPollingDelay;
  }

  public TimeUnit getStatusPollingDelayTimeUnit() {
    return statusPollingDelayTimeUnit;
  }

  public void setStatusPollingDelayTimeUnit(TimeUnit statusPollingDelayTimeUnit) {
    this.statusPollingDelayTimeUnit = statusPollingDelayTimeUnit;
  }

}
