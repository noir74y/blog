### URL
http://localhost:9090/items
---
### ER
* items - посты
* comments - комментарии
* tags - теги
* items_tags - теги постов
  
![image](https://github.com/user-attachments/assets/64bd8b14-d98c-4cab-8dc3-fa13b62d944e)

---
#### БД для прода: 
###### postgresql://postgres:postgres@localhost:5433/mine  
###### не забыть сказать _ALTER ROLE postgres SET search_path TO blog;_
---
#### БД для теста: 
###### postgresql://postgres:postgres@localhost:5433/test  
###### тоже не забыть сказать _ALTER ROLE postgres SET search_path TO blog;_
---
