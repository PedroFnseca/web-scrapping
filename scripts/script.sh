#!/bin/bash
javac HtmlAnalyzer.java
if [ $? -ne 0 ]; then
  echo "Compilation failed."
  exit 1
fi
java HtmlAnalyzer