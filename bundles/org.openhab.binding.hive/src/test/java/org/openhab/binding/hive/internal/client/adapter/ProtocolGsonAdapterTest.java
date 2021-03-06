/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.hive.internal.client.adapter;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.hive.internal.client.HiveApiConstants;
import org.openhab.binding.hive.internal.client.Protocol;

/**
 *
 *
 * @author Ross Brown - Initial contribution
 */
@NonNullByDefault
public class ProtocolGsonAdapterTest extends ComplexEnumGsonAdapterTest<Protocol, ProtocolGsonAdapter> {
    @Override
    protected ProtocolGsonAdapter getAdapter() {
        return new ProtocolGsonAdapter();
    }

    @Override
    protected List<List<Object>> getGoodParams() {
        return Arrays.asList(
                Arrays.asList(
                        Protocol.MQTT,
                        HiveApiConstants.PROTOCOL_MQTT
                ),
                Arrays.asList(
                        Protocol.PROXIED,
                        HiveApiConstants.PROTOCOL_PROXIED
                ),
                Arrays.asList(
                        Protocol.SYNTHETIC,
                        HiveApiConstants.PROTOCOL_SYNTHETIC
                ),
                Arrays.asList(
                        Protocol.VIRTUAL,
                        HiveApiConstants.PROTOCOL_VIRTUAL
                ),
                Arrays.asList(
                        Protocol.XMPP,
                        HiveApiConstants.PROTOCOL_XMPP
                ),
                Arrays.asList(
                        Protocol.ZIGBEE,
                        HiveApiConstants.PROTOCOL_ZIGBEE
                )
        );
    }

    @Override
    protected Protocol getUnexpectedEnum() {
        return Protocol.UNEXPECTED;
    }

    @Override
    protected String getUnexpectedString() {
        return "SOMETHING_UNEXPECTED";
    }
}
