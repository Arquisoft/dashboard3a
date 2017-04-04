cd bin/windows
start "Zookeeper" cmd /c zookeeper-server-start.bat ../../config/zookeeper.properties
timeout /t 10 /nobreak > NUL
start "Apache Kafka" cmd /c kafka-server-start.bat ../../config/server.properties