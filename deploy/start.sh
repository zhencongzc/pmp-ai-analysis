#!/bin/bash
APP_NAME=pmp-ai-analysis-1.0-SNAPSHOT.jar
APP_PATH=/opt/pmp-ai-analysis

# 停止已运行的实例
if [ -f "$APP_PATH/pid.txt" ]; then
    PID=$(cat "$APP_PATH/pid.txt")
    echo "Stopping application (PID: $PID)..."
    kill -9 $PID
    rm "$APP_PATH/pid.txt"
fi

# 启动应用
echo "Starting application..."
nohup java -server -Xms1g -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:G1HeapRegionSize=16m -XX:G1MixedGCCountTarget=8 -XX:ParallelGCThreads=8 -XX:ConcGCThreads=4 -XX:+UseStringDeduplication -jar "$APP_PATH/$APP_NAME" > "$APP_PATH/system.log" 2>&1 &
echo $! > "$APP_PATH/pid.txt"
echo "Application started (PID: $(cat "$APP_PATH/pid.txt"))"
