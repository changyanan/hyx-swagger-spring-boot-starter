#!/usr/bin/env bash
mvn clean source:jar jar:jar javadoc:jar install -Dmaven.test.skip=true