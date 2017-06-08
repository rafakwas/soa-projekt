--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

DROP TABLE USERS;
DROP TABLE USER_ROLES;

CREATE TABLE USERS(login VARCHAR(64) PRIMARY KEY, passwd VARCHAR(64));
CREATE TABLE USER_ROLES(login VARCHAR(64), role VARCHAR(32));

INSERT into USERS values('admin', 'ISMvKXpXpadDiUoOSoAfww==');
INSERT into USERS values('guard1', '771fvPTo2hwhqnFqj26JKg==');
INSERT into USERS values('guard2', '2acNL6r5hJk036uQsveFZA==');

INSERT into USER_ROLES values('admin', 'admin');
INSERT into USER_ROLES values('guard1', 'pool1');
INSERT into USER_ROLES values('guard2', 'pool2');