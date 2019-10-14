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
package com.hotels.bdp.waggledance.server;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.hadoop.hive.thrift.DelegationTokenIdentifier;
import org.apache.hadoop.hive.thrift.DelegationTokenSecretManager;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenSecretManager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WDDelegationTokenSecretManager extends DelegationTokenSecretManager {

  private final long tokenRenewInterval;

  private Map<DelegationTokenIdentifier, DelegationTokenInformation> currentTokens = new HashMap<>();


  /**
   * Create a secret manager
   *
   * @param delegationKeyUpdateInterval        the number of seconds for rolling new
   *                                           secret keys.
   * @param delegationTokenMaxLifetime         the maximum lifetime of the delegation
   *                                           tokens
   * @param delegationTokenRenewInterval       how often the tokens must be renewed
   * @param delegationTokenRemoverScanInterval how often the tokens are scanned
   */
  public WDDelegationTokenSecretManager(long delegationKeyUpdateInterval, long delegationTokenMaxLifetime,
                                        long delegationTokenRenewInterval, long delegationTokenRemoverScanInterval) {

    super(delegationKeyUpdateInterval, delegationTokenMaxLifetime, delegationTokenRenewInterval,
            delegationTokenRemoverScanInterval);
    this.tokenRenewInterval = delegationTokenRenewInterval;

  }

  private DelegationTokenIdentifier extractDelegationTokenIdentifier(Token<DelegationTokenIdentifier> dt) throws IOException {
    DelegationTokenIdentifier identifier = new DelegationTokenIdentifier();
    ByteArrayInputStream buf = new ByteArrayInputStream(dt.getIdentifier());
    DataInputStream in = new DataInputStream(buf);
    identifier.readFields(in);
    in.close();
    return identifier;
  }

  private Token<DelegationTokenIdentifier> extractDelegationToken(String token) throws IOException {
    Token<DelegationTokenIdentifier> dt = new Token<>();
    dt.decodeFromUrlString(token);
    return dt;
  }

  private synchronized void storeToken(Token<DelegationTokenIdentifier> dt, DelegationTokenIdentifier identifier) {
    currentTokens.put(identifier, new AbstractDelegationTokenSecretManager.DelegationTokenInformation(
            System.currentTimeMillis() + tokenRenewInterval, dt.getPassword()));
  }

  private synchronized void updateToken(Token<DelegationTokenIdentifier> dt, DelegationTokenIdentifier identifier,
                                        long renewalDate) {
    currentTokens.put(identifier, new AbstractDelegationTokenSecretManager.DelegationTokenInformation(
            renewalDate, dt.getPassword()));
  }

  public void store(String token) {
    try {
      Token<DelegationTokenIdentifier> dt = extractDelegationToken(token);
      DelegationTokenIdentifier identifier = extractDelegationTokenIdentifier(dt);
      storeToken(dt, identifier);
    } catch (IOException e) {
      throw new RuntimeException("Can't get delegation token " + e.getMessage());
    }
  }

  public void renew(String token, long renewDate) {
    try {
      Token<DelegationTokenIdentifier> delegationToken = extractDelegationToken(token);
      DelegationTokenIdentifier identifier = extractDelegationTokenIdentifier(delegationToken);
      updateToken(delegationToken, identifier, renewDate);
    } catch (IOException e) {
      throw new RuntimeException("Can't renew delegation token " + e.getMessage());
    }
  }
  @Override
  protected DelegationTokenInformation checkToken(DelegationTokenIdentifier identifier) throws InvalidToken {
    return super.checkToken(identifier);
  }

  protected DelegationTokenInformation getTokenInfo(DelegationTokenIdentifier ident) {
    return currentTokens.get(ident);
  }

  @VisibleForTesting
  Map<DelegationTokenIdentifier, DelegationTokenInformation> getCurrentTokens() {
    return currentTokens;
  }
}
