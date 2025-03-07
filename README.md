### URL
http://localhost:9090
---
### ER
* posts - посты
* comments - комментарии
* tags - теги
* items_tags - теги постов
  
![image](https://github.com/user-attachments/assets/b01c6ac8-4397-4de6-8dc0-2a539e18a554)

---
#### БД для прода: 
###### postgresql://postgres:postgres@localhost:5433/mine  
###### не забыть сказать _ALTER ROLE postgres SET search_path TO blog;_
---
#### БД для теста: 
###### postgresql://postgres:postgres@localhost:5433/test  
###### тоже не забыть сказать _ALTER ROLE postgres SET search_path TO blog;_
---
#### Как запускать:  
##### .\gradlew clean build bootJar  
##### cd .\build\libs\  
##### java -jar .\blog-0.0.1-SNAPSHOT.jar 
