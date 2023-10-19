export MYSQLDB_ROOT_PASSWORD := ayadinou
export MYSQLDB_DATABASE := pet_store
export MYSQLDB_LOCAL_PORT := 3306
export MYSQLDB_DOCKER_PORT := 3306
export SPRING_LOCAL_PORT := 3000
export SPRING_DOCKER_PORT := 8080
export MYSQLDB_USER := root
export MYSQLDB_ROOT_PASSWORD := ayadinou 
export MYSQLDB_DATABASE := pet_store

run_app :
	docker-compose  up 
stop_app :
	docker-compose down
